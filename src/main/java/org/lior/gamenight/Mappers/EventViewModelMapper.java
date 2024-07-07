package org.lior.gamenight.Mappers;

import org.lior.gamenight.Entities.AppUser;
import org.lior.gamenight.Entities.Event;
import org.lior.gamenight.Services.EventService;
import org.lior.gamenight.ViewModels.EventVM;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EventViewModelMapper {
    private final EventService eventService;

    public EventViewModelMapper(EventService eventService) {
        this.eventService = eventService;
    }

    public EventVM mapToVM(Event event, AppUser user){
        return new EventVM()
                .setId(event.getId())
                .setHost(event.getHost())
                .setDateTime(event.getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm")))
                .setDescription(event.getDescription())
                .setLanguage(event.getLanguage().toString())
                .setVenue(event.getVenue())
                .setMaxParticipants(event.getMaxParticipants())
                .setAttendants(event.getAttendants())
                .setLoggedUserIsAttending(event.getAttendants().stream().anyMatch(a -> a.getId() == user.getId()))
                .setLoggedUserIsHost(event.getHost().getId() == user.getId())
                .setNumOfPeople(event.getAttendants().size())
                .setGames(eventService.getAvailableGames(event))
                .setHasPassed(event.getDateTime().isBefore(LocalDateTime.now()))
                .setLoggedUserLeftFeedback(event.getFeedbacks().stream().anyMatch(f -> f.getAuthor().getId() == user.getId()));
    }
}
