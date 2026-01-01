package fr.utilix.eshop.api.domain.services;

import fr.utilix.eshop.api.exception.ResourceNotFoundException;
import fr.utilix.eshop.api.exposition.dtos.request.CustomerRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.CustomerResponseDTO;
import fr.utilix.eshop.api.mappers.CustomerMapper;
import fr.utilix.eshop.api.persistence.entities.CustomerEntity;
import fr.utilix.eshop.api.persistence.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public List<CustomerResponseDTO> findAll(){
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }

    public CustomerResponseDTO findById(Long id){
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer avec  l'id " + id + " n'existe pas."
                ));
        return customerMapper.toDto(customer);
    }


    public CustomerResponseDTO findByUserId(Long userId) {
        CustomerEntity customer = customerRepository.findByUserId(userId)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Customer avec  l'id " + userId + " n'existe pas."
                ));
        return customerMapper.toDto(customer);
    }

    public CustomerResponseDTO create(CustomerRequestDTO dto){
        CustomerEntity entity = customerMapper.toEntity(dto);
        CustomerEntity saved = customerRepository.save(entity);
        return customerMapper.toDto(saved);
    }

    public CustomerResponseDTO update(Long id, CustomerRequestDTO dto){
        CustomerEntity existing = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer " + id + " introuvable."
                ));
        existing.updateForm(dto);
        CustomerEntity saved = customerRepository.save(existing);
        return customerMapper.toDto(saved);
    }

    public void delete(Long id){
        if(!customerRepository.existsById(id)){
            throw new ResourceNotFoundException((
                    "Impossible de supprimer : Customer \" + id + \" introuvable."
            ));
        }
        customerRepository.deleteById(id);
    }


}
