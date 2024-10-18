package com.avoristech.hotel.infrastructure.kafkaConsumer;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Service
@Component
public class HotelSearchConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelSearchConsumer.class);

    @KafkaListener(topics = "hotel_availability_searches", groupId = "hotel-search-group")
    public void consume(ConsumerRecord<String, String> consumerRecord) {
        LOGGER.info("Mensaje recibido {}", consumerRecord);
    }
}

