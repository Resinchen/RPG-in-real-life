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
import ru.matmech.jCourse.command.CreateCommands;
import ru.matmech.jCourse.command.PerksCommand;
import ru.matmech.jCourse.command.StatCommands;
import ru.matmech.jCourse.Utils.PlayerUtils;
import ru.matmech.jCourse.domain.User;
import ru.matmech.jCourse.services.UserService;

import javax.annotation.PostConstruct;
//TODO org.springframework.dao.InvalidDataAccessApiUsageException: Target object must not be null; nested exception is java.lang.IllegalArgumentException: Target object must not be null
@Component
public class Bot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);
    private static User user;

    @Autowired
    private CreateCommands createCommands;
    @Autowired
    private StatCommands statCommand;
    @Autowired
    private PerksCommand perksCommand;

    @Autowired
    private UserService userService;

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
            Message message = update.getMessage();

            logger.info("Get command -> {}", message.getText());

            if (message.getText().equals("/create")) {
                send(createCommands.createPlayer(message));
            }
            else if (message.getText().equals("/info")) {
                user = PlayerUtils.getUser(userService, message.getChatId());
                if (user != null) {
                    send(new SendMessage().setChatId(message.getChatId()).setText("_Hello_").enableMarkdown(true));
                    send(statCommand.getPlayerInfo(message, user));
                    send(statCommand.getStatImage(message, user));
                    send(statCommand.getPerks(message, user));
                }
                else {
                    send(new SendMessage().setChatId(message.getChatId()).setText("Player not found"));
                }
            }
            else if (message.getText().equals("/perks")) {
                user = PlayerUtils.getUser(userService, message.getChatId());
                if (user != null) {
                    send(perksCommand.addDefaultPerk(message, user));
                    send(perksCommand.getPerksList(message, user));
                }
            }

        } else if(update.hasCallbackQuery()) {
            Message cbMessage = update.getCallbackQuery().getMessage();
            String cb_data = update.getCallbackQuery().getData();

            user = PlayerUtils.getUser(userService, cbMessage.getChatId());

            logger.info("Get callback -> {}", cb_data);

            String[] spt = cb_data.split("_");

            if (spt[0].equals("change")) {
                if(spt[1].equals("stats")) {
                    send(createCommands.changeStats(cbMessage, user));
                } else {
                    logger.info("Changing {}", spt[1]);
                    send(createCommands.changeStat(cbMessage, spt[1], user));
                }
            }

            else if (spt[0].equals("update")) {
                //TODO перенести изменение статы отсюда в CreateCommands и ?util.PlayerHandler?
                logger.info("Change {}: {}", spt[2], PlayerUtils.GetStat(user, spt[2]));

                if(spt[1].equals("sub")) {
                    PlayerUtils.ChangeStat(user, spt[2], -1);

                } else if (spt[1].equals("add")) {
                    PlayerUtils.ChangeStat(user, spt[2], 1);
                }
                send(createCommands.changeStat(cbMessage, spt[2], user));
            }

            else if (cb_data.equals("back2stats")) {
                send(createCommands.changeStats(cbMessage, user));
            }

            else if (cb_data.equals("done_creating")) {
                logger.info("Creating player is done!\n {}", user);
                send(createCommands.donePlayer(cbMessage, user));
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