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
    // Valid BCrypt hash for "password" with cost 10
    private static final String ENCODED_PASSWORD = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";

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
    void create_shouldThrowExceptionWhenPasswordIsNull() {
        // Arrange
        User userWithNullPassword = User.builder()
                .username("testuser")
                .password(null)
                .role(User.Role.USER)
                .build();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.create(userWithNullPassword));
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void create_shouldThrowExceptionWhenPasswordIsEmpty() {
        // Arrange
        User userWithEmptyPassword = User.builder()
                .username("testuser")
                .password("")
                .role(User.Role.USER)
                .build();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.create(userWithEmptyPassword));
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
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

    @Test
    void update_shouldNotEncodeAlreadyEncodedPassword() {
        // Arrange
        // Valid BCrypt hash for "oldpassword" with cost 10
        String oldEncodedPassword = "$2a$10$e0MYzXyjpJS7Pd0RVvHwHe1hjrYcwdNPZcoazSsNjOhZMBBaibeRi";
        User existingUser = User.builder()
                .id(1)
                .username("testuser")
                .password(oldEncodedPassword)
                .role(User.Role.USER)
                .build();

        User updatedUser = User.builder()
                .username("testuser")
                .password(ENCODED_PASSWORD)
                .role(User.Role.ADMIN)
                .build();
        
        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        userService.update(1, updatedUser);

        // Assert
        verify(passwordEncoder, never()).encode(ENCODED_PASSWORD);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertEquals(ENCODED_PASSWORD, userCaptor.getValue().getPassword());
    }

    @Test
    void update_shouldNotChangePasswordWhenNullProvided() {
        // Arrange
        User existingUser = User.builder()
                .id(1)
                .username("testuser")
                .password(ENCODED_PASSWORD)
                .role(User.Role.USER)
                .build();

        User updatedUser = User.builder()
                .username("newusername")
                .password(null)
                .role(User.Role.ADMIN)
                .build();
        
        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        userService.update(1, updatedUser);

        // Assert
        verify(passwordEncoder, never()).encode(any());
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertEquals(ENCODED_PASSWORD, userCaptor.getValue().getPassword());
    }

    @Test
    void update_shouldNotChangePasswordWhenEmptyStringProvided() {
        // Arrange
        User existingUser = User.builder()
                .id(1)
                .username("testuser")
                .password(ENCODED_PASSWORD)
                .role(User.Role.USER)
                .build();

        User updatedUser = User.builder()
                .username("newusername")
                .password("")
                .role(User.Role.ADMIN)
                .build();
        
        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        userService.update(1, updatedUser);

        // Assert
        verify(passwordEncoder, never()).encode(any());
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertEquals(ENCODED_PASSWORD, userCaptor.getValue().getPassword());
    }
}
