package com.samyakj820.user.service.services;

import com.samyakj820.user.service.entities.Hotel;
import com.samyakj820.user.service.entities.Rating;
import com.samyakj820.user.service.entities.User;
import com.samyakj820.user.service.exceptions.ResourceNotFoundException;
import com.samyakj820.user.service.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        List<User> users = userRepository.findAll();
        for(User user : users) {
            fetchUserRatings(user);
        }
        return users;
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id " + userId + " is not found on server!!"));
        fetchUserRatings(user);
        return user;
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void fetchUserRatings(User user) {
        Rating ratings[] = restTemplate.getForObject("http://localhost:8083/ratings/users/" + user.getUserId(), Rating[].class);
        for(Rating rating : ratings) {
            fetchHotelForRating(rating);
        }
        user.setRatings(Arrays.asList(ratings));
    }

    public void fetchHotelForRating(Rating rating) {
        ResponseEntity<Hotel> response = restTemplate.getForEntity("http://localhost:8082/hotels/" + rating.getHotelId(), Hotel.class);
        Hotel hotel = response.getBody();
        rating.setHotel(hotel);
    }
}
