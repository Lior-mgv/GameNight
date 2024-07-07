package org.lior.gamenight.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.lior.gamenight.Abstractions.AppUserPrincipal;
import org.lior.gamenight.DTO.EventCreationForm;
import org.lior.gamenight.DTO.EventFilters;
import org.lior.gamenight.Entities.Language;
import org.lior.gamenight.Mappers.EventViewModelMapper;
import org.lior.gamenight.Repositories.VenueRepository;
import org.lior.gamenight.Services.EventService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.util.stream.Stream;

@Controller
@SessionAttributes("languages")
public class EventsController {

    private final EventService eventService;
    private final VenueRepository venueRepository;
    private final EventViewModelMapper eventMapper;

    public EventsController(EventService eventService, VenueRepository venueRepository, EventViewModelMapper eventMapper) {
        this.eventService = eventService;
        this.venueRepository = venueRepository;
        this.eventMapper = eventMapper;
    }

    @ModelAttribute
    public void addLanguages(Model model){
        model.addAttribute("languages", Stream.of(Language.values()).map(Enum::name).toList());
        model.addAttribute("venues", venueRepository.findAll());
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/browseEvents")
    public String browseEvents(Model model,
                               @AuthenticationPrincipal AppUserPrincipal principal,
                               @ModelAttribute EventFilters filters,
                               @RequestParam(required = false, defaultValue = "none") String sortBy){
        model.addAttribute("filters", filters);
        var user = principal.getUserDetails().getUser();
        var events = eventService.getAllUpcomingEvents(filters, sortBy).stream()
                .map(event -> eventMapper.mapToVM(event, user)).toList();
        model.addAttribute("events", events);

        return "browseEvents";
    }

    @PostMapping("/applyForEvent")
    public RedirectView applyForEvent(@AuthenticationPrincipal AppUserPrincipal principal,
                                      @RequestParam Long eventId){
        var user = principal.getUserDetails().getUser();
        eventService.addAttendant(eventId, user.getId());
        return new RedirectView("/browseEvents", true, false);
    }

    @PostMapping("/resignFromEvent")
    public RedirectView resignFromEvent(HttpServletRequest request,
                                        @AuthenticationPrincipal AppUserPrincipal principal,
                                        @RequestParam Long eventId){
        var user = principal.getUserDetails().getUser();
        eventService.removeAttendant(eventId, user.getId());
        String referer = request.getHeader("Referer");
        return new RedirectView(referer, true, false);
    }

    @GetMapping("/yourEvents")
    public String yourEvents(Model model,
                             @AuthenticationPrincipal AppUserPrincipal principal,
                             @ModelAttribute EventFilters filters,
                             @RequestParam(required = false, defaultValue = "none") String sortBy){
        model.addAttribute("filters", filters);
        var user = principal.getUserDetails().getUser();
        var upcomingEvents = eventService.getUpcomingUserEvents(user, filters, sortBy).stream()
                .map(event -> eventMapper.mapToVM(event, user)).toList();
        var passedEvents = eventService.getPassedUserEvents(user, filters, sortBy).stream()
                .map(event -> eventMapper.mapToVM(event, user)).toList();
        model.addAttribute("upcoming", upcomingEvents);
        model.addAttribute("passed", passedEvents);
        return "yourEvents";
    }

    @GetMapping("/createEvent")
    public String createEvent(Model model,
                              @AuthenticationPrincipal AppUserPrincipal principal,
                              @ModelAttribute EventCreationForm eventForm){
        var user = principal.getUserDetails().getUser();
        if (eventForm.getHost() == null) {
            eventForm.setHost(user);
        }
        model.addAttribute("event", eventForm);
        return "/createEvent";
    }

    @PostMapping("/createEvent")
    public String createEvent(@ModelAttribute @Valid EventCreationForm eventForm,
                              BindingResult bindingResult,
                              Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("event", eventForm);
            return "createEvent";
        }
        eventService.createEvent(eventForm);
        return "redirect:/yourEvents";
    }

    @PostMapping("/deleteEvent")
    public RedirectView deleteEvent(HttpServletRequest request,
                                    @RequestParam Long eventId){
        eventService.deleteEvent(eventId);
        String referer = request.getHeader("Referer");
        return new RedirectView(referer, true, false);
    }

    @GetMapping("/eventDetails")
    public String eventDetails(Model model,
                               @RequestParam Long eventId,
                               @AuthenticationPrincipal AppUserPrincipal principal){
        var user = principal.getUserDetails().getUser();
        model.addAttribute("event", eventMapper.mapToVM(eventService.getEvent(eventId).get(), user));
        return "details";
    }

}
