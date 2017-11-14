package com.crypt;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Worker implements IObservable, Runnable {
    public ConcurrentLinkedQueue<String> messQueueOut = new ConcurrentLinkedQueue<>();
    private Socket clientSocket = null;
    private ConcreteObserver observer = null;
    private Worker worker = null;

    Worker(Socket clientSocket, ConcreteObserver observer) {
        this.clientSocket = clientSocket;
        this.observer = observer;
    }

    @Override
    public void run() {
        try {
            InputStream sin = clientSocket.getInputStream();
            OutputStream sout = clientSocket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            CircularFifoQueue<HashMap<String, String>> history = Server.observer.getHistory();
            while (!history.isEmpty()) {
                out.writeUTF(history.poll().toString());
                out.flush();
            }

            Thread listen = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String message;
                            message = in.readUTF();
                            notifyObserver(Server.observer, message);
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
   @Override
    public void addObserver(IObserver obs) {}
    @Override
    public void removeObserver(IObserver obs) {}

    public void notifyObserver(IObserver obs, String msg) {
        obs.sendToWorkers(msg);
    }
}
