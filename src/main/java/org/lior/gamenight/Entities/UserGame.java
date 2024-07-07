package org.lior.gamenight.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.lior.gamenight.Validation.Annotations.EitherOr;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@EitherOr(firstField = "inCollection", secondField = "inFavoriteList")
public class UserGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Game game;

    @ManyToOne
    private AppUser user;

    private boolean inCollection;

    private boolean inFavoriteList;

}
