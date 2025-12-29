package fr.utilix.eshop.api.exposition.controllers;

import fr.utilix.eshop.api.domain.services.FavoriteService;
import fr.utilix.eshop.api.exposition.dtos.request.FavoriteRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.FavoriteResponseDTO;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping("/{customerId}")
    public ResponseEntity<List<FavoriteResponseDTO>>  getFavorites(@PathVariable("customerId") Long customerId){
        List<FavoriteResponseDTO> favorites = favoriteService.findAllFavorite(customerId);
        return ResponseEntity.ok(favorites);
    }

    @PostMapping
    public ResponseEntity<FavoriteResponseDTO> create(@Valid @RequestBody FavoriteRequestDTO request){
        FavoriteResponseDTO response = favoriteService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @DeleteMapping
    public ResponseEntity<Integer> delete(@PathParam("customerId") Long customerId, @PathParam("productId") Long productId){
        return ResponseEntity.ok(favoriteService.delete(customerId, productId));
    }
}
