package com.mzx.concurrency.designPattern.observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<Observer> observers = new ArrayList<>();

    private int state;

    public void setState(int state) {
        if (this.state == state){
            return;
        }
        this.state = state;
        notifyAllObserver();
    }

    public int getState() {
        return state;
    }

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObserver(){
        observers.forEach(Observer::update);
    }
}
