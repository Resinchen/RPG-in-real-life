package ru.matmech.jCourse.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.matmech.jCourse.services.PlayerService;
import ru.matmech.jCourse.domain.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;

import static ru.matmech.jCourse.Utils.TelegramUtils.*;

@Component
public class StatCommands {

    @Autowired
    private PlayerService service;

    public SendMessage getPlayerInfo(Message message, User user) {
        long chat_id = message.getChatId();
        StringBuilder builder = new StringBuilder();

        //TODO НЕ РАБОТАЕТ РАЗМЕТКА
        builder.append("_")
                .append(user.getName())
                .append("_")
                .append("\n---")
                .append("\nLevel: ").append(user.getLevel())
                .append("\nExp: ").append(user.getExperience())
                .append("\nPoints: ").append(user.getFreePoints());

        return GenerateSendMarkupMessage(chat_id, builder.toString());
    }

    public SendPhoto getStatImage(Message message, User user) {
        //TODO заменить на генератор картинки
        long chat_id = message.getChatId();

        return GenerateSendPhoto(chat_id, new File("C:\\Users\\alex1\\Desktop\\Bot.png"));
    }

    public SendMessage getPerks() {
        throw new NotImplementedException();
    }
}
