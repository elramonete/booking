package com.avoristech.hotel.domain.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="hotel")
@JsonIgnoreProperties(ignoreUnknown = true)
public final class HotelSearchRequestEntity {
    @JsonProperty("searchId")
    private String searchId;
    @JsonProperty("hotelId")
    private String hotelId;
    @JsonProperty("checkIn")
    private  String checkIn;
    @JsonProperty("checkOut")
    private  String checkOut;
    @JsonProperty("ages")
    private List<Integer> ages;

    @JsonCreator
    public HotelSearchRequestEntity(@JsonProperty("searchId") String searchId,
                                    @JsonProperty("hotelId") String hotelId,
                                    @JsonProperty("checkIn") String checkIn,
                                    @JsonProperty("checkOut") String checkOut,
                                    @JsonProperty("ages") List<Integer> ages) {
        this.searchId = searchId;
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = ages;
    }

    public HotelSearchRequestEntity() {
        this.hotelId = null;
        this.checkIn = null;
        this.checkOut = null;
        this.ages = null;
    }

    // Getters
    public String getHotelId() {
        return hotelId;
    }
    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }
    public String getCheckIn() {
        return checkIn;
    }
    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }
    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }
    public List<Integer> getAges() {
        return ages;
    }
    public void setAges(List<Integer> ages) {
        this.ages = ages;
    }
    public String getSearchId() { return searchId; }

    public void setSearchId(String searchId){ this.searchId = searchId;}
}
