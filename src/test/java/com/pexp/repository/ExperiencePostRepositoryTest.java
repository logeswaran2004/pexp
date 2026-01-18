package com.pexp.repository;

import com.pexp.model.ExperiencePost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ExperiencePostRepositoryTest {

    @Autowired
    private ExperiencePostRepository repository;

    @Test
    void shouldSaveAndFetchPost() {
        ExperiencePost post = new ExperiencePost();
        post.setTitle("Test Post");
        post.setCategory("Tech");
        post.setUserId(1L);

        ExperiencePost saved = repository.save(post);

        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findById(saved.getId())).isPresent();
    }
}
