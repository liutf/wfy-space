package com.lau.core.exe;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static com.lau.core.service.GetFreeSpace.*;

/**
 * Created by liutf on 2016-09-14.
 */

@Component("mTask")
@Scope("prototype")
public class GetFreeSpaceRunable implements Runnable {

    private static Logger log = LoggerFactory.getLogger(GetFreeSpaceRunable.class);


    @Override
    public void run() {
        while (true) {
            log.info("线程:" + Thread.currentThread().getName() + "运行中.....");
            GetMethod visitEmailMethod = null;
            try {
//                GetFreeSpace.process();
                visitEmailMethod = new GetMethod(tenMinutEmailUrl);
                visitEmailMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT , 1000 * 60);

                process(visitEmailMethod);

            } catch (Exception e) {
                Thread.currentThread().interrupt();
            } finally {
                visitEmailMethod.releaseConnection();
            }

        }
    }

    @Async
    public static void process(GetMethod visitEmailMethod) throws Exception {
        HttpClient httpClient = new HttpClient();
        if (httpClient.executeMethod(visitEmailMethod) == HttpStatus.SC_OK) {

            dealWorkFlowy(getEmailAddr(visitEmailMethod));

            Thread.sleep(1000 * 20);

            dealEmailInbox(httpClient, visitEmailMethod);
        } else {
            log.error("visitEmailMethod failed: {} ", visitEmailMethod.getStatusLine());
        }
    }

}
