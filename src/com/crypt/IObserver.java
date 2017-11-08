package com.crypt;

interface IObserver {
    void sendToWorkers(String message);
    void addWorker(IObservable iobs);
}
