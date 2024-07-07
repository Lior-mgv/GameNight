package org.lior.gamenight.Services;

import org.lior.gamenight.DTO.UserRegistrationForm;
import org.lior.gamenight.Mappers.UserRegistrationMapper;
import org.lior.gamenight.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final UserRepository repository;

    private final PasswordEncoder encoder;

    private final UserRegistrationMapper mapper;

    public RegistrationService(UserRepository repository, PasswordEncoder encoder, UserRegistrationMapper mapper) {
        this.repository = repository;
        this.encoder = encoder;
        this.mapper = mapper;
    }

    public void registerUser(UserRegistrationForm registrationForm){
        registrationForm.setPassword(encoder.encode(registrationForm.getPassword()));
        var user = mapper.mapToUser(registrationForm);
        repository.save(user);
    }
}
