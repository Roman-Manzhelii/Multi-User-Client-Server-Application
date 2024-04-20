package com.dkit.oop.sd2;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.dkit.oop.sd2.DAOs.MySqlPlayerDao;
import com.dkit.oop.sd2.DTOs.Player;
import com.dkit.oop.sd2.Exceptions.DaoException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/** Main author: Annita Mila Chuenglin */
public class Server {
    final int PORT_NUMBER = 8888;
    private final MySqlPlayerDao playerDao;
    private final JsonConverter jsonConverter;

    public Server() {
        this.playerDao = new MySqlPlayerDao();
        this.jsonConverter = new JsonConverter();
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.start();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void start() throws DaoException {
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
            System.out.println("The Server has started and is waiting for a client to connect.");
            System.out.flush();
            try (
                    Socket clientSocket = serverSocket.accept();
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
            ) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("The Server has received this message from a client: " + inputLine);
                    String[] request = inputLine.split(" ", 2);
                switch (request[0]) {
                    /** Main author: Annita Mila Chuenglin */
                    case "displayEntityById":
                        int entityId = Integer.parseInt(request[1]);
                        Player player = playerDao.findPlayerById(entityId);
                        String jsonPlayer = jsonConverter.playerToJson(player);
                        out.println(jsonPlayer);
                        break;
                    /** Main author: Annita Mila Chuenglin */
                    case "displayAllEntities":
                        List<Player> allPlayersList = playerDao.getAllPlayers();
                        String allPlayers = jsonConverter.playersListToJson(allPlayersList);
                        out.println(allPlayers);
                        break;
                    /** Main author: Annita Mila Chuenglin */
                    case "deleteEntityById":
                        int deleteId = Integer.parseInt(request[1]);
                        Player deletedPlayer = playerDao.deletePlayer(deleteId);
                        if (deletedPlayer != null) {
                            out.println("Player with ID " + deleteId + " deleted");
                        } else {
                            out.println("Player with ID " + deleteId + " not found.");
                        }
                        break;
                    /** Main author: Elga Jerusha Henry */
                    case "addEntity":
                        String jsonRequest = dis.readUTF();
                        try {
                            System.out.println("Attempting to parse JSON: " + jsonRequest);
                            Player newPlayer = JsonConverter.jsonToPlayer(jsonRequest);
                            Player addedPlayer = playerDao.insertPlayer(newPlayer);
                            if (addedPlayer != null) {
                                String jsonResponse = JsonConverter.playerToJson(addedPlayer);
                                dos.writeUTF(jsonResponse);
                            } else {
                                dos.writeUTF("Failed to add the entity");
                            }
                        } catch (JsonSyntaxException ex) {
                            System.out.println("JSON parsing error: " + ex.getMessage());
                            dos.writeUTF("Invalid JSON format: " + ex.getLocalizedMessage());
                        } catch (Exception e) {
                            System.out.println("Error parsing JSON: " + jsonRequest + "; Error: " + e.getMessage());
                            dos.writeUTF("Invalid JSON format");
                        }
                        dos.flush();
                        break;
                    /** Main author: Roman Manzhelii*/
                    case "getImagesList":
                        File folder = new File("images");
                        File[] listOfFiles = folder.listFiles();
                        Gson gson = new Gson();
                        if (listOfFiles != null) {
                            List<String> fileList = Arrays.stream(listOfFiles)
                                    .filter(File::isFile)
                                    .map(File::getName)
                                    .collect(Collectors.toList());
                            String json = gson.toJson(fileList);
                            out.println(json);
                        } else {
                            out.println(gson.toJson("No images found."));
                        }
                        break;
                    /** Main author: Roman Manzhelii*/
                    case "getFile":
                        String fileName = request[1];
                        sendFile(fileName, clientSocket);
                        break;
                    default:
                        out.println("Unrecognised input");
                        break;
                }
                }
                System.out.println("The Server is finished, and is exiting. Goodbye!");
            }
        } catch (IOException exception) {
            System.out.println("Exception: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    /** Main author: Roman Manzhelii*/
    private void sendFile(String fileName, Socket socket) throws IOException {
        File file = new File("images/" + fileName);
        if (!file.exists()) {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("File not found");
            return;
        }
        long fileLength = file.length();
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("OK");
        dos.writeLong(fileLength);

        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = bis.read(buffer)) != -1) {
            dos.write(buffer, 0, bytesRead);
        }
        bis.close();
        dos.flush();
        System.out.println("Sent file: " + fileName);
    }

}
