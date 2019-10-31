package ru.matmech.jCourse.utils;

import ru.matmech.jCourse.domain.Player;

public class PlayerUtils {
    public static Player player;

    public static void create(long id, String name) {
        player = new Player(id, name);
    }

    public static void ChangeStat(String statistica, int deltaPoint) {

        if (player.getFreePoints() <= 0) {
            deltaPoint = 0;
        }

        player.addFreePoints(-deltaPoint);

        switch (statistica) {
            case "strength":
                player.addStrength(deltaPoint);
                break;
            case "endurance":
                player.addEndurance(deltaPoint);
                break;
            case "charisma":
                player.addCharisma(deltaPoint);
                break;
            case "intelligence":
                player.addIntelligence(deltaPoint);
                break;
            case "lucky":
                player.addLucky(deltaPoint);
                break;
        }

    }

    public static int GetStat(String statistica) {
        int res = -1;

        switch (statistica) {
            case "strength":
                res = player.getStrength();
                break;
            case "endurance":
                res = player.getEndurance();
                break;
            case "charisma":
                res = player.getCharisma();
                break;
            case "intelligence":
                res = player.getIntelligence();
                break;
            case "lucky":
                res = player.getLucky();
                break;
        }
        return res;
    }
}
