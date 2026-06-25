package fr.utilix.eshop.api.exposition.dtos.request;

public record RegisterRequestDTO(
        CustomerRequestDTO customer,
        UserRequestDTO user,
        AddressRequestDTO address
) {

}
