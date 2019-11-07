package ru.matmech.jCourse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.matmech.jCourse.Utils.UserUtils;

import ru.matmech.jCourse.command.CreateCommands;
import ru.matmech.jCourse.command.PerksCommand;
import ru.matmech.jCourse.command.StatCommands;

import ru.matmech.jCourse.domain.User;

import javax.annotation.PostConstruct;

@Component
public class Bot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);
    private User user;
    private Message message;
    private String data;

    @Autowired
    private UserUtils userUtils;
    @Autowired
    private CreateCommands createCommands;
    @Autowired
    private StatCommands statCommand;
    @Autowired
    private PerksCommand perksCommand;

    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String username;

    @PostConstruct
    public void start() {
        logger.info("username: {}, token: {}", username, token);
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() { return token; }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            message = update.getMessage();
            data = message.getText();

            logger.info("Get command {}", data);
            switch (data) {
                case "/create":
                    send(createCommands.createUser(message));
                    break;

                case "/info":
                    user = userUtils.getUser(message.getChatId());
                    logger.info("Read user {}", user);

                    if (userUtils.isNotNullUser(user)) {
                        send(statCommand.getPlayerInfo(message, user));
                        send(statCommand.getStatImage(message, user));
                        send(statCommand.getPerks(message, user));
                    }
                    else {
                        logger.error("Player not found: {}", message.getChatId());
                        send(new SendMessage().setChatId(message.getChatId()).setText("Player not Found"));
                    }
                    break;

                case "/perks":
                    send(perksCommand.getPerksList(message));
                    break;
            }
        }

        else if (update.hasCallbackQuery()) {
            message = update.getCallbackQuery().getMessage();
            data = update.getCallbackQuery().getData();

            logger.info("Get Callback {}", data);

            user = userUtils.getUser(message.getChatId());

            logger.info("Read user {}", user);

            String[] spt = data.split("_");

                switch (spt[0]) {
                    case "change":
                        if (spt[1].equals("stats"))
                            send(createCommands.changeStats(message, user));
                        else
                            send(createCommands.changeStat(message, spt[1], user));
                        break;

                    case "done":
                        send(createCommands.donePlayer(message, user));
                        break;

                    case "back":
                        send(createCommands.changeStats(message, user));
                        break;

                    case "update":
                        send(createCommands.updateStat(message, user, spt[1], spt[2]));
                        break;
                }
        }

    }

    private void send(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }
    }

    private void send(EditMessageText sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }
    }

    private void send(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }
    }
}