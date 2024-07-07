package org.lior.gamenight.Repositories;

import org.lior.gamenight.Entities.Event;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepositoryImplementation<Event, Long> {
    List<Event> findAllByDateTimeAfter(LocalDateTime dateTime);
}
