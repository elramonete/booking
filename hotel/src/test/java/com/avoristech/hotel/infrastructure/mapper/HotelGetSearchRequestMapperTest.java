package com.avoristech.hotel.infrastructure.mapper;

import com.avoristech.hotel.application.dto.GetHotelSearchRequestDto;
import com.avoristech.hotel.domain.model.HotelSearchRequestEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HotelGetSearchRequestMapperTest {

    private HotelGetSearchRequestMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(HotelGetSearchRequestMapper.class);
    }

    @Test
    void testToDto() {
        HotelSearchRequestEntity entity = new HotelSearchRequestEntity();
        entity.setSearchId("123");
        entity.setHotelId("456");
        entity.setCheckIn("2024-10-10");
        entity.setCheckOut("2024-10-15");
        entity.setAges(List.of(25, 30));

        GetHotelSearchRequestDto dto = mapper.toDto(entity);

        assertEquals("123", dto.searchId());
        assertEquals("456", dto.hotelId());
        assertEquals("2024-10-10", dto.checkIn());
        assertEquals("2024-10-15", dto.checkOut());
        assertEquals(List.of(25, 30), dto.ages());
    }

    @Test
    void testToEntity() {
        GetHotelSearchRequestDto dto = new GetHotelSearchRequestDto("123","456","2024-10-10","2024-10-15",List.of(25, 30));

        HotelSearchRequestEntity entity = mapper.toEntity(dto);

        assertEquals("123", entity.getSearchId());
        assertEquals("456", entity.getHotelId());
        assertEquals("2024-10-10", entity.getCheckIn());
        assertEquals("2024-10-15", entity.getCheckOut());
        assertEquals(List.of(25, 30), entity.getAges());
    }
}