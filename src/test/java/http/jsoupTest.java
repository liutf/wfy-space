package http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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

}
