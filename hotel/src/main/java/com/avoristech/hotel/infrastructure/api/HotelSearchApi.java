package com.avoristech.hotel.infrastructure.api;


import com.avoristech.hotel.application.dto.HotelSearchRequestDto;
import com.avoristech.hotel.application.dto.SearchRequestDto;
import com.avoristech.hotel.domain.model.HotelSearchRequestEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "hotel", description = "Operaciones de consulta de hotel")
public interface HotelSearchApi {

    @Operation(summary = "Reserva de un hotel")
    @ApiResponse(responseCode = "200", description = "Reserva realizada correctamente", content = @Content(schema = @Schema(implementation = HotelSearchRequestDto.class)))
    @ApiResponse(responseCode = "404", description = "Error en la reserva")
    @PostMapping("/search")
    ResponseEntity<HotelSearchRequestDto> searchHotel(@RequestBody HotelSearchRequestEntity request);

    @Operation(summary = "Obtener los datos de una reserva, y el número de búsqueda similares")
    @ApiResponse(responseCode = "200", description = "Se han encontrado una reserva", content = @Content(schema = @Schema(implementation = SearchRequestDto.class)))
    @ApiResponse(responseCode = "404", description = "No hemos encontrado ninguna reserva")
    @GetMapping("/count")
    ResponseEntity<SearchRequestDto> getCount(@RequestParam( name = "searchId", defaultValue =  "0134",required = true)  String searchId);
}

