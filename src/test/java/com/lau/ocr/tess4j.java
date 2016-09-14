package com.lau.ocr;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.Test;

import java.io.File;


/**
 * Created by liutf on 2016-09-14.
 */
public class tess4j {

    @Test
    public void test() {
        File imageFile = new File("D:/dev_tool/captcha/randomImgCodeServlet.jpg");
        Tesseract tessreact = new Tesseract();
        tessreact.setDatapath("D:/dev_tool/tessdata/tessdata");
        try {
            String result = tessreact.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }

}
