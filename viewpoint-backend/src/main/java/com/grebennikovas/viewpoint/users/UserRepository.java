package com.grebennikovas.viewpoint.users;

import com.grebennikovas.viewpoint.datasets.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles r LEFT JOIN FETCH r.privileges WHERE u.username = :username")
    User findByUsernameWithPrivileges(@Param("username") String username);

}
