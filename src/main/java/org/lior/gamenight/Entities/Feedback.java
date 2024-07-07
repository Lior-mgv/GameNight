package org.lior.gamenight.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Accessors(chain = true)

public class Feedback {
    @Id
    private Long id;

    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private AppUser author;

    @ManyToOne
    private Event event;

    @ManyToMany
    @JoinTable(
            name = "playedGame",
            joinColumns = @JoinColumn(name = "feedback_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private List<Game> playedGames = new ArrayList<>();

    @OneToMany(mappedBy = "feedback")
    private List<Review> reviews = new ArrayList<>();

}
