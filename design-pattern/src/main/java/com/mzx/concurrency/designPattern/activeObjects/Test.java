package com.mzx.concurrency.designPattern.activeObjects;

import com.mzx.concurrency.designPattern.activeObjects.base.ActiveObject;
import com.mzx.concurrency.designPattern.activeObjects.base.ActiveObjectFactory;

public class Test {
    public static void main(String[] args) {
        ActiveObject<MakeData, String> activeObject = ActiveObjectFactory.createActiveObject(new Servant());
        new MakerClientThread("Alice", activeObject, 'A').start();
        new MakerClientThread("Bobby", activeObject, 'B').start();

        new DisplayClientThread("Chris", activeObject).start();
    }
}
