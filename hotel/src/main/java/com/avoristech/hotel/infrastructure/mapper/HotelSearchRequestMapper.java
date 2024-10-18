package com.avoristech.hotel.infrastructure.mapper;

import com.avoristech.hotel.application.dto.HotelSearchRequestDto;
import com.avoristech.hotel.domain.model.HotelSearchRequestEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelSearchRequestMapper extends GenericMapper<HotelSearchRequestDto, HotelSearchRequestEntity> {

    @Override
    HotelSearchRequestDto toDto(HotelSearchRequestEntity hotelSearchRequestEntity);

    @Override
    HotelSearchRequestEntity toEntity(HotelSearchRequestDto hotelSearchRequestDto);
}
