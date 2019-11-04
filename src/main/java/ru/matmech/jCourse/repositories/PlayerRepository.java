package ru.matmech.jCourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.matmech.jCourse.domain.User;

public interface PlayerRepository extends JpaRepository<User, Long> {
}
