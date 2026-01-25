package io.github.safeslope.user.service;

import io.github.safeslope.entities.User;
import io.github.safeslope.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks private UserService userService;

    @Test
    void get_returnsUser_whenExists() {
            User u = User.builder()
                .username("usr")
                .password("123")
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(u));

        User result = userService.get(1);

        assertThat(result).isSameAs(u);
        verify(userRepository).findById(1);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void create_encodesPassword_andSaves() {
        User u = User.builder()
                .username("usr")
                .password("123")
                .build();

        when(passwordEncoder.encode("123")).thenReturn("ENC(123)");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User saved = userService.create(u);

        assertThat(saved.getPassword()).isEqualTo("ENC(123)");
        verify(passwordEncoder).encode("123");
        verify(userRepository).save(u);
    }


    @Test
    void delete_deletes_whenExists() {
        when(userRepository.existsById(5)).thenReturn(true);

        userService.delete(5);

        verify(userRepository).existsById(5);
        verify(userRepository).deleteById(5);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void getAll_withPageable_returnsPage() {
        User u1 = User.builder().username("user1").password("123").build();
        User u2 = User.builder().username("user2").password("456").build();
        List<User> users = List.of(u1, u2);
        Page<User> userPage = new PageImpl<>(users, PageRequest.of(0, 10), 2);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        Page<User> result = userService.getAll(PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
        verify(userRepository).findAll(any(Pageable.class));
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(passwordEncoder);
    }
}
