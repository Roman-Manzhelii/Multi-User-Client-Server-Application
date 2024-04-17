package com.dkit.oop.sd2;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Client client = new Client();
        client.start(8888);
    }

    public void start(int portNumber) {
        try (
                Socket socket = new Socket("localhost", 8888);
                OutputStream outputStream = socket.getOutputStream();
                PrintWriter out = new PrintWriter(outputStream, true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in)) {

            System.out.println("The client is running and has connected to the server.");

            while(true){
                System.out.println("====== Menu ======");
                System.out.println("1. Send 'hello server'");
                System.out.println("2. Exit");
                System.out.print("Enter option: ");

                int option = scanner.nextInt();
                scanner.nextLine();

                switch(option) {
                    case 1:
                        out.println("hello server");
                        String response = in.readLine();
                        System.out.println("In client: The server response was : " + response);
                        break;
                    case 2:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            }

        } catch (UnknownHostException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
