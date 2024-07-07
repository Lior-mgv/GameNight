package org.lior.gamenight.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Accessors(chain = true)

public class Review {
    @Id
    private Long id;

    private boolean isPositive;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "reviewee_id", nullable = false)
    private AppUser reviewee;

    @ManyToOne
    private Feedback feedback;
}
