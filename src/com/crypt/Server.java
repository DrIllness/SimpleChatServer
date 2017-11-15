package com.crypt;
import com.sun.javafx.property.adapter.PropertyDescriptor;
import javafx.beans.Observable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    static ConcreteObserver observer = new ConcreteObserver();
    private static final Server SERVER = new Server();
    private int serverPort = 8080;
    private boolean isStopped = false;
    private ExecutorService threadPool = Executors.newCachedThreadPool();
    private ServerSocket serverSocket = null;


    public static void main(String[] args) {
        SERVER.openServerSocket();
        SERVER.start();
    }

    private void start() {
        while (!isStopped) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException ex) {
                System.out.println("Failed to accept client connection");
            }
            makeNewWorker(clientSocket, observer);
            System.out.println();
        }
    }

    private void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException ex) {
            System.out.println("Failed to shut down the server");
        }
    }

    private void makeNewWorker(Socket socket, ConcreteObserver obs) {
        Worker worker = new Worker(socket, obs);
        this.threadPool.execute(worker);
        observer.addWorker(worker);
    }

    private boolean isStopped() {
        return this.isStopped;
    }


    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }
}
