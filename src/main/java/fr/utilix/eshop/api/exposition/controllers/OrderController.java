package fr.utilix.eshop.api.exposition.controllers;

import fr.utilix.eshop.api.domain.services.OrderService;
import fr.utilix.eshop.api.exposition.dtos.response.OrderResponseDTO;
import fr.utilix.eshop.api.exposition.dtos.request.OrderRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAll(){
        List<OrderResponseDTO> response = orderService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id){
        OrderResponseDTO response = orderService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<Void> createAndUpdateOrder(
            @PathVariable("customerId") Long customerId,
            @RequestBody OrderRequestDTO dto) {
            orderService.createAndUpdateOrder(customerId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
