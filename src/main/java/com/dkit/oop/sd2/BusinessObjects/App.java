package com.dkit.oop.sd2.BusinessObjects;

import com.dkit.oop.sd2.DAOs.MySqlPlayerDao;
import com.dkit.oop.sd2.DAOs.PlayerDaoInterface;
import com.dkit.oop.sd2.DTOs.Player;
import com.dkit.oop.sd2.Exceptions.DaoException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        PlayerDaoInterface IPlayerDao = new MySqlPlayerDao();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n*** Player Management System ***");
            System.out.println("1. View All Players");
            System.out.println("2. Insert a Player");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    try {
                        List<Player> players = IPlayerDao.getAllPlayers();
                        if(players.isEmpty()) {
                            System.out.println("There are no Players");
                        } else {
                            for (Player player : players) {
                                System.out.println("Player: " + player.toString());
                            }
                        }
                    } catch (DaoException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        scanner.nextLine(); // Clearing the input buffer after the last use nextInt()

                        System.out.println("Enter Player Name: ");
                        String name = scanner.nextLine();

                        System.out.println("Enter Age: ");
                        int age = scanner.nextInt();

                        scanner.nextLine(); // Clearing the input buffer

                        System.out.println("Enter Team: ");
                        String team = scanner.nextLine();

                        System.out.println("Enter Position: ");
                        String position = scanner.nextLine();

                        Player newPlayer = new Player(name, age, team, position);
                        IPlayerDao.insertPlayer(newPlayer);
                        System.out.println("Player inserted successfully!");
                    } catch (DaoException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice, please enter 1, 2, or 3.");
                    break;
            }
        }
    }
}
