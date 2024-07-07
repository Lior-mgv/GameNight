package org.lior.gamenight.ViewModels;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.lior.gamenight.Entities.AppUser;
import org.lior.gamenight.Entities.Game;
import org.lior.gamenight.Entities.Venue;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class EventVM {
    private Long id;

    private String description;

    private String dateTime;

    private Integer maxParticipants;

    private String language;

    private AppUser host;

    private Venue venue;

    private List<Game> games;

    private Set<AppUser> attendants;

    private boolean loggedUserIsAttending;

    private boolean loggedUserIsHost;

    private boolean hasPassed;

    private boolean loggedUserLeftFeedback;

    private int numOfPeople;
}
