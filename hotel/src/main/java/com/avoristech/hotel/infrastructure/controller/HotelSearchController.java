package com.avoristech.hotel.infrastructure.controller;


import com.avoristech.hotel.application.dto.HotelSearchRequestDto;
import com.avoristech.hotel.application.dto.SearchRequestDto;
import com.avoristech.hotel.application.service.HotelSearchService;
import com.avoristech.hotel.domain.model.HotelSearchRequestEntity;
import com.avoristech.hotel.infrastructure.api.HotelSearchApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HotelSearchController implements HotelSearchApi {

    @Autowired
    private HotelSearchService hotelSearchService;

    @Override
    public ResponseEntity<HotelSearchRequestDto> searchHotel(HotelSearchRequestEntity request) {
        HotelSearchRequestDto hotelSearchRequestDto = hotelSearchService.processSearch(request);
        return ResponseEntity.ok(hotelSearchRequestDto);
    }


    @Override
    public ResponseEntity<SearchRequestDto> getCount(String searchId) {
        SearchRequestDto response = hotelSearchService.getSearchCount(searchId);
        return ResponseEntity.ok(response);
    }
}
