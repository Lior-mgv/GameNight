package org.lior.gamenight.Controllers;

import org.lior.gamenight.Abstractions.AppUserPrincipal;
import org.lior.gamenight.Services.GameService;
import org.lior.gamenight.Services.UserGameService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class GameController {

    private final GameService gameService;

    private final UserGameService userGameService;


    public GameController(GameService gameService, UserGameService userGameService) {
        this.gameService = gameService;
        this.userGameService = userGameService;
    }


    @GetMapping("/searchGame")
    public String searchGamePage(Model model,
                                 @RequestParam String listType){
        model.addAttribute("listType", listType);
        return "searchGame";
    }

    @PostMapping("/searchGame")
    public RedirectView searchGame(RedirectAttributes redirectAttributes,
                                   @RequestParam String name,
                                   @RequestParam String listType){
        redirectAttributes.addAttribute("listType", listType);
        var game = gameService.findGame(name);
        if(game.isPresent()){
            redirectAttributes.addFlashAttribute("foundGames", game.get());
            return new RedirectView("searchGame", true, false);
        }
        return new RedirectView("searchGame", true, false);
    }

    @PostMapping("/addUserGame")
    public RedirectView addUserGame(@AuthenticationPrincipal AppUserPrincipal principal,
                                    @RequestParam Long gameId,
                                    @RequestParam String listType){
        var user = principal.getUserDetails().getUser();
        userGameService.addToList(user, gameId, listType);
        return new RedirectView("profile", true, false);
    }
}
