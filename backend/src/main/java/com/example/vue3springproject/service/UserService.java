package com.example.vue3springproject.service;

import com.example.vue3springproject.entity.User;
import com.example.vue3springproject.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPasswordHash());

        user.setPasswordHash(hashedPassword);

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            return false;
        }

        userRepository.deleteById(id);
        return true;
    }

    public User updateUser(Long id, User newUser) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            return null;
        }

        existingUser.setLogin(newUser.getLogin());

        String hashedPassword = passwordEncoder.encode(newUser.getPasswordHash());
        existingUser.setPasswordHash(hashedPassword);

        return userRepository.save(existingUser);
    }

    public User authenticate(String login, String password) {
        Optional<User> optionalUser = userRepository.findByLogin(login);

        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            return null;
        }

        return user;
    }

    public boolean existsByLogin(String login) {
        return userRepository.findByLogin(login).isPresent();
    }
}
