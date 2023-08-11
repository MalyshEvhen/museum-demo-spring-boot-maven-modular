package com.example.dto.museum.article;

import com.example.domain.museum.Article;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link Article}
 */
@Validated
public record ArticlePublishingForm(
        @NotNull @NotBlank String title,
        @NotNull @NotBlank String body,
        Set<Article.ArticleTag> tags,
        @NotNull @Positive Long authorId
) implements Serializable {
}