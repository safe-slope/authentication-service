package io.github.safeslope.api.v1.controller;

import io.github.safeslope.api.v1.dto.UserDto;
import io.github.safeslope.api.v1.mapper.UserMapper;
import io.github.safeslope.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDto> list() {
        return userMapper.toDtoList(userService.getAll());
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Integer id) {
        return userMapper.toDto(userService.get(id));
    }

    @GetMapping("/username/{username}")
    public UserDto getByUsername(@PathVariable String username) {
        return userMapper.toDto(userService.getByUsername(username));
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto dto) {
        return userMapper.toDto(userService.create(userMapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Integer id, @RequestBody UserDto dto) {
        return userMapper.toDto(userService.update(id, userMapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
