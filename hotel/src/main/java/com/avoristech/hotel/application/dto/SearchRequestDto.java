package com.avoristech.hotel.application.dto;



public final class SearchRequestDto {

    private  String searchId;
    private long count;
    private  GetHotelSearchRequestDto getHotelSearchRequestDto;

    public SearchRequestDto() {
    }

    public SearchRequestDto(String searchId, long count, GetHotelSearchRequestDto getHotelSearchRequestDto) {

        this.searchId = searchId;
        this.getHotelSearchRequestDto = getHotelSearchRequestDto;
        this.count = count;
    }

    public String getSearchId() {
        return searchId;
    }
    public void setSearchId(String searchId){
        this.searchId = searchId;
    }

    public GetHotelSearchRequestDto getGetHotelSearchRequestDto() {
        return getHotelSearchRequestDto;
    }
    public void setGetHotelSearchRequestDto(GetHotelSearchRequestDto getHotelSearchRequestDto){
        this.getHotelSearchRequestDto = getHotelSearchRequestDto;
    }

    public long getCount() {
        return count;
    }
    public void setCount(long count){
        this.count = count;
    }
}
