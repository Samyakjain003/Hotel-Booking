package com.samyakj820.hotel.service.services;

import com.samyakj820.hotel.service.entities.Hotel;

import java.util.List;

public interface HotelService {

    //save hotel
    Hotel saveHotel(Hotel hotel);

    //get all hotels
    List<Hotel> getAllHotels();

    //get single user by hotelId
    Hotel getHotel(String hotelId);

    //delete hotel
    void deleteHotel(String hotelId);

    //update hotel
    Hotel updateHotel(Hotel user);
}
