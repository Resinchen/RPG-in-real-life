package ru.matmech.jCourse.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.matmech.jCourse.Utils.UserUtils;
import ru.matmech.jCourse.domain.Perk;
import ru.matmech.jCourse.domain.User;
import ru.matmech.jCourse.services.PerksService;
import ru.matmech.jCourse.services.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.toIntExact;
import static ru.matmech.jCourse.Utils.TelegramUtils.*;

@Component
public class PerksCommand {
    @Autowired
    private PerksService perksService;

    @Autowired
    private UserService userService;

    public SendMessage getPerksList(Message message) {
        long chat_id = message.getChatId();
        StringBuilder builder = new StringBuilder();
        for(Perk perk : perksService.getAll()) {
            builder.append("* ").append(perk.getName()).append(" - ")
                    .append(perk.getDescription()).append('\n');
        }

        return GenerateSendMessage(chat_id, builder.toString());
    }

    public SendMessage getPerksListForUser(Message message) {
        long chat_id = message.getChatId();

        User user = userService.getUserById(chat_id);
        StringBuilder builder = new StringBuilder("Навыки, которые ты можешь получить\n");

        for(Perk perk : perksService.findPerksForUser(user)) {
            builder.append("* ").append(perk.getName()).append(" - ")
                    .append(perk.getDescription()).append('\n');
        }

        if (builder.toString().equals("Навыки, которые ты можешь получить\n")) {
            builder.append("Для этого начни игру командой /create");
        }

        return GenerateSendMessage(chat_id, builder.toString());

    }

    public SendMessage getPerksForAdding(Message message) {
        long chat_id = message.getChatId();
        User user = userService.getUserById(chat_id);
        if (UserUtils.isNotNullUser(user)) {

            String text = "Ваши параметры удовлетворяют следующим навыкам";

            Perk[] perks = Arrays.stream(perksService.findPerksForUser(user))
                    .filter(x -> !user.getPerks().contains(x))
                    .toArray(Perk[]::new);

            InlineKeyboardButton[] btn_perks = new InlineKeyboardButton[perks.length];

            for (int i = 0; i < perks.length; i++) {
                btn_perks[i] = GenerateKeyboardButton(perks[i].getName(), "add_perk_" + perks[i].getName());
            }

            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

            List<List<InlineKeyboardButton>> rows = Stream
                    .of(btn_perks)
                    .map(Arrays::asList)
                    .collect(Collectors.toList());

            keyboard.setKeyboard(rows);

            return GenerateSendMessage(chat_id, text, keyboard);
        }
        else {
            return GenerateSendMessage(chat_id, "Начни игру командой /create");
        }
    }

    public EditMessageText addPerk(Message message, String perkName) {
        long chat_id = message.getChatId();
        int message_id = toIntExact(message.getMessageId());
        String text = "Вы добавили навык " + perkName;

        userService.addPerk(chat_id, perksService.findByName(perkName));

        return GenerateEditMessage(chat_id, message_id, text);
    }
}
