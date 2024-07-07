package org.lior.gamenight.Repositories;

import org.lior.gamenight.Entities.AppUserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends CrudRepository<AppUserDetails, Long> {
    Optional<AppUserDetails> findByEmail(String email);
}
