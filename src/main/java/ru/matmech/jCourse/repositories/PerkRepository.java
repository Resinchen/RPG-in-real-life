package ru.matmech.jCourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.matmech.jCourse.domain.Perk;

import java.util.Optional;

public interface PerkRepository extends JpaRepository<Perk, Long> {
    Optional<Perk> findByName(String namePerk);
}
