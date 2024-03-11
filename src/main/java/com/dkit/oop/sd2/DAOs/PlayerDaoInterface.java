package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Player;
import com.dkit.oop.sd2.Exceptions.DaoException;
import java.util.List;

public interface PlayerDaoInterface
{
    List<Player> getAllPlayers() throws DaoException;
    Player insertPlayer(Player p) throws DaoException;
}

