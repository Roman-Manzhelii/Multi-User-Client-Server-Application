package com.dkit.oop.sd2;

import com.dkit.oop.sd2.DTOs.Player;
import com.google.gson.Gson;

import java.util.List;

/** Main author: Roman Manzhelii
 */
public class JsonConverter {
    private final Gson gson = new Gson();

    public String playersListToJson(List<Player> list) {
        return gson.toJson(list);
    }

    public String playerToJson(Player p) {
        return gson.toJson(p);
    }
}
