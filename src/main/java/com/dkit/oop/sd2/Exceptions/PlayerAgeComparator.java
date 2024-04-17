package com.dkit.oop.sd2.Exceptions;

import java.util.Comparator;
import com.dkit.oop.sd2.DTOs.Player;

public class PlayerAgeComparator {
    public static class AboveAgeComparator implements Comparator<Player> {
        private int minAgeToFilter;

        public AboveAgeComparator(int minAgeToFilter) {
            this.minAgeToFilter = minAgeToFilter;
        }

        @Override
        public int compare(Player player1, Player player2) {
            return Integer.compare(player1.getAge(), player2.getAge());
        }
    }

    public static class BelowAgeComparator implements Comparator<Player> {
        private int maxAgeToFilter;

        public BelowAgeComparator(int maxAgeToFilter) {
            this.maxAgeToFilter = maxAgeToFilter;
        }

        @Override
        public int compare(Player player1, Player player2) {
            return Integer.compare(player2.getAge(), player1.getAge());
        }
    }
}
