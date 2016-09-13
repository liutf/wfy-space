package com.lau.core;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.lau.utils.MyHttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by liutf on 2016-09-12.
 */
public class GetFreeSpace {

    private String invieteUrl = "https://workflowy.com/invite/3bf81761.emlx";
    private String registerUrl = "https://workflowy.com/accounts/register/";
    private String getEmailUrl = "https://10minutemail.net/";

    public void toExtend() throws Exception{

        String emailAcct = getEmailAcct();

        getInvitePage();

        Thread.sleep(20000);
    }


    public void getInvitePage() throws IOException {
//        HttpUtil.httpGetByUrl(invieteUrl);
        HttpUtil.doGet(invieteUrl);
    }

    public void doRegist(String emailAddr){
        HashMap<String, String> paraMap = Maps.newHashMap();
        paraMap.put("email",emailAddr);
        paraMap.put("email2",emailAddr);
        paraMap.put("password","123456");
        MyHttpUtil.doPost(registerUrl,paraMap,false);
    }

    public String getEmailAcct() throws IOException {
        String htmlContent = new String(HttpUtil.doGet(getEmailUrl));
//        System.out.println(htmlContent);
        Document doc = Jsoup.parse(htmlContent);
        String emailAddr = doc.getElementById("fe_text").val();
        System.out.println(emailAddr);
        return emailAddr;
    }


    public static void main(String[] args) throws Exception {
        GetFreeSpace getFreeSpace = new GetFreeSpace();
        String emailAcct = getFreeSpace.getEmailAcct();

        getFreeSpace.getInvitePage();

        getFreeSpace.doRegist(emailAcct);

        Thread.sleep(20000);

        byte[] bytes = HttpUtil.doGet("https://10minutemail.net/");
        String htmlContent = new String(bytes, Charsets.UTF_8);
        Document doc = Jsoup.parse(htmlContent);
        Elements elements = doc.select("a[href]");
        for (Element element : elements) {
            System.out.println(element.attr("href"));
        }

    }


}
