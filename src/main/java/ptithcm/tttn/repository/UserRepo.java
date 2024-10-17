package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {


    @Query(value = "SELECT * FROM user WHERE username = ?1  ", nativeQuery = true)
    User findByUsername(String username);
}
