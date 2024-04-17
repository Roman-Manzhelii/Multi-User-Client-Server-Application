package com.dkit.oop.sd2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final int PORT_NUMBER = 8888;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
             Socket clientSocket = serverSocket.accept();
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            System.out.println("The Server server has started and is waiting for a client to connect.");

            String message = in.readLine();

            System.out.println("The Server has received this message from a client: " + message);
            System.out.println("The server is replying to the client.");

            if ("hello server".equals(message)) {
                out.println("hello client");
            } else {
                out.println("unrecognised greeting");
            }

            System.out.println("The Server is finished, and is exiting. Goodbye!");

        } catch (IOException exception) {
            System.out.println(exception);
        }
    }

}
