package ru.matmech.jCourse.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.matmech.jCourse.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Cacheable("users")
    Optional<User> findById(Long id);
}
