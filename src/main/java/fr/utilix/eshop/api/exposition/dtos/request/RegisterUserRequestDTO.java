package fr.utilix.eshop.api.exposition.dtos.request;

import fr.utilix.eshop.api.enumeration.Role;
import fr.utilix.eshop.api.persistence.entities.UserEntity;

public record RegisterUserRequestDTO(String email, String password) {

    public UserEntity toEntity(){
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setRole(Role.USER);
        // on ne set pas le mot de passe dans le Mapper
        return user;
    }
}
