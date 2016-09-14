package com.lau.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by liutf on 2016-09-13.
 */
public class jsoupTest {

    @Test
    public void test() throws Exception{

//        String url = "https://10minutemail.org/";
//        Document doc = Jsoup.connect(url).get();
        String html = "<html><head><title>First parse</title></head>"
                + "<body>" +
                "<div class=\"div-m-0 text-c\">\n" +
                "                                    <input type=\"text\" id=\"fe_text\" class=\"mailtext\" value=\"fnq39153@ploae.com\" />\n" +
                "                                </div>" +
                "</body></html>";

        Document doc = Jsoup.parse(html);

        System.out.println(doc.title());

        Element element = doc.getElementById("fe_text");
        System.out.println(element.val());

    }

    @Test
    public void getEmail() throws IOException {
        String url = "https://10minutemail.net/";
        Document doc = Jsoup.connect(url).get();
        Element element = doc.getElementById("fe_text");
        System.out.println(element.val());
    }

    @Test
    public void get(){
        String htmlStr = "<table id=\"maillist\">" +
                "<tbody>" +
                "<tr>" +
                "<th>寄件人</th><th>主题</th><th>收件日期</th></tr>" +
                "<tr onclick=\"location='readmail.html?mid=welcome'\">" +
                "<td>no-reply@10minutemail.net</td>" +
                "<td><a href=\"readmail.html?mid=welcome\">嗨，欢迎来到10分钟邮箱！</a></td>" +
                "<td><span title=\"2016-09-13 13:39:11 UTC\">2 分钟 之前</span></td></tr></tbody>" +
                "</table>";

        Document doc = Jsoup.parse(htmlStr);
        Element element = doc.getElementById("maillist");
        Elements links  = element.getElementsByTag("a");
        for (Element link : links) {
            System.out.println(link.attr("href"));
        }

    }

}
