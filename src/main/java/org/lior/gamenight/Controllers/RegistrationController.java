package org.lior.gamenight.Controllers;

import jakarta.validation.Valid;
import org.lior.gamenight.DTO.UserRegistrationForm;
import org.lior.gamenight.Services.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RegistrationController {

    private final RegistrationService service;

    public RegistrationController(RegistrationService service) {
        this.service = service;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
        if(!model.containsAttribute("user")){
            model.addAttribute("user", new UserRegistrationForm());
        }
        return "registration";
    }

    @PostMapping("/register")
    public RedirectView registerUser(@Valid UserRegistrationForm registrationForm, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "user",
                    bindingResult);
            redirectAttributes.addFlashAttribute("user", registrationForm);
            return new RedirectView("/registration", true, false);
        }
        service.registerUser(registrationForm);
        return new RedirectView("/login", true, false);
    }

}
