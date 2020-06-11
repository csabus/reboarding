package hu.progmasters.reboarding.repositories;

import hu.progmasters.reboarding.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
