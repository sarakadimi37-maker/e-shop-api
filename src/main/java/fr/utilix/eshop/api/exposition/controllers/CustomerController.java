package fr.utilix.eshop.api.exposition.controllers;

import fr.utilix.eshop.api.domain.services.CustomerService;
import fr.utilix.eshop.api.exposition.dtos.request.CustomerRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.CustomerResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAll(){
        List<CustomerResponseDTO> response = customerService.findAll();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<CustomerResponseDTO> getByUserId(@PathVariable Long userId){
        CustomerResponseDTO response = customerService.findByUserId(userId);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long id,
                                                      @Valid @RequestBody CustomerRequestDTO request){
        CustomerResponseDTO response = customerService.update(id, request);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
