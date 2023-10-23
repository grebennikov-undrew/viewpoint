package com.grebennikovas.viewpoint.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public List<User> findAll() {
        return userRepository.findAll();
    }
    @PostMapping("/")
    public User save(@RequestBody User user) {
        return userRepository.save(user);
    }
}
