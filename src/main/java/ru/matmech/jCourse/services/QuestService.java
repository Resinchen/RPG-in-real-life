package ru.matmech.jCourse.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.matmech.jCourse.domain.Quest;
import ru.matmech.jCourse.domain.User;
import ru.matmech.jCourse.repositories.QuestRepository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collections;
import java.util.List;

@Service
public class QuestService {
    private final static Logger logger = LoggerFactory.getLogger(PerksService.class);

    @Autowired
    QuestRepository repository;

    public Quest getQuestById(Long id) {
        return repository.findById(id).orElse(Quest.NullQuest);
    }

    public Quest[] getTwoRandomQuest(User user){
        logger.info("Find quests for user {}", user.getId());
        List<Quest> quests = repository.findByMinLevelLessThanEqual(user.getLevel());

        Collections.shuffle(quests);
        return quests.subList(0,2).toArray(new Quest[0]);
    }

    public boolean checkAnswer(String userAnswer, Quest quest) {
        return quest.getAnswer().equals(userAnswer);
    }

}
