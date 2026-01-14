package io.github.safeslope.user.service;

import io.github.safeslope.entities.User;
import io.github.safeslope.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    // BCrypt format pattern: $2[a|b|y]$[cost]$[22 character salt][31 character hash]
    // Cost parameter can be 1-2 digits (range 04-31)
    // Salt and hash use BCrypt's base64 encoding: [./A-Za-z0-9]
    private static final String BCRYPT_PATTERN = "^\\$2[aby]\\$\\d{1,2}\\$[./A-Za-z0-9]{53}$";

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User get(Integer id) {
        return userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException(id));
    }

    public User getByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }
        return user;
    }

    public User create(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User update(Integer id, User updated) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        existing.setUsername(updated.getUsername());
        // Only encode and update password if it's provided and not already encoded
        if (updated.getPassword() != null && !updated.getPassword().isEmpty()) {
            // Check if password is already encoded using BCrypt format pattern
            if (!updated.getPassword().matches(BCRYPT_PATTERN)) {
                existing.setPassword(passwordEncoder.encode(updated.getPassword()));
            } else {
                existing.setPassword(updated.getPassword());
            }
        }
        existing.setRole(updated.getRole());
        existing.setTenant(updated.getTenant());
        return userRepository.save(existing);
    }

    public void delete(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
