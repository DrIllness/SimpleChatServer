package com.crypt;

public class Application {
    private static final Server SERVER = new Server();

    public static void main(String [] args) {
        SERVER.startServer();
    }
}
