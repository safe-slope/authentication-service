package io.github.safeslope.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            io.github.safeslope.user.service.UserNotFoundException.class,
            io.github.safeslope.tenant.repository.TenantNotFoundException.class,
            io.github.safeslope.skiresort.service.SkiResortNotFoundException.class
    })
    public ResponseEntity<String> handleNotFound(RuntimeException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}