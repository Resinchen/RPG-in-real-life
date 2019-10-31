package ru.matmech.jCourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.matmech.jCourse.domain.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
