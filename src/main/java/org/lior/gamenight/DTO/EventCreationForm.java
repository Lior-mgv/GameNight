package org.lior.gamenight.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.lior.gamenight.Entities.AppUser;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class EventCreationForm {
    private AppUser host;

    @Future
    private LocalDateTime dateTime;

    @Positive
    private Integer maxPeople;

    private String description;

    private String language;

    private Long venueId;

}
