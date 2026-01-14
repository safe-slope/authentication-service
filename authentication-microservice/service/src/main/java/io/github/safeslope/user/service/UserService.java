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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User update(Integer id, User updated) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        existing.setUsername(updated.getUsername());
        existing.setPassword(passwordEncoder.encode(updated.getPassword()));
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
