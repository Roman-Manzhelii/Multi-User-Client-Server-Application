package com.dkit.oop.sd2;
import static junit.framework.TestCase.assertEquals;
import com.dkit.oop.sd2.DTOs.Player;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/** Main author: Roman Manzhelii
 */
public class AppTest {
    private final JsonConverter converter = new JsonConverter();

    @Test
    public void testPlayerToJson() {
        Player player = new Player(1, "John Doe", 30, "Team A", "Forward");
        String expectedJson = "{\"id\":1,\"name\":\"John Doe\",\"age\":30,\"team\":\"Team A\",\"position\":\"Forward\"}";
        String actualJson = converter.playerToJson(player);
        assertEquals(expectedJson, actualJson);
    }

    @Test
    public void testPlayersListToJson() {
        List<Player> players = Arrays.asList(
                new Player(1, "John Doe", 30, "Team A", "Forward"),
                new Player(2, "Jane Smith", 25, "Team B", "Midfielder")
        );
        String expectedJson = "[{\"id\":1,\"name\":\"John Doe\",\"age\":30,\"team\":\"Team A\",\"position\":\"Forward\"}," +
                "{\"id\":2,\"name\":\"Jane Smith\",\"age\":25,\"team\":\"Team B\",\"position\":\"Midfielder\"}]";
        String actualJson = converter.playersListToJson(players);
        assertEquals(expectedJson, actualJson);
    }
}