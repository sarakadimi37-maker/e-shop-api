package fr.utilix.eshop.api.domain.services;

import fr.utilix.eshop.api.enumeration.Role;
import fr.utilix.eshop.api.exception.UtilixException;
import fr.utilix.eshop.api.exposition.dtos.request.AddressRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.request.CustomerRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.request.RegisterRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.request.UserRequestDTO;
import fr.utilix.eshop.api.mappers.AddressMapper;
import fr.utilix.eshop.api.mappers.CustomerMapper;
import fr.utilix.eshop.api.persistence.entities.AddressEntity;
import fr.utilix.eshop.api.persistence.entities.CustomerEntity;
import fr.utilix.eshop.api.persistence.entities.UserEntity;
import fr.utilix.eshop.api.persistence.repositories.AddressRepository;
import fr.utilix.eshop.api.persistence.repositories.CustomerRepository;
import fr.utilix.eshop.api.persistence.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public void create(RegisterRequestDTO request) {

        if (userRepository.existsByEmail(request.user().email())) {
            throw new UtilixException("Cet email est déjà utilisé");
        }

        // creer un user eb bdd
        UserRequestDTO userDto = request.user();
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDto.email());
        userEntity.setRole(Role.ROLE_USER);
        userEntity.setPassword(passwordEncoder.encode(userDto.password()));

        userRepository.save(userEntity);

        // creation d'address d'utilisateur
        AddressRequestDTO addressDto = request.address();
        AddressEntity address = AddressMapper.toEntity(addressDto);
        addressRepository.save(address);

        // créer customer
        CustomerRequestDTO customerDto = request.customer();
        CustomerEntity customer = CustomerMapper.toEntity(customerDto);
        customer.setUser(userEntity);
        customer.setAddress(address);
        customerRepository.save(customer);

    }
}
