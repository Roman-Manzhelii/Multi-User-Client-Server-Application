package com.dkit.oop.sd2.BusinessObjects;

import com.dkit.oop.sd2.DAOs.MySqlPlayerDao;
import com.dkit.oop.sd2.DAOs.PlayerDaoInterface;
import com.dkit.oop.sd2.DTOs.Player;
import com.dkit.oop.sd2.Exceptions.DaoException;
import com.dkit.oop.sd2.Exceptions.PlayerAgeComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        PlayerDaoInterface IPlayerDao = new MySqlPlayerDao();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n*** Player Management System ***");
            System.out.println("1. View All Players");
            System.out.println("2. Find Player by ID");
            System.out.println("3. Delete Player by ID");
            System.out.println("4. Insert a Player");
            System.out.println("5. Update a Player by ID");
            System.out.println("6. Compare Players by Age");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // View all players
                    try {
                        List<Player> players = IPlayerDao.getAllPlayers();
                        if (players.isEmpty()) {
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
                    // Find a player by ID
                    try {
                        System.out.println("Enter Player ID to find: ");
                        int playerIdToFind = scanner.nextInt();
                        Player foundPlayer = IPlayerDao.findPlayerById(playerIdToFind);
                        if (foundPlayer != null) {
                            System.out.println("Player found: " + foundPlayer.toString());
                        } else {
                            System.out.println("Player with ID " + playerIdToFind + " not found.");
                        }
                    } catch (DaoException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    // Delete a player by ID
                    try {
                        scanner.nextLine();

                        System.out.println("Enter Player ID to Delete: ");
                        int playerIdToDelete = scanner.nextInt();

                        if (!IPlayerDao.exists(playerIdToDelete)) {
                            System.out.println("Player with ID " + playerIdToDelete + " does not exist. Returning to the main menu.");
                            break;
                        }

                        IPlayerDao.deletePlayer(playerIdToDelete);
                        System.out.println("Player with ID " + playerIdToDelete + " deleted successfully!");
                    } catch (DaoException e) {
                        System.out.println("An error occurred: " + e.getMessage());
                    }
                    break;
                case 4:
                    // Insert a new player
                    try {
                        scanner.nextLine(); // Clearing the input buffer

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
                case 5:
                    try {
                        scanner.nextLine();

                        System.out.println("Enter Player ID to Update: ");
                        int playerId = scanner.nextInt();

                        if (!IPlayerDao.exists(playerId)) {
                            System.out.println("Player with ID " + playerId + " does not exist. Returning to the main menu.");
                            break;
                        }
                        scanner.nextLine();

                        System.out.println("Enter New Player Name: ");
                        String newName = scanner.nextLine();

                        System.out.println("Enter New Age: ");
                        int newAge = scanner.nextInt();

                        scanner.nextLine();

                        System.out.println("Enter New Team: ");
                        String newTeam = scanner.nextLine();

                        System.out.println("Enter New Position: ");
                        String newPosition = scanner.nextLine();

                        Player updatedPlayer = new Player(newName, newAge, newTeam, newPosition);
                        IPlayerDao.updatePlayer(playerId, updatedPlayer);
                        System.out.println("Player updated successfully!");
                    } catch (DaoException e) {
                        System.out.println("An error occurred: " + e.getMessage());
                    }
                    break;
                case 6:
                    // Filter players by age
                    try {
                        System.out.println("Enter the age to filter players: ");
                        int ageToFilter = scanner.nextInt();

                        List<Player> allPlayers = IPlayerDao.getAllPlayers();
                        List<Player> filteredPlayers = new ArrayList<>();

                        for (Player player : allPlayers) {
                            if (player.getAge() == ageToFilter) {
                                filteredPlayers.add(player);
                            }
                        }

                        if (filteredPlayers.isEmpty()) {
                            System.out.println("No players found with the specified age.");
                        } else {
                            System.out.println("Filtered Players:");
                            for (Player player : filteredPlayers) {
                                System.out.println("Player: " + player.toString());
                            }
                        }
                    } catch (DaoException e) {
                        e.printStackTrace();
                    }
                    break;





                case 7:
                    // Exit
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice, please enter a valid option.");
                    break;
            }
        }
    }
}