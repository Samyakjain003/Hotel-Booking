package com.samyakj820.user.service.controllers;

import com.samyakj820.user.service.entities.User;
import com.samyakj820.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    int retryCount = 1;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user) {

        User userResponse = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
//    @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        logger.info("Retry count: {}", retryCount);
        retryCount++;
        User userResponse = userService.getUser(userId);
        return ResponseEntity.ok(userResponse);
    }

    //creating fallback method for breaker
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception exception) {
        logger.info("Fallback is executed because service is down: ", exception.getMessage());
        User user = User
                .builder()
                .userId("1234")
                .email("dummy@gmail.com")
                .name("dummy")
                .about("This user is created dummy because some service is down")
                .build();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }

    @PutMapping()
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User userResponse = userService.updateUser(user);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

}
