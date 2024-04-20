package com.dkit.oop.sd2;
import com.dkit.oop.sd2.DTOs.Player;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

/** Main author: Annita Mila Chuenglin */
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
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream()); // Додано для addEntity
                DataInputStream dis = new DataInputStream(socket.getInputStream()); // Додано для addEntity
                Scanner scanner = new Scanner(System.in)) {

            System.out.println("The client is running and has connected to the server.");

            while(true){
                System.out.println("====== Menu ======");
                System.out.println("1. Display Entity by Id");
                System.out.println("2. Display All Entities");
                System.out.println("3. Add an Entity");
                System.out.println("4. Delete an Entity by Id");
                System.out.println("5. Get Images List");
                System.out.println("6. Exit");
                System.out.print("Enter option: ");

                int option = scanner.nextInt();
                scanner.nextLine();

                switch(option) {
                    /** Main author: Annita Mila Chuenglin */
                    case 1:
                        System.out.println("Enter the entity ID to display: ");
                        int entityId = scanner.nextInt();
                        scanner.nextLine();
                        out.println("displayEntityById "+entityId);
                        String response = in.readLine();
                        System.out.println("In client: The server response was : " + response);
                        break;
                    /** Main author: Annita Mila Chuenglin */
                    case 2:
                        System.out.println("Displaying all entities.");
                        out.println("displayAllEntities");
                        response = in.readLine();
                        System.out.println("In client: The server response was : " + response);
                        break;
                    /** Main author: Elga Jerusha Henry */
                    case 3:
                        System.out.println("Enter the details of the entity:");
                        System.out.print("Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Age: ");
                        int age = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Team: ");
                        String team = scanner.nextLine();
                        System.out.print("Position: ");
                        String position = scanner.nextLine();

                        Player newPlayer = new Player(name, age, team, position);
                        String jsonRequest = JsonConverter.playerToJson(newPlayer);
                        out.println("addEntity ");
                        dos.writeUTF(jsonRequest);
                        dos.flush();

                        String addResponse = dis.readUTF();
                        System.out.println("Server response: " + addResponse);
                        break;

                    case 4:
                        System.out.println("Enter the entity ID to delete: ");
                        entityId = scanner.nextInt();
                        scanner.nextLine();
                        out.println("deleteEntityById "+entityId);
                        response = in.readLine();
                        System.out.println("Player successfully deleted");
                        break;
                    /** Main author: Roman Manzhelii*/
                    case 5:
                        out.println("getImagesList");
                        Gson gson = new Gson();
                        String jsonResponse = in.readLine();

                        //System.out.println("Raw JSON response from server: " + jsonResponse);

                        Type type = new TypeToken<List<String>>() {}.getType();
                        List<String> imagesList = gson.fromJson(jsonResponse, type);

                        if (imagesList.isEmpty()) {
                            System.out.println("No images found.");
                        } else {
                            System.out.println("Available images: ");
                            imagesList.forEach(System.out::println);
                        }

                        System.out.print("Enter the name of the image to download: ");
                        String imageName = scanner.nextLine();
                        out.println("getFile " + imageName);
                        receiveFile(imageName, socket);
                        break;

                    /** Main author: Elga Jerusha Henry*/
                    case 6:
                        System.out.println("Exiting the client. Goodbye!");
                        socket.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 to 6.");
                }
            }

        } catch (UnknownHostException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /** Main author: Roman Manzhelii*/
    private void receiveFile(String fileName, Socket socket) throws IOException {
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        String status = dis.readUTF();
        if (!"OK".equals(status)) {
            System.out.println(status);
            return;
        }
        long fileLength = dis.readLong();
        FileOutputStream fos = new FileOutputStream("downloaded/" + fileName);
        byte[] buffer = new byte[4096];
        int bytesRead;
        long totalRead = 0;
        while (totalRead < fileLength && (bytesRead = dis.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
            totalRead += bytesRead;
        }
        fos.close();
        if (totalRead == fileLength) {
            System.out.println("Received file: " + fileName);
        } else {
            System.out.println("File transfer incomplete. Expected " + fileLength + " bytes, received " + totalRead + " bytes.");
        }
    }
}
