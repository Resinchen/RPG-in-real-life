package ru.matmech.jCourse.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.matmech.jCourse.domain.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class SessionsPool {
    private static final Logger logger = LoggerFactory.getLogger(SessionsPool.class);
    private static Map<Long, Session> sessions = new HashMap<>();

    public static void addSession(long id, User player) {
        if (!sessions.containsKey(id)) {
            sessions.put(id, new Session(new Date(), player));
        }
    }

    public static boolean has(long id) {
        return sessions.containsKey(id);
    }

    public static User getSessionUser(long id) {
        Session session = sessions.get(id);
        if (sessions.containsKey(id)) {
            return session.getUser();
        }
        return User.NullUser;
    }

    @Scheduled(fixedRate = 150000)
    private static void ReleaseSessions() {
        for (Map.Entry<Long, Session> entry : sessions.entrySet()) {
            if (new Date().getTime() - entry.getValue().getDateCreateSession().getTime() > 5000) {
                logger.info("Pop {}", entry.getValue());
                sessions.remove(entry.getKey());
            }
        }
    }
}
//java cache caffeine timeout
//