package org.lior.gamenight.Services;

import org.lior.gamenight.Entities.AppUser;
import org.lior.gamenight.Entities.Game;
import org.lior.gamenight.Entities.UserGame;
import org.lior.gamenight.Repositories.GameRepository;
import org.lior.gamenight.Repositories.UserGameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserGameService {
    private final UserGameRepository userGameRepository;

    private final GameRepository gameRepository;


    public UserGameService(UserGameRepository userGameRepository, GameRepository gameRepository) {
        this.userGameRepository = userGameRepository;
        this.gameRepository = gameRepository;
    }

    public List<Game> getOwnedGames(AppUser user){
        return userGameRepository.findAllByUserAndInCollection(user, true)
                .stream().map(UserGame::getGame).collect(Collectors.toList());
    }

    public List<Game> getFavoriteGames(AppUser user){
        return userGameRepository.findAllByUserAndInFavoriteList(user, true)
                .stream().map(UserGame::getGame).toList();
    }

    public void addToList(AppUser user, Long gameId, String listType) {
        var game = gameRepository.findById(gameId);
        if(game.isEmpty()) return;
        var userGame = new UserGame().setUser(user).setGame(gameRepository.findById(gameId).get());
        switch (listType){
            case "collection" -> {
                userGame.setInCollection(true);
            }
            case "favorites" -> {
                userGame.setInFavoriteList(true);
            }
            default -> {return;}
        }
        userGameRepository.save(userGame);
    }
}
