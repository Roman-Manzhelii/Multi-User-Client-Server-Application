package com.dkit.oop.sd2;

import com.dkit.oop.sd2.DTOs.Player;
import com.google.gson.Gson;

import java.util.List;

/** Main author: Roman Manzhelii
 */
public class JsonConverter {
    private static final Gson gson = new Gson();

    public String playersListToJson(List<Player> list) {
        return gson.toJson(list);
    }

    public static String playerToJson(Player p) {
        return gson.toJson(p);
    }

    public static Player jsonToPlayer(String json) {
        return gson.fromJson(json, Player.class);
    }
}
