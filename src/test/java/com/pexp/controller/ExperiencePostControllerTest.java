package com.pexp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pexp.exception.AuthorizationException;
import com.pexp.model.User;
import com.pexp.repository.UserRepository;
import com.pexp.service.ExperiencePostService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.pexp.security.*;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExperiencePostController.class)
@AutoConfigureMockMvc(addFilters = false)   // disables JWT filter for tests
class ExperiencePostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExperiencePostService service;

    @MockBean
    private UserRepository userRepository;
    
    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;


    @Autowired
    private ObjectMapper objectMapper;

    // ✅ ADD YOUR TEST HERE
    @Test
    @WithMockUser(username = "user2", roles = "USER")
    void shouldFailUpdatingOthersPost() throws Exception {

        User user = new User();
        user.setId(2L);
        user.setUsername("user2");

        when(userRepository.findByUsername("user2"))
                .thenReturn(Optional.of(user));

        when(service.updatePost(anyLong(), any(), eq(2L)))
                .thenThrow(new AuthorizationException("Forbidden"));

        mockMvc.perform(put("/api/posts/1")
                .contentType("application/json")
                .content("""
                    {
                      "title": "Hacked",
                      "category": "Tech",
                      "organization": "Google",
                      "role": "Backend Intern",
                      "experience": "Updated experience text",
                      "tips": "Revise Spring Boot",
                      "anonymous": false,
                      "text": "text"
                    }
                """))
                .andExpect(status().isForbidden());
    }
}
