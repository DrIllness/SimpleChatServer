package com.crypt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.*;


public class ConcreteObserver implements IObserver {
    private List<Worker> workerList =  new ArrayList<>();
    private volatile boolean notified = false;



    public synchronized void setNotified(boolean b) {
        this.notified = b;
    }

    public synchronized void sendToWorkers(String msg) {
        for (Worker worker : workerList) {
            worker.messQueueOut.add(msg);
            setNotified(false);
        }
    }

    public void addWorker(IObservable iobs) {
        if (iobs == null)
            throw new NullPointerException();
        if (!workerList.contains(iobs)) {
            workerList.add((Worker)iobs);
        }
    }
}
