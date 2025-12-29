package fr.utilix.eshop.api.exposition.controllers;

import fr.utilix.eshop.api.domain.services.OrderService;
import fr.utilix.eshop.api.exposition.PagenateUtils;
import fr.utilix.eshop.api.exposition.dtos.response.OrderResponseDTO;
import fr.utilix.eshop.api.exposition.dtos.request.OrderRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size,
                                                         @RequestParam(defaultValue = "id, asc") String[] sort,
                                                      @RequestParam() Long customerId){

        String sortField = sort[0];
        String sortStr = sort.length > 1 ? sort[1] : "asc";

        Sort sortDirection = null;
        if(sortStr.equalsIgnoreCase("desc")){
            sortDirection = Sort.by(sortField).descending();
        }else{
            sortDirection = Sort.by(sortField).ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sortDirection);

        Page<OrderResponseDTO> response = orderService.findAll(pageable, customerId);
        Map<String, Object> result = PagenateUtils.getResponse(response);
        return ResponseEntity.ok(result);
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

    @DeleteMapping("/item/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id){
        orderService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }


}
