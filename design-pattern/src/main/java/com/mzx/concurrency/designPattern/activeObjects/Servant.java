package com.mzx.concurrency.designPattern.activeObjects;

import com.mzx.concurrency.designPattern.activeObjects.base.ActiveObject;
import com.mzx.concurrency.designPattern.activeObjects.base.Result;
import com.mzx.concurrency.designPattern.activeObjects.base.impl.RealResult;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 真正执行对象
 */
class Servant implements ActiveObject<MakeData, String> {
    @Override
    public Result<String> apply(MakeData makeData) {
        char[] buffer = new char[makeData.getCount()];
        IntStream.range(0, makeData.getCount()).forEach(value -> {
            buffer[value] = makeData.getFillChar();
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
        });
        return new RealResult<>(new String(buffer));
    }

    @Override
    public void accept(MakeData makeData) {
        try {
            System.out.println("Display: " + makeData);
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Result<String> get() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        MakeData makeData = new MakeData(random.nextInt(30), (char) (33 + random.nextInt(93)));
        return new RealResult<>(makeData.toString());
    }
}
