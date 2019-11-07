package ru.matmech.jCourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.matmech.jCourse.domain.Perk;
import ru.matmech.jCourse.domain.User;
import ru.matmech.jCourse.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    public UserService() {}

    public User findById(Long id) {
        return repository.findById(id).orElse(User.NullUser);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public void create(User user) {
        repository.save(user);
    }

    public void addPerk(User user, Perk perk) {
        user.getPerks().add(perk);
        repository.save(user);
    }
}
