package com.crypt;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Server SERVER = new Server();
    private int serverPort = 8080;
    private boolean isStopped = false;
    private ExecutorService threadPool = Executors.newCachedThreadPool();
    ConcreteObserver observer = null;
    private ServerSocket serverSocket = null;


    public static void main(String[] args) {

    }


    private void start(){
        while (!isStopped){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException ex) {
                System.out.println("Failed to accept client connection");
            }
            this.threadPool.execute(new Worker(clientSocket, this.observer));



        }

    }

    private void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException ex) {
            System.out.println("Failed to shut down the server");
        }

    }

    private boolean isStopped() {
        return this.isStopped;
    }


}
