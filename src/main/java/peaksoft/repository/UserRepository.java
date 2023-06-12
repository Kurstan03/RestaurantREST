package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.user.response.UserResponse;
import peaksoft.entities.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("select new peaksoft.dto.user.response.UserResponse" +
            "(u.id, concat(u.firstName, ' ', u.lastName), u.dateOfBirth, u.email," +
            " u.phoneNumber, u.role, u.experience)" +
            " from User u where u.accepted = true ")
    List<UserResponse> getAllUsers();
    @Query("select new peaksoft.dto.user.response.UserResponse" +
            "(u.id, concat(u.firstName,' ', u.lastName), u.dateOfBirth, u.email," +
            " u.phoneNumber, u.role, u.experience)" +
            " from User u where u.accepted = false ")
    List<UserResponse> getAllApplication();

    boolean existsByEmail(String email);
    @Query("select new peaksoft.dto.user.response.UserResponse" +
            "(u.id, concat(u.firstName,' ', u.lastName), u.dateOfBirth, u.email," +
            " u.phoneNumber, u.role, u.experience)" +
            " from User u where u.id = ?1")
    Optional<UserResponse> getUserById(Long userId);
}