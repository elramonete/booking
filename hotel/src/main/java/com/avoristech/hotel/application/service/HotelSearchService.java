package com.avoristech.hotel.application.service;


import com.avoristech.hotel.application.dto.GetHotelSearchRequestDto;
import com.avoristech.hotel.application.dto.HotelSearchRequestDto;
import com.avoristech.hotel.application.dto.SearchRequestDto;
import com.avoristech.hotel.domain.exceptions.EntityNotFoundException;
import com.avoristech.hotel.domain.model.HotelSearchRequestEntity;
import com.avoristech.hotel.infrastructure.mapper.HotelGetSearchRequestMapper;
import com.avoristech.hotel.infrastructure.mapper.HotelSearchRequestMapper;
import com.avoristech.hotel.infrastructure.repository.HotelSearchRespository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class HotelSearchService {

    @Autowired
    private HotelSearchRequestMapper hotelSearchRequestMapper;
    @Autowired
    private HotelGetSearchRequestMapper hotelGetSearchRequestMapper;
    @Autowired
    private HotelSearchRespository hotelSearchRespository;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "hotel";
    private static final String COLLECTION_NAME = "hotel";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();

    public HotelSearchService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Processes a hotel search request.
     *
     * @param request the hotel search request entity
     * @return the hotel search request DTO
     */
    public HotelSearchRequestDto processSearch(HotelSearchRequestEntity request) {
        request.setSearchId(generateSearchId());

        sendToKafka(convertToJson(objectMapper, request));

        return hotelSearchRequestMapper.toDto(saveToDatabase(request)
                .orElseThrow(() -> new EntityNotFoundException("Error al conectar con la base de datos")));
    }

    private String generateSearchId() {
        return IntStream.range(0, 4)
                .mapToObj(i -> String.valueOf(random.nextInt(10)))
                .collect(Collectors.joining());
    }

    private String convertToJson(ObjectMapper objectMapper, Object value) {
        return Optional.ofNullable(value)
                .map(v -> {
                    try {
                        return objectMapper.writeValueAsString(v);
                    } catch (JsonProcessingException e) {
                        throw new EntityNotFoundException("Error serializing object to JSON");
                    }

                })
                .orElseThrow(() -> new EntityNotFoundException("Error al convertir a JSON"));
    }

    private void sendToKafka(String jsonString) {
        try {
            kafkaTemplate.send("hotel_availability_searches", jsonString).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Reinterrumpir el hilo
            throw new EntityNotFoundException("El hilo fue interrumpido mientras se conectaba al servidor de Kafka " + e);
        } catch (ExecutionException e) {
            throw new EntityNotFoundException("No se pudo conectar al servidor de Kafka " +e);
        } catch (Exception e) {
            throw new EntityNotFoundException("Ocurrió un error inesperado al conectar al servidor de Kafka " + e);
        }
    }

    private Optional<HotelSearchRequestEntity> saveToDatabase(HotelSearchRequestEntity request) {
        try {
            return Optional.of(hotelSearchRespository.save(request));
        } catch (MongoException e) {
            return Optional.empty();
        }
    }


    /**
     * Retrieves the search count for a given search ID.
     *
     * @param searchId the ID of the search request
     * @return a {@link SearchRequestDto} containing the search details and count
     * @throws RuntimeException if no document matches the provided search ID
     */
    public SearchRequestDto getSearchCount(String searchId) {
        HotelSearchRequestEntity hotelSearchRequestEntity = findToDatabase(searchId)
                .orElseThrow(() -> new RuntimeException("No document matches the provided searchId."));

        long count = countDocumentToDatabase(hotelSearchRequestEntity);

        GetHotelSearchRequestDto hotelGetSearchRequestDto = hotelGetSearchRequestMapper.toDto(hotelSearchRequestEntity);
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        searchRequestDto.setGetHotelSearchRequestDto(hotelGetSearchRequestDto);
        searchRequestDto.setCount(count);
        searchRequestDto.setSearchId(searchId);

        return searchRequestDto;
    }


    public Optional<HotelSearchRequestEntity> findToDatabase(String searchId) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
            Bson query = new Document("searchId", searchId);
            return Optional.ofNullable(collection.find(query).first())
                    .map(doc -> {
                        try {
                            return objectMapper.readValue(doc.toJson(), HotelSearchRequestEntity.class);
                        } catch (Exception e) {
                            throw new EntityNotFoundException("Error parsing document to entity " + e);
                        }
                    });
        } catch (Exception e) {
            throw new EntityNotFoundException("Error connecting to MongoDB " +e);
        }
    }

    public long countDocumentToDatabase(HotelSearchRequestEntity hotelSearchRequestEntity) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            Document query = new Document("hotelId", hotelSearchRequestEntity.getHotelId())
                    .append("checkIn", hotelSearchRequestEntity.getCheckIn())
                    .append("checkOut", hotelSearchRequestEntity.getCheckOut())
                    .append("ages", new Document("$all", hotelSearchRequestEntity.getAges()));

            return collection.countDocuments(query);
        } catch (Exception e) {
            throw new EntityNotFoundException("Error connecting to MongoDB");
        }
    }



}
