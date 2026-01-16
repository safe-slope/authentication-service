package io.github.safeslope;

import io.github.safeslope.entities.User;
import io.github.safeslope.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    private User testUser;
    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1)
                .username(USERNAME)
                .password(PASSWORD)
                .role(User.Role.USER)
                .build();
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetailsWhenUserExists() {
        // Arrange
        when(userService.getByUsername(USERNAME)).thenReturn(testUser);

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(USERNAME);

        // Assert
        assertNotNull(userDetails);
        assertEquals(USERNAME, userDetails.getUsername());
        assertEquals(PASSWORD, userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER")));

        verify(userService).getByUsername(USERNAME);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void loadUserByUsername_shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(userService.getByUsername(USERNAME)).thenReturn(null);

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(USERNAME)
        );
        assertEquals("User not found: " + USERNAME, exception.getMessage());

        verify(userService).getByUsername(USERNAME);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void loadUserByUsername_shouldMapAdminRoleCorrectly() {
        // Arrange
        testUser.setRole(User.Role.ADMIN);
        when(userService.getByUsername(USERNAME)).thenReturn(testUser);

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(USERNAME);

        // Assert
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")));

        verify(userService).getByUsername(USERNAME);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void loadUserByUsername_shouldMapSuperAdminRoleCorrectly() {
        // Arrange
        testUser.setRole(User.Role.SUPER_ADMIN);
        when(userService.getByUsername(USERNAME)).thenReturn(testUser);

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(USERNAME);

        // Assert
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("SUPER_ADMIN")));

        verify(userService).getByUsername(USERNAME);
        verifyNoMoreInteractions(userService);
    }
}