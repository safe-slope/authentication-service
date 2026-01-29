package io.github.safeslope.api.v1.controller;

import io.github.safeslope.JwtService;
import io.github.safeslope.api.v1.dto.AuthMeDto;
import io.github.safeslope.api.v1.dto.AuthRequestDto;

import io.github.safeslope.entities.Tenant;
import io.github.safeslope.entities.User;
import io.github.safeslope.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager, UserService userService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequestDto authRequest) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            if (authentication.isAuthenticated()) {
                User user = userService.getByUsername(authRequest.getUsername());
                return jwtService.generateToken(user);
            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'USER')")
    public AuthMeDto me(){
        //extract userid, role and tenantid from the token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            User user = userService.getByUsername(authentication.getName());
            Tenant tenant = user.getTenant();
            return new AuthMeDto(user.getUsername(), user.getRole(), tenant.getName());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @GetMapping("/logout")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<Void> logout(){
        return ResponseEntity.noContent().build();
    }
}
