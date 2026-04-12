package fr.utilix.eshop.api.exposition.controllers;

import fr.utilix.eshop.api.domain.services.RegisterService;
import fr.utilix.eshop.api.exposition.dtos.request.LoginUserRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.request.RegisterRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.LoginUserResponseDTO;
import fr.utilix.eshop.api.persistence.entities.UserEntity;
import fr.utilix.eshop.api.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;
    private final RegisterService registerService;


    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(
            @RequestBody RegisterRequestDTO request) {

        registerService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "Utilisateur inscrit avec succès !"));
    }



    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDTO> authenticateUser(@RequestBody LoginUserRequestDTO request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserEntity authenticatedUser = (UserEntity) authentication.getPrincipal();
        String token = jwtUtil.generateToken(authenticatedUser);

        LoginUserResponseDTO response = LoginUserResponseDTO.fromEntity(token, authenticatedUser);
        return ResponseEntity.ok(response);
    }
}




