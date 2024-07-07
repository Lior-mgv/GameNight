package org.lior.gamenight.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lior.gamenight.Validation.Annotations.EitherOr;

@Entity
@Getter
@Setter
@EitherOr(firstField = "wantToPlay", secondField = "willBring")
public class GameVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AppUser user;

    @ManyToOne
    private Game game;

    @ManyToOne
    private Event event;

    private Boolean wantToPlay;

    private boolean willBring;

}
