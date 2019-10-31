package ru.matmech.jCourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.matmech.jCourse.domain.Player;
import ru.matmech.jCourse.repositories.PlayerRepository;

import java.util.List;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository repository;

    public PlayerService() {}

    public Player findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Player> getAll() {
        return repository.findAll();
    }

    public void create(Player player) {
        repository.save(player);
    }

    public void update(Player updatedPlayer) {
        Player playerToBeUpdated = repository.getOne(updatedPlayer.getId());
        repository.save(updatedPlayer);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
