package com.pexp.service;

import java.util.List;
import org.springframework.data.domain.Page;
import com.pexp.model.ExperiencePost;

public interface ExperiencePostService {
    ExperiencePost createPost(ExperiencePost post, Long userId);
    Page<ExperiencePost> getAllPosts(
            int page, int size, String sortBy,
            String category, String keyword);
    ExperiencePost getPostById(Long id);
    ExperiencePost updatePost(Long id, ExperiencePost post, Long userId);
    void deletePost(Long id, Long userId);
}
