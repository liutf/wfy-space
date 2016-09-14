package com.lau.core.exe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liutf on 2016-09-14.
 */
public class GetFreeSpaceSchedule {

    private static Logger logger = LoggerFactory.getLogger(GetFreeSpaceSchedule.class);

    public static final int threadNum = 2;
    public static final ExecutorService executor = Executors.newFixedThreadPool(threadNum);

    public static void startProcess(){
        for (int i = 0; i < threadNum; i++) {
            executor.execute(new GetFreeSpaceRunable());
        }
    }

}
