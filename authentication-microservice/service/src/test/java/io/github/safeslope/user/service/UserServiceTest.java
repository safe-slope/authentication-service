package io.github.safeslope.user.service;

import io.github.safeslope.entities.User;
import io.github.safeslope.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private static final String RAW_PASSWORD = "plainPassword123";
    private static final String ENCODED_PASSWORD = "$2a$10$encodedPasswordHash";

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1)
                .username("testuser")
                .password(RAW_PASSWORD)
                .role(User.Role.USER)
                .build();
    }

    @Test
    void create_shouldEncodePasswordBeforeSaving() {
        // Arrange
        when(passwordEncoder.encode(RAW_PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        userService.create(testUser);

        // Assert
        verify(passwordEncoder).encode(RAW_PASSWORD);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertEquals(ENCODED_PASSWORD, userCaptor.getValue().getPassword());
    }

    @Test
    void update_shouldEncodePasswordBeforeSaving() {
        // Arrange
        User updatedUser = User.builder()
                .username("testuser")
                .password(RAW_PASSWORD)
                .role(User.Role.ADMIN)
                .build();
        
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.encode(RAW_PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        userService.update(1, updatedUser);

        // Assert
        verify(passwordEncoder).encode(RAW_PASSWORD);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertEquals(ENCODED_PASSWORD, userCaptor.getValue().getPassword());
    }

    @Test
    void create_shouldNotStoreRawPassword() {
        // Arrange
        when(passwordEncoder.encode(RAW_PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        userService.create(testUser);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertNotEquals(RAW_PASSWORD, userCaptor.getValue().getPassword());
    }

    @Test
    void update_shouldNotStoreRawPassword() {
        // Arrange
        User updatedUser = User.builder()
                .username("testuser")
                .password(RAW_PASSWORD)
                .role(User.Role.ADMIN)
                .build();
        
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.encode(RAW_PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        userService.update(1, updatedUser);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertNotEquals(RAW_PASSWORD, userCaptor.getValue().getPassword());
    }
}
