package org.lior.gamenight.Services;

import org.lior.gamenight.Entities.Game;
import org.lior.gamenight.Repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Optional<Game> findGame(String name){
        return gameRepository.findByName(name);
    }

    public Optional<Game> findGame(Long id){
        return gameRepository.findById(id);
    }
}
