package com.dkit.oop.sd2.Exceptions;

import java.util.Comparator;
import com.dkit.oop.sd2.DTOs.Player;

public class PlayerAgeComparator implements Comparator<Player> {
    private int ageToFilter;

    public PlayerAgeComparator(int ageToFilter) {
        this.ageToFilter = ageToFilter;
    }

    @Override
    public int compare(Player p1, Player p2) {
        // Compare the age of the players with the specified age
        if (p1.getAge() == ageToFilter && p2.getAge() == ageToFilter) {
            return 0;
        } else if (p1.getAge() < ageToFilter && p2.getAge() < ageToFilter) {
            return -1;
        } else {
            return 1;
        }
    }
}
