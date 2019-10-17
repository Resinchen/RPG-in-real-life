package ru.matmech.jCourse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.matmech.jCourse.command.CreateCommands;
import ru.matmech.jCourse.command.TestCommand;

import javax.annotation.PostConstruct;

@Component
public class Bot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

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
            if (update.getMessage().getText().equals("/create")) {
                logger.info("Get command /create");
                send(CreateCommands.createPlayer(update.getMessage()));
            }

        } else if(update.hasCallbackQuery()) {
            Message cbMessage = update.getCallbackQuery().getMessage();
            String cb_data = update.getCallbackQuery().getData();

            if (cb_data.equals("changeStats")) {
                logger.info("Choose statistics");
                send(CreateCommands.changeStats(cbMessage));
            }

            else if (cb_data.equals("changeStrength")) {
                logger.info("Choose strength");
                send(CreateCommands.changeStat(cbMessage, "Strength"));
            }
            else if (cb_data.equals("changeEndurance")) {
                logger.info("Choose endurance");
                send(CreateCommands.changeStat(cbMessage, "Endurance"));
            }
            else if (cb_data.equals("changeCharisma")) {
                logger.info("Choose charisma");
                send(CreateCommands.changeStat(cbMessage, "Charisma"));
            }
            else if (cb_data.equals("changeIntelligence")) {
                logger.info("Choose intelligence");
                send(CreateCommands.changeStat(cbMessage, "Intelligence"));
            }
            else if (cb_data.equals("changeLucky")) {
                logger.info("Choose lucky");
                send(CreateCommands.changeStat(cbMessage, "Lucky"));
            }

            else if (cb_data.equals("back2Stats")) {
                logger.info("Return to stats");
                send(CreateCommands.changeStats(cbMessage));
            }
            else if (cb_data.startsWith("subStat")) {
                //TODO перенести изменение статы отсюда
                String stat = cb_data.split(";")[1];
                TestCommand.ChangeStat(cb_data, -1);
                logger.info("Change {}: {}", stat, TestCommand.GetStat(stat));
                send(CreateCommands.changeStat(cbMessage, stat));
            }
            else if (cb_data.startsWith("addStat")) {
                //TODO перенести изменение статы отсюда
                String stat = cb_data.split(";")[1];
                TestCommand.ChangeStat(stat, 1);
                logger.info("Change {}: {}", stat, TestCommand.GetStat(stat));
                send(CreateCommands.changeStat(cbMessage, stat));
            }
            else if (cb_data.equals("doneCreating")) {
                logger.info("Creating player is done!\n {}", TestCommand.player);
                send(CreateCommands.donePlayer(cbMessage));
            }
        }
    }

    private void send(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void send(EditMessageText sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}