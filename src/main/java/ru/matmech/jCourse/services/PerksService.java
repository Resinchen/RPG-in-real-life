package ru.matmech.jCourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.matmech.jCourse.domain.Perk;
import ru.matmech.jCourse.repositories.PerkRepository;

import java.util.List;

@Service
public class PerksService {
    @Autowired
    PerkRepository repository;

    public Perk findByName(String name) {
        return repository.findByName(name).orElse(Perk.NullPerk);
    }

    public List<Perk> getAll() {
        return repository.findAll();
    }
}
