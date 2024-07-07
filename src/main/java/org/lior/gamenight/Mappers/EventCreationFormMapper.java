package org.lior.gamenight.Mappers;

import org.lior.gamenight.DTO.EventCreationForm;
import org.lior.gamenight.Entities.Event;
import org.lior.gamenight.Entities.Language;
import org.lior.gamenight.Repositories.VenueRepository;
import org.springframework.stereotype.Service;

@Service
public class EventCreationFormMapper {
    private final VenueRepository venueRepository;

    public EventCreationFormMapper(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public Event mapToEntity(EventCreationForm form){
        var event = new Event().setHost(form.getHost())
                .setDateTime(form.getDateTime())
                .setDescription(form.getDescription())
                .setLanguage(Language.valueOf(form.getLanguage()))
                .setMaxParticipants(form.getMaxPeople())
                .setVenue(venueRepository.findById(form.getVenueId()).orElse(null));
        event.addAttendant(form.getHost());
        return event;
    }
}
