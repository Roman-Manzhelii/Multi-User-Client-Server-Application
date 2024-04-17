package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Player;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MySqlPlayerDao extends MySqlDao implements PlayerDaoInterface
{
    @Override
    public List<Player> getAllPlayers() throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Player> playersList = new ArrayList<>();

        try
        {
            connection = this.getConnection();

            String query = "SELECT * FROM player";
            preparedStatement = connection.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                int playerId = resultSet.getInt("id");
                String playerName = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String team = resultSet.getString("team");
                String position  = resultSet.getString("position");
                Player u = new Player(playerId, playerName, age, team, position);
                playersList.add(u);
            }
        } catch (SQLException e)
        {
            throw new DaoException("getAllPlayersResultSet() " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("getAllPlayers() " + e.getMessage());
            }
        }
        return playersList;     // may be empty
    }

    /** Main author: Mila Annita Chuenglin*/
    @Override
    public Player findPlayerById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Player player = null;

        try {
            connection = this.getConnection();

            String query = "SELECT * FROM player WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
                int playerId = resultSet.getInt("id");
                String playerName = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String team = resultSet.getString("team");
                String position  = resultSet.getString("position");
                player = new Player(playerId, playerName, age, team, position);
            } else {
                throw new DaoException("Player with ID " + id + " not found.");
            }
    
        } catch (SQLException e) {
            throw new DaoException("findPlayerById() " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) freeConnection(connection);
            } catch (SQLException e) {
                throw new DaoException("findPlayerById() " + e.getMessage());
            }
        }
        return player;
    }

    /** Main author: Elga Jerusha Henry
     */
    @Override
    public Player deletePlayer(int id) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = this.getConnection();
            String query = "DELETE FROM player WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0)
            {
                throw new DaoException("Player with ID " + id + " not found");
            }

        } catch (SQLException e)
        {
            throw new DaoException("Error deleting player: " + e.getMessage());
        } finally
        {
            try
            {
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("Error closing resources: " + e.getMessage());
            }
        }
        return null;
    }


    /** Main author: Roman Manzhelii
     */
    @Override
    public Player insertPlayer(Player p) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = this.getConnection();

            String query = "INSERT INTO player (name, age, team, position) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            // Set the parameters for the Player
            preparedStatement.setString(1, p.getName());
            preparedStatement.setInt(2, p.getAge());
            preparedStatement.setString(3, p.getTeam());
            preparedStatement.setString(4, p.getPosition());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("Creating player failed, no rows affected.");
            }

            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Assign the auto-generated ID back to the player object
                p.setId(generatedKeys.getInt(1));
            } else {
                throw new DaoException("Creating player failed, no ID obtained.");
            }

        } catch (SQLException e) {
            throw new DaoException("insertPlayer() " + e.getMessage());
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) freeConnection(connection);
            } catch (SQLException e) {
                throw new DaoException("insertPlayer() " + e.getMessage());
            }
        }

        return p;
    }


    /** Main author: Roman Manzhelii
     */
    @Override
    public Player updatePlayer(int id, Player p) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.getConnection();

            String query = "UPDATE player SET name = ?, age = ?, team = ?, position = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);

            // Set the parameters for the Player
            preparedStatement.setString(1, p.getName());
            preparedStatement.setInt(2, p.getAge());
            preparedStatement.setString(3, p.getTeam());
            preparedStatement.setString(4, p.getPosition());
            preparedStatement.setInt(5, id);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("Updating player failed, no rows affected.");
            }

            p.setId(id);


        } catch (SQLException e) {
            throw new DaoException("updatePlayer() " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) freeConnection(connection);
            } catch (SQLException e) {
                throw new DaoException("updatePlayer() " + e.getMessage());
            }
        }
        return p;
    }

    /** Main author: Elga Jerusha Henry
     */
    @Override
    public List<Player> findPlayersUsingFilter(Comparator<Player> comparator) throws DaoException
    {
        List<Player> allPlayers = getAllPlayers();
        List<Player> filteredPlayers = new ArrayList<>(allPlayers);
        filteredPlayers.sort(comparator);

        return filteredPlayers;
    }


    /** Main author: Roman Manzhelii
     */
    @Override
    public boolean exists(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean exists = false;

        try {
            connection = this.getConnection();
            String query = "SELECT id FROM player WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            exists = resultSet.next();

        } catch (SQLException e) {
            throw new DaoException("exists() " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("exists() finally " + e.getMessage());
            }
        }
        return exists;
    }

}



