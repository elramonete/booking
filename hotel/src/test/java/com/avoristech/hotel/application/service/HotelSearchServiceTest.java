package com.avoristech.hotel.application.service;

import com.avoristech.hotel.domain.model.HotelSearchRequestEntity;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class HotelSearchServiceTest {

    @Mock
    private MongoClient mongoClient;

    @Mock
    private MongoDatabase database;

    @Mock
    private MongoCollection<Document> collection;

    @InjectMocks
    private HotelSearchService hotelSearchService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mongoClient.getDatabase(anyString())).thenReturn(database);
        when(database.getCollection(anyString())).thenReturn(collection);
    }

    @Test
    void testCountDocumentToDatabase() {
        HotelSearchRequestEntity entity = new HotelSearchRequestEntity();
        entity.setHotelId("8765mnr");
        entity.setCheckIn("04/08/2024");
        entity.setCheckOut("07/08/2024");
        entity.setAges(Arrays.asList(2,2, 4));

        when(collection.countDocuments(any(Document.class))).thenReturn(3L);

        long count = hotelSearchService.countDocumentToDatabase(entity);

        assertEquals(3L, count);
    }
}
