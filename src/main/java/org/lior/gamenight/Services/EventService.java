package org.lior.gamenight.Services;

import jakarta.transaction.Transactional;
import org.lior.gamenight.DTO.EventCreationForm;
import org.lior.gamenight.DTO.EventFilters;
import org.lior.gamenight.Entities.AppUser;
import org.lior.gamenight.Entities.Event;
import org.lior.gamenight.Entities.Game;
import org.lior.gamenight.Entities.GameVote;
import org.lior.gamenight.Mappers.EventCreationFormMapper;
import org.lior.gamenight.Repositories.EventRepository;
import org.lior.gamenight.Repositories.GameVoteRepository;
import org.lior.gamenight.Repositories.UserRepository;
import org.lior.gamenight.Specifications.EventSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final GameVoteRepository gameVoteRepository;
    private final EventCreationFormMapper eventMapper;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, GameVoteRepository gameVoteRepository, EventCreationFormMapper eventMapper, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.gameVoteRepository = gameVoteRepository;
        this.eventMapper = eventMapper;
        this.userRepository = userRepository;
    }

    public List<Event> getAllUpcomingEvents(){
        return eventRepository.findAllByDateTimeAfter(LocalDateTime.now());
    }

    public List<Game> getAvailableGames(Event event){
        return gameVoteRepository.findByWillBring(true).stream().filter(gameVote -> gameVote.getEvent() == event)
                .map(GameVote::getGame).toList();
    }

    public List<Event> getUpcomingUserEvents(AppUser user, EventFilters filters, String sortBy){
        var specs = EventSpecifications.happensAfter(LocalDateTime.now())
                .and(EventSpecifications.attendedByUser(user))
                .and(getSpecifications(filters));
        if(filters.isHostedOnly()){
            specs = specs.and(EventSpecifications.isHost(user));
        }
        if(filters.isAttendedOnly()){
            specs = specs.and(Specification.not(EventSpecifications.isHost(user)));
        }
        return sortEvents(eventRepository.findAll(specs), sortBy);
    }
    public List<Event> getPassedUserEvents(AppUser user, EventFilters filters, String sortBy){
        var specs = EventSpecifications.happensBefore(LocalDateTime.now())
                .and(EventSpecifications.attendedByUser(user))
                .and(getSpecifications(filters));
        if(filters.isHostedOnly()){
            specs = specs.and(EventSpecifications.isHost(user));
        }
        if(filters.isAttendedOnly()){
            specs = specs.and(Specification.not(EventSpecifications.isHost(user)));
        }
        return sortEvents(eventRepository.findAll(specs), sortBy);
    }

    public List<Event> getAllUpcomingEvents(EventFilters filters, String sortBy){
        var specs = EventSpecifications.happensAfter(LocalDateTime.now()).and(getSpecifications(filters));
        var events = eventRepository.findAll(specs);
        return sortEvents(events, sortBy);
    }

    private List<Event> sortEvents(List<Event> events, String sortBy){
        switch (sortBy){
            case "date" -> {
                events.sort((e1, e2) -> e1.getDateTime().isBefore(e2.getDateTime()) ? -1 :
                        e1.getDateTime().isAfter(e2.getDateTime()) ? 1 : 0);
            }
            case "numOfPeople" -> {
                events.sort(Comparator.comparingInt(e -> e.getAttendants().size()));
            }
            case "numOfGames" -> {
                events.sort(Comparator.comparingInt(e -> e.getGameVotes().stream().filter(GameVote::isWillBring)
                        .toList().size()));
            }
        }
        return events;
    }

    @Transactional
    public void addAttendant(Long eventId, Long userId){
        var user = userRepository.findById(userId);
        var eventOpt = eventRepository.findById(eventId);
        if(eventOpt.isEmpty() || user.isEmpty()) return;
        var event = eventOpt.get();
        event.addAttendant(user.get());
        user.get().addEvent(event);
        eventRepository.save(event);
    }

    @Transactional
    public void removeAttendant(Long eventId, Long userId) {
        var user = userRepository.findById(userId);
        var eventOpt = eventRepository.findById(eventId);
        if(eventOpt.isEmpty()  || user.isEmpty()) return;
        var event = eventOpt.get();
        event.removeAttendant(user.get().getId());
        user.get().removeEvent(eventId);
        eventRepository.save(event);
    }

    private Specification<Event> getSpecifications(EventFilters filters){
        return EventSpecifications.hasLanguage(filters.getLanguage())
                .and(EventSpecifications.happensAfter(filters.getDateFrom() == null ? null : filters.getDateFrom().atStartOfDay()))
                .and(EventSpecifications.happensBefore(filters.getDateTo() == null ? null : filters.getDateTo().atStartOfDay()));
    }

    public void createEvent(EventCreationForm eventForm) {
        eventRepository.save(eventMapper.mapToEntity(eventForm));
    }

    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    public Optional<Event> getEvent(Long eventId) {
        return eventRepository.findById(eventId);
    }
}
