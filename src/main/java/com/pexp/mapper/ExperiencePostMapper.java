package com.pexp.mapper;

import com.pexp.dto.ExperiencePostRequestDTO;
import com.pexp.dto.ExperiencePostResponseDTO;
import com.pexp.model.ExperiencePost;

public class ExperiencePostMapper {

    public static ExperiencePost toEntity(ExperiencePostRequestDTO dto) {
        ExperiencePost post = new ExperiencePost();
        post.setTitle(dto.getTitle());
        post.setCategory(dto.getCategory());
        post.setOrganization(dto.getOrganization());
        post.setRole(dto.getRole());
        post.setExperience(dto.getExperience());
        post.setTips(dto.getTips());
        post.setAnonymous(dto.isAnonymous());
        post.setText(dto.getText());
        return post;
    }

    public static ExperiencePostResponseDTO toResponseDTO(ExperiencePost post) {
        ExperiencePostResponseDTO dto = new ExperiencePostResponseDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setCategory(post.getCategory());
        dto.setOrganization(post.getOrganization());
        dto.setRole(post.getRole());
        dto.setExperience(post.getExperience());
        dto.setTips(post.getTips());
        dto.setAnonymous(post.isAnonymous());
        dto.setText(post.getText());
        return dto;
    }
}
