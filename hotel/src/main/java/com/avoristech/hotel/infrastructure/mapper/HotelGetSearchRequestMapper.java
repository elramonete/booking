package com.avoristech.hotel.infrastructure.mapper;

import com.avoristech.hotel.application.dto.GetHotelSearchRequestDto;
import com.avoristech.hotel.domain.model.HotelSearchRequestEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelGetSearchRequestMapper extends GenericMapper<GetHotelSearchRequestDto, HotelSearchRequestEntity> {

    @Override
    GetHotelSearchRequestDto toDto(HotelSearchRequestEntity hotelSearchRequestEntity);

    @Override
    HotelSearchRequestEntity toEntity(GetHotelSearchRequestDto hotelSearchRequestDto);
}
