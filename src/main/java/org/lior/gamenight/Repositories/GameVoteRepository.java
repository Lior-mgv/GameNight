package org.lior.gamenight.Repositories;

import org.lior.gamenight.Entities.GameVote;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameVoteRepository extends ListCrudRepository<GameVote, Long> {
    List<GameVote> findByWillBring(boolean willBring);
}
