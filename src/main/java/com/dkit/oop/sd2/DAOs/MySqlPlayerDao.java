package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Player;
import com.dkit.oop.sd2.Exceptions.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
}

