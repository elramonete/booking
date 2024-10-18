package com.avoristech.hotel.infrastructure.repository;

import com.avoristech.hotel.domain.model.HotelSearchRequestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HotelSearchRespository extends MongoRepository<HotelSearchRequestEntity,String> {
}
