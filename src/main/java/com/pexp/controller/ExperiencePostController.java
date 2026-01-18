package com.pexp.controller;

import java.util.List;
import java.util.stream.Collectors;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


import org.springframework.web.bind.annotation.*;

import com.pexp.dto.ExperiencePostRequestDTO;
import com.pexp.dto.ExperiencePostResponseDTO;
import com.pexp.mapper.ExperiencePostMapper;
import com.pexp.model.ExperiencePost;
import com.pexp.model.User;
import com.pexp.repository.UserRepository;
import com.pexp.service.ExperiencePostService;
import com.pexp.util.SecurityUtil;
import org.springframework.data.domain.Page;
import jakarta.validation.Valid;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/posts")
public class ExperiencePostController {

    private final ExperiencePostService service;
    private final UserRepository userRepository;

    public ExperiencePostController(ExperiencePostService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    /**
     * Helper method to get current user's ID from database
     */
    private Long getCurrentUserId() {
        String username = SecurityUtil.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found: " + username));
        return user.getId();
    }

    
    @PostMapping
    public ExperiencePostResponseDTO createPost(
            @Valid @RequestBody ExperiencePostRequestDTO requestDTO) {

        Long userId = getCurrentUserId();
        ExperiencePost post = ExperiencePostMapper.toEntity(requestDTO);
        ExperiencePost savedPost = service.createPost(post, userId);

        return ExperiencePostMapper.toResponseDTO(savedPost);
    }

    @GetMapping
    public Page<ExperiencePostResponseDTO> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword) {

        Page<ExperiencePost> posts =
                service.getAllPosts(page, size, sortBy, category, keyword);

        return posts.map(ExperiencePostMapper::toResponseDTO);
    }


    
    @GetMapping("/{id}")
    public ExperiencePostResponseDTO getPostById(@PathVariable Long id) {

        ExperiencePost post = service.getPostById(id);
        return ExperiencePostMapper.toResponseDTO(post);
    }

    @PutMapping("/{id}")
    public ExperiencePostResponseDTO updatePost(
            @PathVariable Long id,
            @Valid @RequestBody ExperiencePostRequestDTO requestDTO) {

        Long userId = getCurrentUserId();
        ExperiencePost updatedEntity = ExperiencePostMapper.toEntity(requestDTO);
        ExperiencePost updatedPost = service.updatePost(id, updatedEntity, userId);

        return ExperiencePostMapper.toResponseDTO(updatedPost);
    }

   
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        service.deletePost(id, userId);
    }
}
