package org.lior.gamenight.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private AppUser host;

    private String description;

    private LocalDateTime dateTime;

    @Positive
    private Integer maxParticipants;

    @Positive
    private Integer minAge;

    @Enumerated(EnumType.STRING)
    private Language language;

    @OneToMany(mappedBy = "event")
    private List<Feedback> feedbacks = new ArrayList<>();

    @ManyToOne
    private Venue venue;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<AppUser> attendants = new HashSet<>();

    @OneToMany(mappedBy = "event")
    private List<GameVote> gameVotes = new ArrayList<>();

    public void addAttendant(AppUser user){
        attendants.add(user);
    }

    public void removeAttendant(long id) {
        attendants.removeIf(a -> a.getId() == id);
    }
}
