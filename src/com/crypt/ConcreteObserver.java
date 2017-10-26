package com.crypt;

import java.util.ArrayList;
import java.util.*;

public class ConcreteObserver implements IObserver {
    List<Observable> workerList =  new ArrayList<>();

    @Override
    public void update(IObservable o, Object arg) {

    }
    public void sendToWorker(String msg) {

    }
}
