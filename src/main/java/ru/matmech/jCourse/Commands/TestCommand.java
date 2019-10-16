package ru.matmech.jCourse.Commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.matmech.jCourse.Domains.Player;

public class TestCommand {
    public static Player player = new Player(12, "Alex");

    public static void ChangeStat(String statistica, int deltaPoint) {
        player.addFreePoints(-deltaPoint);

        if (player.getFreePoints() <= 0) {
            deltaPoint = 0;
        }

        switch (statistica) {
            case "Strength":
                player.addStrength(deltaPoint);
                break;
            case "Endurance":
                player.addEndurance(deltaPoint);
                break;
            case "Charisma":
                player.addCharisma(deltaPoint);
                break;
            case "Intelligence":
                player.addIntelligence(deltaPoint);
                break;
            case "Lucky":
                player.addLucky(deltaPoint);
                break;
        }

    }

    public static int GetStat(String statistica) {
        int res = 0;

        switch (statistica) {
            case "Strength":
                res = player.getStrength();
                break;
            case "Endurance":
                res = player.getEndurance();
                break;
            case "Charisma":
                res = player.getCharisma();
                break;
            case "Intelligence":
                res = player.getIntelligence();
                break;
            case "Lucky":
                res = player.getLucky();
                break;
        }
        return res;
    }
}