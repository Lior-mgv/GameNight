package org.lior.gamenight.Services;

import jakarta.transaction.Transactional;
import org.lior.gamenight.Abstractions.AppUserPrincipal;
import org.lior.gamenight.Repositories.UserDetailsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private final UserDetailsRepository repository;

    public AppUserDetailsService(UserDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).map(AppUserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
