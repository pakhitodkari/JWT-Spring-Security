package pv.security.jwt.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pv.security.jwt.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
	Optional<User> findByEmail(String email);

}
