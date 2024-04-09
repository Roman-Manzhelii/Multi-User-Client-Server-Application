package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Player;
import com.dkit.oop.sd2.Exceptions.DaoException;
import java.util.List;

public interface PlayerDaoInterface
{
    List<Player> getAllPlayers() throws DaoException; // feature 1 (Roman)
    Player findPlayerById(int id) throws DaoException; // feature 2 (Mila)
    
    Player insertPlayer(Player p) throws DaoException; // feature 4 (Roman)
    Player updatePlayer(int id, Player p) throws DaoException; // feature 5 (Roman)
    boolean exists(int id) throws DaoException;
}

