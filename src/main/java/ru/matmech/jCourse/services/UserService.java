package ru.matmech.jCourse.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.matmech.jCourse.Utils.UserUtils;
import ru.matmech.jCourse.domain.Perk;
import ru.matmech.jCourse.domain.User;
import ru.matmech.jCourse.repositories.UserRepository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository repository;

    public UserService() {}

    public User create(Long id, String name) {
        logger.info("Create new user {}({})", id, name);
        User user = new User(id, name);
        repository.save(user);

        return user;
    }

    public void updateStat(Long id, String stat, int dPoint) {
        logger.info("Update stat {} at {} points for user {}", stat, dPoint, id);
        User user = findByIdForWrite(id);

        if (UserUtils.isNotNullUser(user) && user.getFreePoints() > 0) {
            user.addFreePoints(-dPoint);

            switch (stat) {
                case "strength":
                    user.addStrength(dPoint);
                    break;
                case "endurance":
                    user.addEndurance(dPoint);
                    break;
                case "charisma":
                    user.addCharisma(dPoint);
                    break;
                case "intelligence":
                    user.addIntelligence(dPoint);
                    break;
                case "lucky":
                    user.addLucky(dPoint);
                    break;
            }

            repository.save(user);
        }
    }

    public String[] getUserInfo(Long id) {
        logger.info("Get user info by {}", id);
        User user = findByIdForRead(id);
        if (UserUtils.isNotNullUser(user)) {
            return new String[] {user.getName(),
                    user.getLevel().toString(),
                    user.getExperience().toString(),
                    user.getFreePoints().toString()};
        } else {
            return new String[0];
        }
    }

    public Perk[] getUserPerks(Long id) {
        logger.info("Get perks user by {}", id);
        User user = findByIdForRead(id);
        if (UserUtils.isNotNullUser(user)) {
            return user.getPerks().toArray(new Perk[0]);
        } else {
            return new Perk[0];
        }
    }

    public void addExp(Long id, int experience) {
        logger.info("Add experience ({} points) to user {}", experience, id);
        User user = findByIdForWrite(id);

        if (UserUtils.isNotNullUser(user)) {
            user.addExperience(experience);

            while (user.getExperience() >= UserUtils.lvlLimitExp[user.getLevel()]) {
                logger.info("Lvl up {}; {} experience points left", user.getLevel(), user.getExperience());
                user.addExperience(-UserUtils.lvlLimitExp[user.getLevel()]);
                user.setLevel(user.getLevel()+1);
                user.addFreePoints(5);
            }

            repository.save(user);
        }
    }

    public User getUserById(Long id) {
        logger.info("Get user by {}", id);

        return findByIdForRead(id);
    }

    public void addPerk(Long id, Perk perk) {
        logger.info("Add perk {} to user {}", perk.getName(), id);
        logger.info(" perk {} to user {}", perk.getId(), id);
        User user = findByIdForWrite(id);

        if (UserUtils.isNotNullUser(user)) {
            user.getPerks().add(perk);
            repository.save(user);
        }
    }
//transactions
    /*!!!*/public void getCurrentQuest(Long id) {
        throw new NotImplementedException();
    }

    @Cacheable("users")
    private User findByIdForRead(Long id) {
        logger.info("Find user by {} for read", id);
        return repository.findById(id).orElse(User.NullUser);
    }

    @CachePut("users")
    private User findByIdForWrite(Long id) {
        logger.info("Find user by {} for write", id);
        return repository.findById(id).orElse(User.NullUser);
    }
}