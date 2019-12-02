package ru.matmech.jCourse.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.matmech.jCourse.domain.Quest;
import ru.matmech.jCourse.domain.User;
import ru.matmech.jCourse.services.QuestService;
import ru.matmech.jCourse.services.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.toIntExact;
import static ru.matmech.jCourse.Utils.TelegramUtils.*;

@Component
public class QuestCommands {

    @Autowired
    private QuestService questService;
    @Autowired
    private UserService userService;

    public SendMessage getQuests(Message message) {
        long chat_id = message.getChatId();
        User user = userService.getUserById(chat_id);

        if (user.getQuest().getId().equals(-1L)) {
            String text = "Вам доступны следующие квесты:";
            Quest[] quests = questService.getTwoRandomQuest(user);
            InlineKeyboardButton[] btn_quests = new InlineKeyboardButton[quests.length];

            for (int i = 0; i < quests.length; i++) {
                btn_quests[i] = GenerateKeyboardButton(quests[i].getName(), "set_quest_" + quests[i].getId());
            }

            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

            List<List<InlineKeyboardButton>> rows = Stream
                    .of(btn_quests)
                    .map(Arrays::asList)
                    .collect(Collectors.toList());

            keyboard.setKeyboard(rows);

            return GenerateSendMessage(chat_id, text, keyboard);
        }
        else {
            return GenerateSendMessage(chat_id, "Текущий квест:\n" + user.getQuest().getDescription());
        }
    }

    public EditMessageText setQuest(Message message, String id) {
        Long id_quest = Long.parseLong(id);
        long chat_id = message.getChatId();
        int message_id = toIntExact(message.getMessageId());
        Quest quest =  questService.getQuestById(id_quest);

        userService.setQuest(chat_id, quest);

        String text = "Вы выбрали квест " + quest.getName() + '\n' + quest.getDescription();
        return GenerateEditMessage(chat_id, message_id, text);
    }

    public SendMessage completeQuest(Message message) {
        Long chat_id = message.getChatId();
        User user = userService.getUserById(chat_id);
        String answer = message.getText();
        Quest quest = user.getQuest();

        switch (quest.getType()) {
            case "promocode":
            case "puzzle":
                if (questService.checkAnswer(answer.replace("/complete ", ""), quest)) {
                    userService.releaseQuest(chat_id);
                }
                break;

            case "photo":
                if (message.getCaption().equals("/complete")) {
                    userService.releaseQuest(chat_id);
                }
                break;
        }
        return GenerateSendMessage(chat_id,
                "Поздравляю, ты выполнил задание и получаешь " + quest.getExperience() + " очков опыта");
    }
}
