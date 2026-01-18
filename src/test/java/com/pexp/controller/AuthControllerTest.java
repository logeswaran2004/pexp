package com.pexp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pexp.repository.UserRepository;
import com.pexp.security.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;
    
    @MockBean
    private UserRepository userRepository;


    @Autowired
    private ObjectMapper objectMapper;
    

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @MockBean
    private PasswordEncoder passwordEncoder;
    
    @MockBean
    private UserDetailsService userDetailsService;   

    @Test
    void shouldLoginSuccessfully() throws Exception {

        UserDetails mockUser = org.springframework.security.core.userdetails.User
                .withUsername("user1")
                .password("encoded-pass")
                .roles("USER")
                .build();

        when(userDetailsService.loadUserByUsername("user1"))
                .thenReturn(mockUser);

        when(passwordEncoder.matches("password", "encoded-pass"))
                .thenReturn(true);

        when(jwtUtil.generateToken("user1"))
                .thenReturn("fake-jwt-token");

        mockMvc.perform(post("/api/auth/login")
                .contentType("application/json")
                .content("""
                    {
                      "username": "user1",
                      "password": "password"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"));
    }
}
