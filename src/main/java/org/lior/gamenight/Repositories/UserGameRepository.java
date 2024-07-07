package org.lior.gamenight.Repositories;

import org.lior.gamenight.Entities.AppUser;
import org.lior.gamenight.Entities.UserGame;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface UserGameRepository extends ListCrudRepository<UserGame, Long> {
    List<UserGame> findAllByUserAndInCollection(AppUser user, boolean inCollection);
    List<UserGame> findAllByUserAndInFavoriteList(AppUser user, boolean inFavorites);
}
