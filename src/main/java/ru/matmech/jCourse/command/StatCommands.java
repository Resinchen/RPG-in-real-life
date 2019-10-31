package ru.matmech.jCourse.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.matmech.jCourse.services.PlayerService;
import ru.matmech.jCourse.domain.Player;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;

import static ru.matmech.jCourse.utils.TelegramUtils.*;

@Component
public class StatCommands {

    @Autowired
    private PlayerService service;

    private Player player;

    public SendMessage getPlayerInfo(Message message) {
        long chat_id = message.getChatId();
        StringBuilder builder = new StringBuilder();
        player = service.findById(chat_id);
        if (player == null)
           return new SendMessage().setChatId(chat_id).setParseMode("Markdown").setText("Player not found");

        //TODO НЕ РАБОТАЕТ РАЗМЕТКА
        builder.append("**")
                .append(player.getName())
                .append("**")
                .append("\n---")
                .append("\nLevel: ").append(player.getLevel())
                .append("\nExp: ").append(player.getExperience())
                .append("\nPoints: ").append(player.getFreePoints());

        return GenerateSendMarkupMessage(chat_id, builder.toString());
    }

    public SendPhoto getStatImage(Message message) {
        //TODO заменить на генератор картинки
        long chat_id = message.getChatId();
        if (player != null)
            return GenerateSendPhoto(chat_id, new File("C:\\Users\\alex1\\Desktop\\Bot.png"));
        // throw new Exception(player not found)
        return null;
    }

    public SendMessage getPerks() {
        throw new NotImplementedException();
    }
}
