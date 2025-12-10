package fr.utilix.eshop.api.exposition.dtos.response;


public record AddressResponseDTO(
        Long id,
        String street,
        String city,
        String zipCode,
        String country
) {}
