package org.lior.gamenight.Controllers;

import org.lior.gamenight.Abstractions.AppUserPrincipal;
import org.lior.gamenight.Services.StatisticService;
import org.lior.gamenight.Services.UserGameService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
public class UserController {
    private final UserGameService userGameService;
    private final StatisticService statisticService;

    public UserController(UserGameService userGameService, StatisticService statisticService) {
        this.userGameService = userGameService;
        this.statisticService = statisticService;
    }

    @GetMapping("/profile")
    public String getUserProfile(Model model, @AuthenticationPrincipal AppUserPrincipal principal){
        var user = principal.getUserDetails().getUser();
        model.addAttribute("yearlyActivity", statisticService.getYearlyActivity(user));
        model.addAttribute("playmates", statisticService.getTopPlaymates(user, 3));
        model.addAttribute("mostPlayed", statisticService.getMostPlayedGames(user, 3));
        model.addAttribute("user", user);
        model.addAttribute("favoriteGames", userGameService.getFavoriteGames(user));
        model.addAttribute("ownedGames", userGameService.getOwnedGames(user));
        return "profile";
    }

}
