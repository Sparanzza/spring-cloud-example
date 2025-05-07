package com.sparanzza.springcloud.msvc.users.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparanzza.springcloud.msvc.users.entities.Role;
import com.sparanzza.springcloud.msvc.users.entities.User;
import com.sparanzza.springcloud.msvc.users.repositories.RoleRepository;
import com.sparanzza.springcloud.msvc.users.repositories.UserRepository;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(getRoles(user));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<User> update(User user, Long id) {
        
        return userRepository.findById(id).map(userDb -> {
            userDb.setEmail(user.getEmail());
            userDb.setUsername(user.getUsername());
            userDb.setEnabled(user.isEnabled());
            if (user.isEnabled() == null) {
                userDb.setEnabled(true);
            } else {
                userDb.setEnabled(user.isEnabled());
            }
            List<Role> roles = getRoles(user);
            user.setRoles(roles);

            return Optional.of(userRepository.save(userDb));
        }).orElseGet(Optional::empty);
    }

    private List<Role> getRoles(User user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> roleOptional = roleRepository.findByName("ROLE_USER");
        roleOptional.ifPresent(roles::add);
        if (user.isAdmin()) {
            Optional<Role> roleAdminOptional = roleRepository.findByName("ROLE_ADMIN");
            roleAdminOptional.ifPresent(roles::add);
        }
        return roles;
    }
}
