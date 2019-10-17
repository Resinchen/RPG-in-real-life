package ru.matmech.jCourse.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;


public class TelegramUtils {
    public static SendMessage GenerateSendMessage(long chat_id, String text) {
        return new SendMessage()
                .setChatId(chat_id)
                .setText(text);
    }

    public static SendMessage GenerateSendMessage(long chat_id, String text, InlineKeyboardMarkup keyboard) {
        return GenerateSendMessage(chat_id, text)
                .setReplyMarkup(keyboard);
    }

    public static EditMessageText GenerateEditMessage(long chat_id, int message_id, String text) {
        return new EditMessageText()
                .setChatId(chat_id)
                .setMessageId(message_id)
                .setText(text);
    }

    public static EditMessageText GenerateEditMessage(long chat_id, int message_id, String text, InlineKeyboardMarkup keyboard) {
        return GenerateEditMessage(chat_id, message_id, text)
                .setReplyMarkup(keyboard);
    }

    public static InlineKeyboardButton GenerateKeyboardButton(String text, String callback) {
        return new InlineKeyboardButton().setText(text).setCallbackData(callback);
    }
}
