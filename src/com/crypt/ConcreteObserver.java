package com.crypt;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.ArrayList;
import java.util.*;


public class ConcreteObserver implements IObserver {
    private List<Worker> workerList =  new ArrayList<>();
    private CircularFifoQueue<HashMap<Date, String>> history = new CircularFifoQueue<>(10);

    public synchronized void sendToWorkers(String msg) {
        store(msg);
        for (Worker worker : workerList) {
            worker.messQueueOut.add(msg);
        }
    }
    @Override
    public void addWorker(IObservable iobs) {
        if (iobs == null)
            throw new NullPointerException();
        if (!workerList.contains(iobs)) {
            workerList.add((Worker)iobs);
        }
    }

    public CircularFifoQueue<HashMap<Date, String>> getHistory() {
        if (!history.isEmpty())
            return new CircularFifoQueue<>(this.history);
        else
            return this.history;
    }

    private void store(String mes) {
        HashMap<Date, String> pair = new HashMap<>();
        pair.put(new Date(), mes);
        history.add(pair);
    }
}
