package com.avoristech.hotel.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


public record GetHotelSearchRequestDto(@JsonIgnore String searchId, String hotelId, String checkIn, String checkOut,
                                       List<Integer> ages) { }
