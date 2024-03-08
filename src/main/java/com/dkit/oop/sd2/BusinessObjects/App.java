package com.dkit.oop.sd2.BusinessObjects;

import com.dkit.oop.sd2.DAOs.MySqlPlayerDao;
import com.dkit.oop.sd2.DAOs.PlayerDaoInterface;
import com.dkit.oop.sd2.DTOs.Player;
import com.dkit.oop.sd2.Exceptions.DaoException;
import java.util.List;

public class App
{
    public static void main(String[] args)
    {
        PlayerDaoInterface IPlayerDao = new MySqlPlayerDao();

        try
        {
            System.out.println("\nCall getAllPlayers()");
            List<Player> players = IPlayerDao.getAllPlayers();     // call a method in the DAO

            if( players.isEmpty() )
                System.out.println("There are no Players");
            else {
                for (Player player : players)
                    System.out.println("Player: " + player.toString());
            }
        }
        catch( DaoException e )
        {
            e.printStackTrace();
        }
    }
}
