package fr.utilix.eshop.api.integration;

import fr.utilix.eshop.api.enumeration.Role;
import fr.utilix.eshop.api.persistence.entities.UserEntity;
import fr.utilix.eshop.api.persistence.repositories.UserRepository;
import fr.utilix.eshop.api.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc(addFilters = false)
class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldReturnJwtTokenWhenLoginIsValid() throws Exception {
        persistUser("admin@example.com", "admin123", Role.ROLE_ADMIN);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "admin@example.com",
                                  "password": "admin123"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.email").value("admin@example.com"))
                .andExpect(jsonPath("$.role").value(Role.ROLE_ADMIN.name()));
    }

    @Test
    void shouldRejectAccessWithInvalidToken() throws Exception {
        mockMvc.perform(get("/products")
                .header("Authorization", "Bearer fake_token"))
                .andExpect(status().isUnauthorized());
    }

    // accèes interdit à un role user
    @Test
    void shouldRejectAccessForUserRoleToAdminEndpoint() throws Exception {
        UserEntity user = persistUser("user@example.com", "user123", Role.ROLE_USER);
        String token = jwtUtil.generateToken(user);

        mockMvc.perform(get("/admin/products")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }



    private UserEntity persistUser(String email, String rawPassword, Role role) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role);
        return userRepository.save(user);
    }

    // Connexion valide
    @Test
    void shouldReturnJwtTokenWhenConnectionValid() throws Exception {
        persistUser("user@example.com", "user123", Role.ROLE_USER);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "user@example.com",
                                  "password": "user123"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.email").value("user@example.com"))
                .andExpect(jsonPath("$.role").value(Role.ROLE_USER.name()));
    }

    // Connexion invalide
    @Test
    void shouldReturnUnauthorizedWhenConnectionInvalid() throws Exception {
        persistUser("user@example.com", "423", Role.ROLE_USER);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "user@example.com",
                                  "password": "456"
                                }
                                """))
                .andExpect(status().isUnauthorized());
    }

    // Token expiré
    @Test
    void shouldRejectAccessWithExpiredToken() throws Exception {
        mockMvc.perform(get("/products")
                        .header("Authorization", "Bearer expired_token"))
                .andExpect(status().isUnauthorized());
    }

    // Accès autorisé pour un admin
    @Test
    void shouldAuthorizedForAdminRoleToAdminEndpoint() throws Exception {
        UserEntity user = persistUser("admin@example.com", "admin123", Role.ROLE_ADMIN);
        String token = jwtUtil.generateToken(user);

        mockMvc.perform(post("/admin/product")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "pomme",
                                  "description": "description de pomme",
                                  "imageUrl": "https://i.etsystatic.com/19468294/r/il/a53591/3040112082/il_1588xN.3040112082_g2eb.jpg",
                                  "isActive": true,
                                  "price": 10.0,
                                  "stock": 5,
                                  "discount": 20.0
                    
                                }
                                """))
                .andExpect(status().isCreated());
    }

    // Accès public sans auth
    @Test
    void shouldAccessToPublic() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }


}
