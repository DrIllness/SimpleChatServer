package com.crypt;

interface IObservable {
    void addObserver(IObserver obs);
    void removeObserver(IObserver obs);
    void notifyObserver(IObserver obs, String message);
}


