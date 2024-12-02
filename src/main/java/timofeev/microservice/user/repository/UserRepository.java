package timofeev.microservice.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import timofeev.microservice.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
