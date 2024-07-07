package org.lior.gamenight.Repositories;

import org.lior.gamenight.Entities.Venue;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends ListCrudRepository<Venue, Long> {
}
