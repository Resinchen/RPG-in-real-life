package ru.matmech.jCourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.matmech.jCourse.domain.User;
import ru.matmech.jCourse.repositories.PlayerRepository;

import java.util.List;

@Service
public class PlayerService {
    @Autowired
    PlayerRepository repository;

    public PlayerService() {}

    public User findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public void create(User user) {
        repository.save(user);
    }

    public void update(User updatedUser) {
        User userToBeUpdated = repository.getOne(updatedUser.getId());
        repository.save(updatedUser);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
