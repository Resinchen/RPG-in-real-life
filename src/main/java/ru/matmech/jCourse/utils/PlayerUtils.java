package ru.matmech.jCourse.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.matmech.jCourse.domain.User;
import ru.matmech.jCourse.services.UserService;

public class PlayerUtils {
    public static void create(long id, String name) {
        SessionsPool.addSession(id, new User(id, name));
    }

    public static User getUser(UserService service, long id) {
        if (SessionsPool.has(id)) {
            return SessionsPool.getSession(id);
        }

        User user = service.findById(id);
        SessionsPool.addSession(id, user);

        return user;
    }

    public static void ChangeStat(User user, String statistica, int deltaPoint) {
        if (user.getFreePoints() <= 0) {
            deltaPoint = 0;
        }

        user.addFreePoints(-deltaPoint);

        switch (statistica) {
            case "strength":
                user.addStrength(deltaPoint);
                break;
            case "endurance":
                user.addEndurance(deltaPoint);
                break;
            case "charisma":
                user.addCharisma(deltaPoint);
                break;
            case "intelligence":
                user.addIntelligence(deltaPoint);
                break;
            case "lucky":
                user.addLucky(deltaPoint);
                break;
        }
    }

    public static int GetStat(User user, String statistica) {
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
