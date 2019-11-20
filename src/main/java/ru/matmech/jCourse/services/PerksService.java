package ru.matmech.jCourse.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.matmech.jCourse.domain.Perk;
import ru.matmech.jCourse.domain.User;
import ru.matmech.jCourse.repositories.PerkRepository;

import java.util.List;

@Service
public class PerksService {
    private final static Logger logger = LoggerFactory.getLogger(PerksService.class);

    @Autowired
    PerkRepository repository;

    public Perk findByName(String name) {
        return repository.findByName(name).orElse(Perk.NullPerk);
    }

    public Perk[] findPerksForUser(User user) {
        logger.info("Find perks for user {}", user.getId());
        List<Perk> perks = repository.findByStrengthLessThanEqualAndEnduranceLessThanEqualAndCharismaLessThanEqualAndIntelligenceLessThanEqualAndLuckyLessThanEqual(user.getStrength(),
                user.getEndurance(), user.getCharisma(), user.getIntelligence(), user.getLucky());

        return perks.toArray(new Perk[0]);
    }

    public List<Perk> getAll() {
        return repository.findAll();
    }
}
