package com.sfwe496.user.service;

import com.sfwe496.user.model.User;
import com.sfwe496.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
    

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        User user = getUserById(id);
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        return userRepository.save(user);
    }

    public boolean resetPassword(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("Password reset link sent to: " + email); // simulate
            return true;
        }
        return false;
    }
    

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}