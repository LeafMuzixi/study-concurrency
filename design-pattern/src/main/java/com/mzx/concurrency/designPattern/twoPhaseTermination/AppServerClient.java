package com.mzx.concurrency.designPattern.twoPhaseTermination;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AppServerClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        AppServer appServer = new AppServer(13345);
        appServer.start();
        TimeUnit.SECONDS.sleep(5);
        appServer.shutdown();
    }
}
