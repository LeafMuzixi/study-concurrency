package com.mzx.concurrency.designPattern.activeObjects;

import com.mzx.concurrency.designPattern.activeObjects.base.ActiveObject;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class DisplayClientThread extends Thread {
    private final ActiveObject<MakeData, String> activeObject;

    public DisplayClientThread(String name, ActiveObject<MakeData, String> activeObject) {
        super(name);
        this.activeObject = activeObject;
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            try {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                activeObject.accept(new MakeData(random.nextInt(10), (char) (65 + random.nextInt(26))));
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }
}
