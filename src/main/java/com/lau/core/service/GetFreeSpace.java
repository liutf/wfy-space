package com.lau.core.service;

import com.google.common.base.Preconditions;
import com.lau.utils.Precondition;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by liutf on 2016-09-12.
 */
public class GetFreeSpace {

    private static final Logger log = LoggerFactory.getLogger(GetFreeSpace.class);

    public static final String wfInviteLink = "https://workflowy.com/invite/3bf81761.emlx";
    public static final String wfRegisterUrl = "https://workflowy.com/accounts/register/";
    public static final String tenMinutEmailUrl = "https://www.10minutemail.net/";

    @Async
    public static void process() throws Exception{
        HttpClient httpClient = new HttpClient();
        GetMethod visitEmailMethod  = new GetMethod(tenMinutEmailUrl);
        visitEmailMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT , 1000 * 60);
        try {
            if (httpClient.executeMethod(visitEmailMethod) == HttpStatus.SC_OK) {

                dealWorkFlowy(getEmailAddr(visitEmailMethod));

                Thread.sleep(1000 * 20);

                dealEmailInbox(httpClient,visitEmailMethod);
            }else{
                log.error("visitEmailMethod failed: {} " ,visitEmailMethod.getStatusLine());
            }
        } catch (Exception ex) {
            Thread.currentThread().interrupt();
            log.error("process处理失败,异常原因:{}" , ex);
        } finally {
            visitEmailMethod.releaseConnection();
        }
    }

    private static String getHtmlContent(GetMethod getMethod) throws Exception{
        InputStream inputStream = getMethod.getResponseBodyAsStream();
        byte[] bytes = IOUtils.toByteArray(inputStream);
        String content = new String(bytes);

        Precondition.checkStringArgument("getHtmlContent - content",content);

        return content;
    }

    public static String getEmailAddr(GetMethod visitEmailMethod) throws Exception {

        String emailAddr="";

        Document doc = Jsoup.parse(getHtmlContent(visitEmailMethod));

        if (!isCaptcha(doc)) {
            Element feText = doc.getElementById("fe_text");
            emailAddr = feText.val();
            System.out.println(emailAddr);
        }

        Precondition.checkStringArgument("getEmailAddr - emailAddr",emailAddr);
        return emailAddr;
    }

    private static boolean isCaptcha(Document doc) throws Exception{

        Preconditions.checkNotNull(doc);

        boolean isCaptcha = false;
        Element feText = doc.getElementById("fe_text");
        if (feText == null) {
            throw new Exception("机器人异常!"
            + "There are too many requested addresses from your IP. We need to make sure you are human. Please answer the CAPTCHA below, and click the submit button to get a confirmation.");
        }
        return isCaptcha;
    }


    public static void toExpand(HttpClient httpClient, String href) throws Exception {
        GetMethod getEmailContentMethod  = new GetMethod(tenMinutEmailUrl + href);
        if(httpClient.executeMethod(getEmailContentMethod) == HttpStatus.SC_OK){
            Document lastDoc = Jsoup.parse(getHtmlContent(getEmailContentMethod));
            Element lastElement = lastDoc.getElementById("tab1");
            Elements lastLinks  = lastElement.getElementsByTag("a");
            for (Element lastLink : lastLinks) {
                String verifyLink = lastLink.attr("href");
                if(verifyLink.contains("workflowy.com/email/verify/")){
                    if(httpClient.executeMethod(new GetMethod(verifyLink)) == HttpStatus.SC_OK){
                        System.out.println("+250扩容完成...");
                    }
                }
            }
        }else {
            log.error("打开 [验证邮件] 失败..");
        }
    }


    public static void dealEmailInbox(HttpClient httpClient,GetMethod visitEmailMethod) throws Exception {
        if(httpClient.executeMethod(visitEmailMethod) == HttpStatus.SC_OK){

            Document doc = Jsoup.parse(getHtmlContent(visitEmailMethod));

            Element element = doc.getElementById("maillist");

            Elements links  = element.getElementsByTag("a");

            for (Element link : links) {
                String text = link.text();
                if(StringUtils.equals("Please verify your email address",text)){
                    String href = link.attr("href");
//                    System.out.println(href);
                    toExpand(httpClient,href);
                    break;
                }
            }
        }else{
            log.error("进入邮箱失败 - getStatusLine:{}",visitEmailMethod.getStatusLine());
        }
    }


    public static void dealWorkFlowy(String email) throws Exception{
        HttpClient httpClient = new HttpClient();
        GetMethod  visitWorkFlowyGetMethod  = new GetMethod(wfInviteLink);
        visitWorkFlowyGetMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT , 10000);
        try {
            if (httpClient.executeMethod(visitWorkFlowyGetMethod) == HttpStatus.SC_OK) {
                GetMethod  getMethod2  = new GetMethod(wfRegisterUrl);
                if(httpClient.executeMethod(getMethod2) == HttpStatus.SC_OK){
                    PostMethod postMethod=new PostMethod(wfRegisterUrl);
                    postMethod.addParameter("email", email);
                    postMethod.addParameter("email2", email);
                    postMethod.addParameter("password", "123456");
                    httpClient.executeMethod(postMethod);
                }
            } else {
                log.error("visitWorkFlowyGetMethod failed: {} " ,visitWorkFlowyGetMethod.getStatusLine());
            }
        } catch (IOException ex) {
            log.error("dealWorkFlowy 处理失败,异常原因:{}" , ex);
            log.error("线程{}异常.." , Thread.currentThread().getName());
            Thread.currentThread().interrupt();
        } finally {
            visitWorkFlowyGetMethod.releaseConnection();
        }
    }


    public static void main(String[] args) throws Exception {
        GetFreeSpace getFreeSpace = new GetFreeSpace();
        getFreeSpace.process();
    }


}
