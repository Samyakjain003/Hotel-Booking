package com.samyakj820.rating.service.repositories;

import com.samyakj820.rating.service.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, String > {
    List<Rating> findByUserId(String userId);

    List<Rating> findByHotelId(String hotelID);
}
