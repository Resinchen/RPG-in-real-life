package ru.matmech.jCourse.Utils;

import ru.matmech.jCourse.domain.User;

public class UserUtils {

    public static final int[] lvlLimitExp = new int[] {0, 5, 5, 15};

    public static boolean isNotNullUser(User user) {
        return !user.equals(User.NullUser);
    }

    public static int getStat(User user, String statistica) {
        int res = -1;

        switch (statistica) {
            case "strength":
                res = user.getStrength();
                break;
            case "endurance":
                res = user.getEndurance();
                break;
            case "charisma":
                res = user.getCharisma();
                break;
            case "intelligence":
                res = user.getIntelligence();
                break;
            case "lucky":
                res = user.getLucky();
                break;
        }

        return res;
    }

}
