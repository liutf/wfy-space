package com.lau.ocr;

import com.asprise.ocr.Ocr;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by liutf on 2016-09-14.
 */
public class AspriseOcrTest {

    @Test
    public void test() throws IOException {

//        String imgPath = "D:/dev_tool/validcode.jpg";
//        String imgPath = "D:/dev_tool/12152520_7R29.jpg";
        String imgPath = "D:/dev_tool/captcha/randomImgCodeServlet.jpg";

        Ocr.setUp(); // one time setup
        Ocr ocr = new Ocr(); // create a new OCR engine
        ocr.startEngine("eng", Ocr.SPEED_FASTEST); // English
        String s = ocr.recognize(new File[] {new File(imgPath)},
                Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT); // PLAINTEXT | XML | PDF | RTF
        System.out.println("Result: " + s);
        ocr.stopEngine();


    }

}
