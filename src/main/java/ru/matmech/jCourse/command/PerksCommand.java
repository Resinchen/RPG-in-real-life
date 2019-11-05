package ru.matmech.jCourse.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.matmech.jCourse.domain.Perk;
import ru.matmech.jCourse.domain.User;
import ru.matmech.jCourse.services.PerksService;
import ru.matmech.jCourse.services.UserService;

import static ru.matmech.jCourse.Utils.TelegramUtils.GenerateSendMessage;

@Component
public class PerksCommand {
    @Autowired
    private PerksService perksService;

    @Autowired
    private UserService userService;

    public SendMessage getPerksList(Message message, User user) {
        long chat_id = message.getChatId();
        StringBuilder builder = new StringBuilder();

        for(Perk perk : perksService.getAll()) {
            builder.append("* ").append(perk.getName()).append(" - ")
                    .append(perk.getDescription()).append('\n');
        }

        return GenerateSendMessage(chat_id, builder.toString());
    }

    public SendMessage addDefaultPerk(Message message, User user) {
        long chat_id = message.getChatId();
        StringBuilder builder = new StringBuilder();
        userService.addPerk(user, perksService.findByName("Junior"));

        for(Perk perk : user.getPerks()) {
            builder.append("* ").append(perk.getName()).append(" - ")
                    .append(perk.getDescription()).append('\n');
        }

        return GenerateSendMessage(chat_id, builder.toString());
    }

}
