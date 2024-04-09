package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Player;
import com.dkit.oop.sd2.Exceptions.DaoException;
import java.util.List;

public interface PlayerDaoInterface
{
    List<Player> getAllPlayers() throws DaoException;
    Player deletePlayer(int id) throws DaoException;
    Player insertPlayer(Player p) throws DaoException;
    Player updatePlayer(int id, Player p) throws DaoException;
    boolean exists(int id) throws DaoException;
}

