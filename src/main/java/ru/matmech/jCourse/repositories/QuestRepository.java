package ru.matmech.jCourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.matmech.jCourse.domain.Quest;

import java.util.List;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    List<Quest> findByMinLevelLessThanEqual(int userLevel);
}
