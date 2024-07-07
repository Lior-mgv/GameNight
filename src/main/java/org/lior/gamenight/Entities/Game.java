package org.lior.gamenight.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Min(1)
    @Max(5)
    private int complexity;

    private String durationMinutes;

    private String playersNumber;

    private String ageRestriction;

    private String iconPath;

    @Enumerated(EnumType.STRING)
    private GameCategory category;

    @OneToMany(mappedBy = "game")
    private List<GameVote> gameVotes = new ArrayList<>();

    @OneToMany(mappedBy = "game")
    private List<UserGame> userGames = new ArrayList<>();

}
