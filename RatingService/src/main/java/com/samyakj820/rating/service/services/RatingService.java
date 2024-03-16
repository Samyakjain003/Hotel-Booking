package com.samyakj820.rating.service.services;


import com.samyakj820.rating.service.entities.Rating;

import java.util.List;

public interface RatingService {

    //create rating
    Rating create(Rating rating);

    //get all ratings
    List<Rating> getAllRatings();

    //get all by UserId
    List<Rating> getAllRatingsByUserId(String userId);

    //get all by hotel
    List<Rating> getAllRatingsByHotelId(String hotelId);
}
