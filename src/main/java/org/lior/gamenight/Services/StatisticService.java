package org.lior.gamenight.Services;

import org.lior.gamenight.Entities.AppUser;
import org.lior.gamenight.Entities.Event;
import org.lior.gamenight.Entities.Game;
import org.lior.gamenight.Entities.GameVote;
import org.lior.gamenight.Repositories.EventRepository;
import org.lior.gamenight.Specifications.EventSpecifications;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticService {
    private final EventRepository eventRepository;

    public StatisticService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<List<Object>> getYearlyActivity(AppUser user){
        var specs = EventSpecifications.attendedByUser(user).and(EventSpecifications.happensBefore(LocalDateTime.now()))
                .and(EventSpecifications.happensAfter(LocalDateTime.now().minusYears(1)));
        var events = eventRepository.findAll(specs);
        List<List<Object>> statistics = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            var date = LocalDateTime.now().minusMonths(i).withDayOfMonth(1);
            var monthlyEvents = events.stream().filter(event -> event.getDateTime()
                            .isAfter(date) && event.getDateTime().isBefore(date.plusMonths(1))).count();
            statistics.add(List.of(date.getMonth().name() + " " + date.getYear(), monthlyEvents));
        }
        return statistics;
    }

    public List<AppUser> getTopPlaymates(AppUser user, int limit){
        var specs = EventSpecifications.attendedByUser(user);
        var events = eventRepository.findAll(specs);
        Map<AppUser, Integer> userFrequency = new HashMap<>();
        for (Event event : events) {
            for (AppUser attendant : event.getAttendants().stream().filter(a -> a.getId() != user.getId()).toList()) {
                if(userFrequency.putIfAbsent(attendant, 1) != null){
                    userFrequency.put(attendant, userFrequency.get(attendant) + 1);
                }
            }
        }
        return userFrequency.entrySet()
                .stream().sorted(Map.Entry.<AppUser, Integer>comparingByValue().reversed()).limit(limit)
                .map(Map.Entry::getKey).toList();
    }

    public List<Game> getMostPlayedGames(AppUser user, int limit){
        var specs = EventSpecifications.attendedByUser(user);
        var events = eventRepository.findAll(specs);
        Map<Game, Integer> gameFrequency = new HashMap<>();
        for (Event event : events) {
            for (Game game : event.getGameVotes().stream().filter(GameVote::isWillBring)
                    .map(GameVote::getGame).distinct().toList()) {
                if(gameFrequency.putIfAbsent(game, 1) != null){
                    gameFrequency.put(game, gameFrequency.get(game) + 1);
                }
            }
        }
        return gameFrequency.entrySet()
                .stream().sorted(Map.Entry.<Game, Integer>comparingByValue().reversed()).limit(limit)
                .map(Map.Entry::getKey).toList();
    }


}
