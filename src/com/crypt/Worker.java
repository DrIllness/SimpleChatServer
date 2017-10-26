package com.crypt;

import java.net.Socket;

public class Worker implements IObservable, Runnable {
    private Socket clientSocket = null;
    private ConcreteObserver observer = null;

    Worker(Socket clientSocket, ConcreteObserver observer) {
        this.clientSocket = clientSocket;
        this.observer = observer;
    }

    @Override
    public void run() {}

    @Override
    public void addObserver(IObserver obs) {}

    @Override
    public void removeObserver(IObserver obs) {}

    public void notifyObserver(IObserver obs, String msg) {}

}
