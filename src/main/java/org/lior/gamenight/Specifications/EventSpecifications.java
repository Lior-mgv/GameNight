package org.lior.gamenight.Specifications;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.lior.gamenight.Entities.AppUser;
import org.lior.gamenight.Entities.Event;
import org.lior.gamenight.Entities.Event_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class EventSpecifications {
    public static Specification<Event> hasLanguage(String language){
        return (root, query, cb) ->
                language == null ? cb.conjunction() : cb.equal(root.get(Event_.language), language);
    }

    public static Specification<Event> happensAfter(LocalDateTime date){
        return (root, query, cb) -> date == null ? cb.conjunction() : cb.greaterThan(root.get(Event_.dateTime), date);
    }

    public static Specification<Event> happensBefore(LocalDateTime date) {
        return (root, query, cb) -> date == null ? cb.conjunction() : cb.lessThan(root.get(Event_.dateTime), date);
    }

    public static Specification<Event> isHost(AppUser user) {
        return (root, query, cb) -> user == null ? cb.conjunction() : cb.equal(root.get(Event_.host).get("id"),
                user.getId());
    }

    public static Specification<Event> attendedByUser(AppUser user) {
        return (root, query, cb) -> {
            Join<Event, AppUser> attendantsJoin = root.join("attendants", JoinType.INNER);
            return cb.equal(attendantsJoin.get("id"), user.getId());
        };
    }


}
