package com.lau.core;

import com.github.kevinsawicki.http.HttpRequest;
import com.lau.utils.HttpUtil;

import java.io.IOException;

/**
 * Created by liutf on 2016-09-12.
 */
public class GetFreeSpace {

    private String WF_INVIETE_URL = "https://workflowy.com/invite/3bf81761.emlx";
    private String WF_REGISTER_URL = "https://workflowy.com/accounts/register/";
    private String TEN_MINUT_EMAIL_URL = "https://10minutemail.org/";

    public void toExtend() throws Exception{

        getInvitePage();

    }

    public void getInvitePage() throws IOException {
        HttpUtil.httpGetByUrl(WF_INVIETE_URL);
    }

    public void regist(){

    }

    public String getEmailAcct() throws IOException {
        String emailAcct = "";
//        String content = HttpUtil.httpGetByUrl(TEN_MINUT_EMAIL_URL);
        String content = HttpRequest.get(TEN_MINUT_EMAIL_URL).body();
        System.out.println(content);
        String content1=new String(content);
        int index=content1.indexOf("<div class=\"div-m-0 text-c\">");
        String tmp=content1.substring(index);
        int index2=tmp.indexOf("</div>");
        String tmp2=tmp.substring(0,index2);
        String emailtmp=tmp2.substring(tmp.indexOf("value=")+7,tmp.indexOf(" />")-1);
        System.out.println(emailtmp);
        return emailAcct;
    }




    public static void main(String[] args) throws IOException {
        GetFreeSpace getFreeSpace = new GetFreeSpace();
        getFreeSpace.getEmailAcct();
    }


}
