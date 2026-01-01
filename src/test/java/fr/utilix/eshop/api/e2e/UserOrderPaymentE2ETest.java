package fr.utilix.eshop.api.e2e;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.utilix.eshop.api.config.TestContainerConfig;
import fr.utilix.eshop.api.enumeration.OrderStatus;
import fr.utilix.eshop.api.enumeration.Role;
import fr.utilix.eshop.api.exposition.dtos.request.OrderItemRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.request.OrderRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.CustomerResponseDTO;
import fr.utilix.eshop.api.exposition.dtos.response.ProductResponseDTO;
import fr.utilix.eshop.api.persistence.entities.AddressEntity;
import fr.utilix.eshop.api.persistence.entities.UserEntity;
import fr.utilix.eshop.api.persistence.repositories.AddressRepository;
import fr.utilix.eshop.api.persistence.repositories.UserRepository;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("e2e")
class UserOrderPaymentE2ETest extends TestContainerConfig {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 👉 clé de signature locale uniquement pour les tests
    private static final Key TEST_KEY =  Keys.hmacShaKeyFor("4f1b5272c2b1517f08fbbb09c6ace2fd8b94de0f69dfa3ecb6a445072e0a8866".getBytes(StandardCharsets.UTF_8));
    // ----------------------------------------------------------------------
    // 🔹 1. Helper pour extraire le token JWT de la réponse JSON de /auth/login
    // ----------------------------------------------------------------------
    protected String extractToken(String jsonResponse) {
        try {
            JsonNode root = MAPPER.readTree(jsonResponse);
            if (root.has("token")) {
                return root.get("token").asText();
            } else if (root.has("accessToken")) {
                return root.get("accessToken").asText();
            } else {
                throw new IllegalStateException("Aucun champ 'token' ou 'accessToken' trouvé dans la réponse : " + jsonResponse);
            }
        } catch (Exception e) {
            throw new RuntimeException("Impossible d'extraire le token JWT : " + e.getMessage(), e);
        }
    }



    @Test
    void shouldRegisterLoginAndCreatePaidOrder() throws Exception{

        // 1. créer un compte utilisateur
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "username": "john",
                        "email": "john@example.com",
                        "password": "secret123"
                    }
                """))
                .andExpect(status().isCreated());

        // 2. se connecter et récupérer le token JWT
        String tokenResponse = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    { "email": "john@example.com",
                     "password": "secret123" }
                """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String userJwt = extractToken(tokenResponse); // helper qui parse le JSON

        // créer le customer
        MvcResult customerResult = mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userJwt)
                        .content("""
                    {
                        "userId": %d,
                        "firstName": "John",
                        "lastName": "King",
                        "addressId": %d
                    }
                """.formatted(1, 1)))
                .andExpect(status().isCreated())
                .andReturn();
        String customerJson = customerResult.getResponse().getContentAsString();
        CustomerResponseDTO customer = MAPPER.readValue(customerJson, CustomerResponseDTO.class);



        // 3. créer ou REGISTER ADMIN
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
        {
            "username": "admin",
            "email": "admin@example.com",
            "password": "admin123"
        }
        """))
                .andExpect(status().isCreated());
        // forcé la mise à jour du role en admin
       UserEntity userEntity = userRepository.findByEmail("admin@example.com").orElseThrow();
       userEntity.setRole(Role.ROLE_ADMIN);
       userRepository.save(userEntity);


        /* =====================================================
         * Login admin
         * ===================================================== */
        String adminLoginResponse = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "admin@example.com",
                            "password": "admin123"
                        }
                        """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String adminJwt = extractToken(adminLoginResponse);


        /* =====================================================
         * ADMIN CREATES PRODUCT
         * ===================================================== */
        MvcResult productResult = mockMvc.perform(post("/admin/product")
                        .header("Authorization", "Bearer " + adminJwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Potion",
                                    "description": "description du produit",
                                    "imageUrl": "https://pomme.fr",
                                    "active": true,
                                    "price": 50.0,
                                    "stock": 10,
                                    "discount": 0.0
                                }
                                """))
                .andExpect(status().isCreated()).andReturn();
        String productJson= productResult.getResponse().getContentAsString();
        ProductResponseDTO productResponseDTO = MAPPER.readValue(productJson, ProductResponseDTO.class);

        /* =====================================================
         * USER CREATES ORDER
         * ===================================================== */
        Long customerId = 1L;
        OrderRequestDTO order = new OrderRequestDTO(OrderStatus.PENDING, new OrderItemRequestDTO(2, productResponseDTO.id()));
        mockMvc.perform(post("/orders/{customerId}", customerId)
                        .header("Authorization", "Bearer " + userJwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                MAPPER.writeValueAsString(order)
                        ))
                .andExpect(status().isCreated());

        /* =====================================================
         * VERIFY ORDER STATUS
         * ===================================================== */
        mockMvc.perform(get("/orders/1")
                        .header("Authorization", "Bearer " + userJwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(OrderStatus.PENDING.name()));


    }


}
