package com.grebennikovas.viewpoint.security;

import com.grebennikovas.viewpoint.security.rbac.Role;
import com.grebennikovas.viewpoint.security.rbac.RoleRepository;
import com.grebennikovas.viewpoint.users.User;
import com.grebennikovas.viewpoint.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Service
@Transactional
public class UserRepositoryDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsernameWithPrivileges(username);
        if (user == null)
            throw new UsernameNotFoundException("username not found");
        return new ViewPointUserDetails(user);
    }

}
