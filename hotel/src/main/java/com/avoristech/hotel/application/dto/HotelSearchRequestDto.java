package com.avoristech.hotel.application.dto;



import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


public record HotelSearchRequestDto(String searchId, @JsonIgnore String hotelId, @JsonIgnore String checkIn,
                                    @JsonIgnore String checkOut, @JsonIgnore List<Integer> ages) { }
