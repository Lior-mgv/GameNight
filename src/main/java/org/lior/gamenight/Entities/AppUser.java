package org.lior.gamenight.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private AppUserDetails userDetails;

    private String profilePicPath = "/img/default-profile-pic.jpg";

    @NotNull
    private String username;

    private String personalInfo;

    private LocalDate dateOfBirth;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    private List<Feedback> feedbacksLeft = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "reviewee")
    private List<Review> reviewsAbout = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Event> attendedEvents = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "host")
    private List<Event> hostedEvents = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<GameVote> gameVotes = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<UserGame> userGames = new ArrayList<>();

    public void addEvent(Event event){
        attendedEvents.add(event);
    }

    public void removeEvent(long id) {
        attendedEvents.removeIf(e -> e.getId() == id);
    }
}
