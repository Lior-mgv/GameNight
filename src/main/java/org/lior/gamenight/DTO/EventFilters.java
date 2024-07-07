package org.lior.gamenight.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EventFilters {
    private String language;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    private boolean hostedOnly;

    private boolean attendedOnly;

}
