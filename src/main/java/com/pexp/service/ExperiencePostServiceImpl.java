package com.pexp.service;

import com.pexp.exception.*;
import com.pexp.model.ExperiencePost;
import com.pexp.repository.ExperiencePostRepository;
import com.pexp.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class ExperiencePostServiceImpl implements ExperiencePostService {

    private static final Logger log = LoggerFactory.getLogger(ExperiencePostServiceImpl.class);

    private final ExperiencePostRepository repository;

    public ExperiencePostServiceImpl(ExperiencePostRepository repository) {
        this.repository = repository;
    }

    @Override
    public ExperiencePost createPost(ExperiencePost post, Long userId) {
        log.info("Creating post for userId={}", userId);

        post.setUserId(userId);
        ExperiencePost savedPost = repository.save(post);

        log.info("Post created successfully with id={}", savedPost.getId());
        return savedPost;
    }

    @Override
    public Page<ExperiencePost> getAllPosts(
            int page, int size, String sortBy,
            String category, String keyword) {

        log.info("Fetching posts page={}, size={}, sortBy={}, category={}, keyword={}",
                page, size, sortBy, category, keyword);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        if (category != null && keyword != null) {
            log.debug("Filtering by category={} and keyword={}", category, keyword);
            return repository.findByCategoryIgnoreCaseAndTitleContainingIgnoreCase(category, keyword, pageable);
        }

        if (category != null) {
            log.debug("Filtering by category={}", category);
            return repository.findByCategoryIgnoreCase(category, pageable);
        }

        if (keyword != null) {
            log.debug("Filtering by keyword={}", keyword);
            return repository.findByTitleContainingIgnoreCaseOrExperienceContainingIgnoreCase(keyword, keyword, pageable);
        }

        log.debug("Fetching all posts without filters");
        return repository.findAll(pageable);
    }

    @Override
    public ExperiencePost getPostById(Long id) {
        log.info("Fetching post by id={}", id);

        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Post not found with id={}", id);
                    return new ResourceNotFoundException("Post not found with id " + id);
                });
    }

    @Override
    public ExperiencePost updatePost(Long id, ExperiencePost updatedPost, Long userId) {
        log.info("Update request for postId={} by userId={}", id, userId);

        ExperiencePost existingPost = getPostById(id);

        if (!existingPost.getUserId().equals(userId) && !SecurityUtil.isAdmin()) {
            log.warn("Unauthorized update attempt for postId={} by userId={}", id, userId);
            throw new AuthorizationException("You do not have permission to update this post");
        }

        log.debug("Updating postId={}", id);
        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setCategory(updatedPost.getCategory());
        existingPost.setOrganization(updatedPost.getOrganization());
        existingPost.setRole(updatedPost.getRole());
        existingPost.setExperience(updatedPost.getExperience());
        existingPost.setTips(updatedPost.getTips());
        existingPost.setAnonymous(updatedPost.isAnonymous());
        existingPost.setText(updatedPost.getText());

        ExperiencePost savedPost = repository.save(existingPost);
        log.info("Post updated successfully postId={}", savedPost.getId());

        return savedPost;
    }

    @Override
    public void deletePost(Long id, Long userId) {
        log.info("Delete request for postId={} by userId={}", id, userId);

        ExperiencePost existingPost = getPostById(id);

        if (!existingPost.getUserId().equals(userId) && !SecurityUtil.isAdmin()) {
            log.warn("Unauthorized delete attempt for postId={} by userId={}", id, userId);
            throw new AuthorizationException("You do not have permission to delete this post");
        }

        repository.delete(existingPost);
        log.info("Post deleted successfully postId={}", id);
    }
}
