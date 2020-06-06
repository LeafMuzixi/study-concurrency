package com.mzx.concurrency.designPattern.activeObjects;

import com.mzx.concurrency.designPattern.activeObjects.base.ActiveObject;
import com.mzx.concurrency.designPattern.activeObjects.base.Result;

import java.util.concurrent.TimeUnit;

public class MakerClientThread extends Thread {
    private final ActiveObject<MakeData, String> activeObject;

    private final char fillChar;

    public MakerClientThread(String name, ActiveObject<MakeData, String> activeObject, char fillChar) {
        super(name);
        this.activeObject = activeObject;
        this.fillChar = fillChar;
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            try {
                Result<String> result = activeObject.apply(new MakeData(i + 1, fillChar));
                TimeUnit.MILLISECONDS.sleep(200);
                System.out.println(Thread.currentThread().getName() + " : value = " + result.getResultValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }
}
