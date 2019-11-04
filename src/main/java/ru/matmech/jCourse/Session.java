package ru.matmech.jCourse;

import ru.matmech.jCourse.domain.User;

import java.util.Date;

public class Session {
    public static Session NullSession = new Session(null, null);

    public Session(Date dateCreateSession, User user) {
        this.dateCreateSession = dateCreateSession;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Session {" +
                "dateCreateSession=" + dateCreateSession +
                ", user=" + user +
                '}';
    }

    private Date dateCreateSession;

    public Date getDateCreateSession() {
        return dateCreateSession;
    }

    private User user;

    public User getUser() {
        return user;
    }

}
