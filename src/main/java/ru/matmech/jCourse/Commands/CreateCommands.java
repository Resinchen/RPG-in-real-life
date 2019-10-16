package ru.matmech.jCourse.Commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.matmech.jCourse.Domains.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.matmech.jCourse.Utils.TelegramUtils.*;
import static java.lang.Math.toIntExact;

public class CreateCommands {
    private static Player player;

    public static SendMessage createPlayer(Message message) {
        long chat_id = message.getChatId();
        String text = "Create player\n You have 5 points.\n Would you change your stats?";

        InlineKeyboardButton statsBtn = GenerateKeyboardButton("Change statistic`s", "changeStats");
        InlineKeyboardButton doneBtn = GenerateKeyboardButton("Done creating", "doneCreating");

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = Stream
                .of(statsBtn, doneBtn)
                .map(Arrays::asList)
                .collect(Collectors.toList());

        keyboard.setKeyboard(rows);

        return GenerateSendMessage(chat_id, text, keyboard);
    }

    public static EditMessageText changeStats(Message message) {
        long chat_id = message.getChatId();
        int message_id = toIntExact(message.getMessageId());
        String text = "Change statistic`s " + player.getFreePoints();

        InlineKeyboardButton strBtn = GenerateKeyboardButton("Strength", "changeStrength");
        InlineKeyboardButton endBtn = GenerateKeyboardButton("Endurance", "changeEndurance");
        InlineKeyboardButton chaBtn = GenerateKeyboardButton("Charisma", "changeCharisma");
        InlineKeyboardButton intBtn = GenerateKeyboardButton("Intelligence", "changeIntelligence");
        InlineKeyboardButton lucBtn = GenerateKeyboardButton("Lucky", "changeLucky");
        InlineKeyboardButton doneBtn = GenerateKeyboardButton("Done creating", "doneCreating");

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = Stream
                .of(strBtn, endBtn, chaBtn, intBtn, lucBtn, doneBtn)
                .map(Arrays::asList)
                .collect(Collectors.toList());

        keyboard.setKeyboard(rows);

        return GenerateEditMessage(chat_id, message_id, text, keyboard);
    }

    public static EditMessageText changeStat(Message message, String statistica) {
        long chat_id = message.getChatId();
        int message_id = toIntExact(message.getMessageId());
        String text = "Change " + statistica + ": " + TestCommand.GetStat(statistica) + "Осталось: " + player.getFreePoints();

        InlineKeyboardButton backBtn = GenerateKeyboardButton("Back", "back2Stats");
        InlineKeyboardButton subBtn = GenerateKeyboardButton("-", "subStat;" + statistica);
        InlineKeyboardButton addBtn = GenerateKeyboardButton("+", "addStat;" + statistica);

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> row = Stream
                .of(subBtn, backBtn, addBtn)
                .collect(Collectors.toList());

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row);

        keyboard.setKeyboard(rows);

        return GenerateEditMessage(chat_id, message_id, text, keyboard);
    }

    public static EditMessageText donePlayer(Message message) {
        //TODO Sending to DB

        long chat_id = message.getChatId();
        int message_id = toIntExact(message.getMessageId());
        String text = "Character Created! " + player;

        return GenerateEditMessage(chat_id, message_id, text);
    }
}