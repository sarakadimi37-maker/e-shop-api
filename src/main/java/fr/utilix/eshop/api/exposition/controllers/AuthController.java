package fr.utilix.eshop.api.exposition.controllers;

import fr.utilix.eshop.api.exposition.dtos.request.LoginUserRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.request.RegisterUserRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.LoginUserResponseDTO;
import fr.utilix.eshop.api.persistence.entities.UserEntity;
import fr.utilix.eshop.api.persistence.repositories.UserRepository;
import fr.utilix.eshop.api.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserRequestDTO request) {
        boolean alreadyExists = userRepository.existsByEmail(request.email());
        if (alreadyExists) {
            String response = "Cet email est déjà utilisé !";
            return ResponseEntity.badRequest().body(response);
        }

        UserEntity user = request.toEntity();
        // 👇 On SET le mot de passe depuis le Controller, pas depuis le Mapper
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);

        String response = "Utilisateur inscrit avec succès !";
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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




