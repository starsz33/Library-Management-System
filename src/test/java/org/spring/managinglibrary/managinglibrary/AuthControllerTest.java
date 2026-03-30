package org.spring.managinglibrary.managinglibrary;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.spring.managinglibrary.managinglibrary.model.Member;
import org.spring.managinglibrary.managinglibrary.model.Role;
import org.spring.managinglibrary.managinglibrary.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class AuthControllerTest {

    @Autowired
    private WebApplicationContext context;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    private MockMvc mockMvc() {
        return MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void testLoginSuccess() throws Exception {

        Member mockMember = new Member();
        mockMember.setEmail("test@library.com");
        mockMember.setPassword("password123");
        mockMember.setName("Test User");
        mockMember.setRole(Role.MEMBER);
        when(userDetailsService.loadUserByUsername("test@library.com"))
                .thenReturn(mockMember);
        when(jwtService.generateToken(any(Member.class)))
                .thenReturn("mock-jwt-token");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(
                        mockMember, null, mockMember.getAuthorities()));
        //requesting body
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", "test@library.com");
        loginRequest.put("password", "password123");

        // 4. Perform and assert
        mockMvc().perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-jwt-token"));
    }

    @Test
    void testLoginWithWrongCredentialsShouldReturn401() throws Exception {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new org.springframework.security.authentication.BadCredentialsException("Bad credentials"));

        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", "wrong@library.com");
        loginRequest.put("password", "wrongpass");

        mockMvc().perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }
}