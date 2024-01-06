package pv.security.jwt.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pv.security.jwt.user.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>
{
	Optional<UserInfo> findByEmail(String email);

}
