package com.crypt;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.ArrayList;
import java.util.*;


public class ConcreteObserver implements IObserver {
    private List<Worker> workerList =  new ArrayList<>();
    private CircularFifoQueue<HashMap<String, String>> history = new CircularFifoQueue<>(10);

    public synchronized void sendToWorkers(String msg) {
        store(msg);
        for (Worker worker : workerList) {
            worker.messQueueOut.add(msg);
        }
    }

    public void addWorker(IObservable iobs) {
        if (iobs == null)
            throw new NullPointerException();
        if (!workerList.contains(iobs)) {
            workerList.add((Worker)iobs);
        }
    }

    public CircularFifoQueue<HashMap<String, String>> getHistory() {
        if (!history.isEmpty())
            return new CircularFifoQueue<>(this.history);
        else
            return this.history;
    }

    private void store(String mes) {
        HashMap<String, String> pair = new HashMap<>();
        pair.put("Test", mes);
        history.add(pair);
    }
}
