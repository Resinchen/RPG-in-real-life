package ru.matmech.jCourse.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.matmech.jCourse.Utils.UserUtils;
import ru.matmech.jCourse.domain.User;
import ru.matmech.jCourse.services.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.matmech.jCourse.Utils.TelegramUtils.*;
import static java.lang.Math.toIntExact;

@Component
public class CreateCommands {
    @Autowired
    private UserService userService;
    @Autowired
    private UserUtils userUtils;

    public SendMessage createUser(Message message) {
        long chat_id = message.getChatId();
        String text = "Create player\n" +
                "You have 5 points.\n" +
                "Would you change your stats?";

        InlineKeyboardButton statsBtn = GenerateKeyboardButton("Change statistic`s", "change_stats");
        InlineKeyboardButton doneBtn = GenerateKeyboardButton("Done creating", "done_creating");

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = Stream
                .of(statsBtn, doneBtn)
                .map(Arrays::asList)
                .collect(Collectors.toList());

        keyboard.setKeyboard(rows);
        userUtils.create(chat_id, message.getFrom().getUserName());

        return GenerateSendMessage(chat_id, text, keyboard);
    }

    public EditMessageText changeStats(Message message, User user) {
        long chat_id = message.getChatId();
        int message_id = toIntExact(message.getMessageId());
        String text = "Change statistic`s " + user.getFreePoints();

        InlineKeyboardButton strBtn = GenerateKeyboardButton("Strength", "change_strength");
        InlineKeyboardButton endBtn = GenerateKeyboardButton("Endurance", "change_endurance");
        InlineKeyboardButton chaBtn = GenerateKeyboardButton("Charisma", "change_charisma");
        InlineKeyboardButton intBtn = GenerateKeyboardButton("Intelligence", "change_intelligence");
        InlineKeyboardButton lucBtn = GenerateKeyboardButton("Lucky", "change_lucky");
        InlineKeyboardButton doneBtn = GenerateKeyboardButton("Done creating", "done_creating");

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = Stream
                .of(strBtn, endBtn, chaBtn, intBtn, lucBtn, doneBtn)
                .map(Arrays::asList)
                .collect(Collectors.toList());

        keyboard.setKeyboard(rows);

        return GenerateEditMessage(chat_id, message_id, text, keyboard);
    }

    public EditMessageText changeStat(Message message, String statistica, User user) {
        long chat_id = message.getChatId();
        int message_id = toIntExact(message.getMessageId());
        String text = "Change " + statistica + ": " +
                userUtils.getStat(user, statistica) +
                "\nОсталось очков: " + user.getFreePoints();


        InlineKeyboardButton backBtn = GenerateKeyboardButton("Back", "back_stats");
        InlineKeyboardButton subBtn = GenerateKeyboardButton("-", "update_sub_" + statistica);
        InlineKeyboardButton addBtn = GenerateKeyboardButton("+", "update_add_" + statistica);

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> row = Stream
                .of(subBtn, backBtn, addBtn)
                .collect(Collectors.toList());

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row);

        keyboard.setKeyboard(rows);

        return GenerateEditMessage(chat_id, message_id, text, keyboard);
    }


    public EditMessageText donePlayer(Message message, User user) {
        long chat_id = message.getChatId();
        int message_id = toIntExact(message.getMessageId());
        String text = "Character Created! " + user;
        userUtils.addDefaultPerk(user);
        userService.create(user);
        return GenerateEditMessage(chat_id, message_id, text);
    }

    public EditMessageText updateStat(Message message, User user, String subcommand, String statistica) {
        switch (subcommand) {
            case "sub":
                userUtils.ChangeStat(user, statistica, -1);
                break;

            case "add":
                userUtils.ChangeStat(user, statistica, 1);
                break;
        }

        return changeStat(message, statistica, user);
    }
}