package com.pexp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pexp.model.ExperiencePost;

public interface ExperiencePostRepository
        extends JpaRepository<ExperiencePost, Long> {

    Page<ExperiencePost> findByCategoryIgnoreCase(
            String category, Pageable pageable);

    Page<ExperiencePost> findByTitleContainingIgnoreCaseOrExperienceContainingIgnoreCase(
            String title, String experience, Pageable pageable);

    Page<ExperiencePost> findByCategoryIgnoreCaseAndTitleContainingIgnoreCase(
            String category, String title, Pageable pageable);
}
