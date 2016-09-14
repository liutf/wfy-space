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
        boolean isRun = true;
        while (isRun) {
            log.error("线程: [{}] 运行中.....",Thread.currentThread().getName());
            GetMethod visitEmailMethod = null;
            try {
//                GetFreeSpace.process();
                visitEmailMethod = new GetMethod(tenMinutEmailUrl);
                visitEmailMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT , 1000 * 60);

                process(visitEmailMethod);

            } catch (Exception e) {
                log.error("线程 [{}] 处理异常.异常原因: ",Thread.currentThread().getName(),e);
                isRun = false;
                Thread.currentThread().interrupt();
                log.error("线程 [{}] 终止..",Thread.currentThread().getName());
            } finally {
                visitEmailMethod.releaseConnection();
                log.error("关闭连接..");
            }
        }
    }

    @Async
    public static void process(GetMethod visitEmailMethod) throws Exception {
        HttpClient httpClient = new HttpClient();
        if (httpClient.executeMethod(visitEmailMethod) == HttpStatus.SC_OK) {

            dealWorkFlowy(getEmailAddr(visitEmailMethod));

            Thread.sleep(1000 * 10);

            dealEmailInbox(httpClient, visitEmailMethod);
        } else {
            log.error("visitEmailMethod failed: {} ", visitEmailMethod.getStatusLine());
        }
    }

}
