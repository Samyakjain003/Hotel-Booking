package com.samyakj820.user.service.external.services;

import com.samyakj820.user.service.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@FeignClient(name = "RATINGSERVICE")
public interface RatingService {

    @GetMapping("/ratings/{ratingId}")
    ResponseEntity<Rating> getRating(@PathVariable String ratingId);

    @PostMapping("/ratings")
    ResponseEntity<Rating> createRating(Rating rating);

    @PutMapping("/ratings")
    ResponseEntity<Rating> updateRating(Rating rating);

    @DeleteMapping("/ratings/{ratingId}")
    void deleteRating(@PathVariable String ratingId);
}
