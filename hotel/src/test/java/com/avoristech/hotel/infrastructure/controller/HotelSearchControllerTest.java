package com.avoristech.hotel.infrastructure.controller;

import com.avoristech.hotel.application.dto.HotelSearchRequestDto;
import com.avoristech.hotel.application.dto.SearchRequestDto;
import com.avoristech.hotel.application.service.HotelSearchService;
import com.avoristech.hotel.domain.model.HotelSearchRequestEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class HotelSearchControllerTest {

    @Mock
    private HotelSearchService hotelSearchService;

    @InjectMocks
    private HotelSearchController hotelSearchController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
     void testSearchHotel() {
        HotelSearchRequestEntity request = new HotelSearchRequestEntity();
        HotelSearchRequestDto expectedResponse = new HotelSearchRequestDto("123", "1234","15/11/2024", "19/11/2024", null);

        when(hotelSearchService.processSearch(request)).thenReturn(expectedResponse);

        ResponseEntity<HotelSearchRequestDto> response = hotelSearchController.searchHotel(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
    @Test
    void testGetCount() {
        // Arrange
        String searchId = "123";
        SearchRequestDto mockResponse = new SearchRequestDto();
        when(hotelSearchService.getSearchCount(searchId)).thenReturn(mockResponse);

        // Act
        ResponseEntity<SearchRequestDto> response = hotelSearchController.getCount(searchId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(hotelSearchService, times(1)).getSearchCount(searchId);
    }
}
