package ru.matmech.jCourse.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.matmech.jCourse.Utils.UserUtils;
import ru.matmech.jCourse.domain.Perk;
import ru.matmech.jCourse.services.UserService;
import ru.matmech.jCourse.domain.User;

import java.io.File;

import static ru.matmech.jCourse.Utils.TelegramUtils.*;

@Component
public class StatCommands {

    @Autowired
    private UserService userService;

    public SendMessage getPlayerInfo(Message message) {
        long chat_id = message.getChatId();
        String stringInfo;
        String[] userInfo = userService.getUserInfo(chat_id);

        if (userInfo.length != 0) {
        stringInfo = "_" +
                userInfo[0] +
                "_" +
                "\nLevel: " + userInfo[1] +
                "\nExp: " + userInfo[2] +
                "\nPoints: " + userInfo[3];
        } else {
            stringInfo = "Начни игру командой /create";
        }

        return GenerateSendMarkupMessage(chat_id, stringInfo);
    }

    public SendPhoto getStatImage(Message message) {
        //TODO заменить на генератор картинки
        long chat_id = message.getChatId();
        User user = userService.getUserById(chat_id);
        if(UserUtils.isNotNullUser(user)) {
            return GenerateSendPhoto(chat_id, new File("C:\\Users\\alex1\\Desktop\\Bot.png"));
        }
        return null;
    }

    public SendMessage getPerks(Message message) {
        long chat_id = message.getChatId();
        Perk[] userPerks = userService.getUserPerks(chat_id);
        StringBuilder builder = new StringBuilder();
        for (Perk perk : userPerks) {
            builder.append('*')
                    .append(perk.getName())
                    .append('\n');
        }

        return GenerateSendMessage(chat_id, builder.toString());
    }

    //CHEAT!!!
    public SendMessage cheatAddExp(Message message) {
        long chat_id = message.getChatId();
        userService.addExp(chat_id, 10);

        return GenerateSendMessage(chat_id, "ЧИИИТЕЕРР!!!!");
    }
}
