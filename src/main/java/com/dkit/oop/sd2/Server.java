package com.dkit.oop.sd2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.dkit.oop.sd2.DAOs.MySqlPlayerDao;
import com.dkit.oop.sd2.DTOs.Player;
import com.dkit.oop.sd2.Exceptions.DaoException;

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
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
             Socket clientSocket = serverSocket.accept();
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            System.out.println("The Server server has started and is waiting for a client to connect.");

            String message = in.readLine();

            System.out.println("The Server has received this message from a client: " + message);
            System.out.println("The server is replying to the client.");

            String[] request = message.split(" ");
            /** Main author: Annita Mila Chuenglin */
            if(request[0].equals("displayEntityById")) {
                int entityId = Integer.parseInt(request[1]);
                Player player = playerDao.findPlayerById(entityId);
                String jsonPlayer = jsonConverter.playerToJson(player);
                out.println(jsonPlayer);
            }
            /** Main author: Annita Mila Chuenglin */
            else if(request[0].equals("displayAllEntities")) {
                List<Player> allPlayersList = playerDao.getAllPlayers();
                String allPlayers = jsonConverter.playersListToJson(allPlayersList);
                out.println(allPlayers);
            }
            else {
                out.println("Unrecognised input");
            }

            System.out.println("The Server is finished, and is exiting. Goodbye!");

        } catch (IOException exception) {
            System.out.println(exception);
        }
    }
}
