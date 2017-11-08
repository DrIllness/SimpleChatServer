package com.crypt;

import sun.util.calendar.BaseCalendar;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Formatter;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Worker implements IObservable, Runnable {
    public ConcurrentLinkedQueue<String> messQueueOut = new ConcurrentLinkedQueue<>();
    private static int wCounter = 0;
    private Socket clientSocket = null;
    private ConcreteObserver observer = null;
    private Worker worker = null;
    static Date date = new Date();

    Worker(Socket clientSocket, ConcreteObserver observer) {
        this.clientSocket = clientSocket;
        this.observer = observer;
        this.wCounter++;
    }

    @Override
    public void run() {
        try {
            InputStream sin = clientSocket.getInputStream();
            OutputStream sout = clientSocket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            Thread listen = new Thread(new Runnable() {
                @Override
                public void run() {

                    while (true) {
                        try {
                            String line = null;
                            line = "[" + date + "]" + in.readUTF();

                            notifyObserver(observer, line);
                        } catch (IOException ex) {
                        }
                    }
                }
            });
            listen.setDaemon(true);
            listen.start();

            while (true) {
                while (!this.messQueueOut.isEmpty()) {
                    System.out.println("Have message to send:");
                    out.writeUTF(messQueueOut.poll());
                    out.flush();
                    System.out.println("Waiting for the next line...");
                    System.out.println();
                }
            }
          } catch(Exception x) { x.printStackTrace(); }
}

    public void sendToClient() {
        String message = messQueueOut.poll();
    }

    @Override
    public void addObserver(IObserver obs) {
    }

    @Override
    public void removeObserver(IObserver obs) {}

    public void notifyObserver(IObserver obs, String msg) {
        observer.sendToWorkers(msg);
    }
}
