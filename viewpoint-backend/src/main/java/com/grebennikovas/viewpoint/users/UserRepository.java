package com.grebennikovas.viewpoint.users;

import com.grebennikovas.viewpoint.datasets.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    User save(User person);
}
