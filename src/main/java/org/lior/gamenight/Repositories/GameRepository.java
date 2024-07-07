package org.lior.gamenight.Repositories;

import org.lior.gamenight.Entities.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
    Optional<Game> findByName(String name);
}
