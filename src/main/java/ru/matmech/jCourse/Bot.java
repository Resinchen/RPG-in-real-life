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
import ru.matmech.jCourse.command.StatCommands;
import ru.matmech.jCourse.utils.PlayerUtils;

import javax.annotation.PostConstruct;

@Component
public class Bot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    @Autowired
    private CreateCommands createCommands;
    @Autowired
    private StatCommands statCommand;

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
                send(new SendMessage().setChatId(message.getChatId()).setText("_Hello_").enableMarkdown(true));
                send(statCommand.getPlayerInfo(message));
                send(statCommand.getStatImage(message));
//                send(statCommand.getPerks());
            }

        } else if(update.hasCallbackQuery()) {
            Message cbMessage = update.getCallbackQuery().getMessage();
            String cb_data = update.getCallbackQuery().getData();

            logger.info("Get callback -> {}", cb_data);

            String[] spt = cb_data.split("_");

            if (spt[0].equals("change")) {
                if(spt[1].equals("stats")) {
                    send(createCommands.changeStats(cbMessage));
                } else {
                    logger.info("Changing {}", spt[1]);
                    send(createCommands.changeStat(cbMessage, spt[1], PlayerUtils.player));
                }
            }

            else if (spt[0].equals("update")) {
                //TODO перенести изменение статы отсюда в CreateCommands и ?util.PlayerHandler?
                logger.info("Change {}: {}", spt[2], PlayerUtils.GetStat(spt[2]));

                if(spt[1].equals("sub")) {
                    PlayerUtils.ChangeStat(spt[2], -1);

                } else if (spt[1].equals("add")) {
                    PlayerUtils.ChangeStat(spt[2], 1);
                }
                send(createCommands.changeStat(cbMessage, spt[2], PlayerUtils.player));
            }

            else if (cb_data.equals("back2stats")) {
                send(createCommands.changeStats(cbMessage));
            }

            else if (cb_data.equals("done_creating")) {
                logger.info("Creating player is done!\n {}", PlayerUtils.player);
                send(createCommands.donePlayer(cbMessage));
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