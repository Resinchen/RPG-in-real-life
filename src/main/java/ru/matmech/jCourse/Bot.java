package ru.matmech.jCourse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.matmech.jCourse.Commands.TestCommand;

import javax.annotation.PostConstruct;

@Component
public class Bot extends TelegramLongPollingCommandBot {
    public static final Logger logger = LoggerFactory.getLogger(Bot.class);

    @Value("${bot.token}")
    private static String token;

    @Value("${bot.username}")
    private static String username;

    // Конструктор для Spring
    public Bot() { this(username); }

    private Bot(String botUsername) {
        super(botUsername);
        register(new TestCommand("/test", "Test command"));
    }

    @PostConstruct
    public void start() {
        logger.info("username: {}, token: {}", username, token);
    }

    @Override
    public String getBotToken() { return token; }

    @Override
    public void processNonCommandUpdate(Update update) {}
}