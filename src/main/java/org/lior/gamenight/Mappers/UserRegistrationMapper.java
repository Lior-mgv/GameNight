package org.lior.gamenight.Mappers;

import org.lior.gamenight.DTO.UserRegistrationForm;
import org.lior.gamenight.Entities.AppUser;
import org.lior.gamenight.Entities.AppUserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationMapper {
    public AppUser mapToUser(UserRegistrationForm registrationForm){
        var userDetails = new AppUserDetails()
                .setEmail(registrationForm.getEmail())
                .setPassword(registrationForm.getPassword())
                .setRole("USER");
        var user = new AppUser()
                .setUsername(registrationForm.getUsername())
                .setUserDetails(userDetails);
        userDetails.setUser(user);
        return user;
    }
}
