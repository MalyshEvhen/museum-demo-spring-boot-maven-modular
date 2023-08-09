package com.example.dao.museum.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Article is domain model of the web-page article business object.
 *
 * @author Evhen Malysh
 */
@Entity
@Table(name = "articles", indexes = {
        @Index(name = "idx_article_title", columnList = "title"),
        @Index(name = "idx_article_author_id", columnList = "author_id"),
        @Index(name = "idx_article_tags", columnList = "tags")
})
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Article {

    // @formatter:on

    /**
     * Unique identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Title string of article.
     */
    @Column(name = "title", nullable = false)
    @NotNull
    @NotBlank
    private String title;

    /**
     * Body of article.
     */
    @Column(name = "body", nullable = false)
    @NotNull
    @NotBlank
    private String body;

    /**
     * Collection of #tags related to the article.
     */
    @Setter(AccessLevel.PRIVATE)
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<WebArticleTag> tags = new LinkedHashSet<>();

    /**
     * Author of the article.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    /**
     * Timestamp of record creation.
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * Timestamp of record update.
     */
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * @param articleTitle  Title string of article
     * @param articleBody   Body of article
     * @param articleTags   Collection of #tags related to the article
     * @param articleAuthor Author of the article
     */
    public Article(final @NotNull @NotBlank String articleTitle,
                   final @NotNull @NotBlank String articleBody,
                   final Set<WebArticleTag> articleTags,
                   final Author articleAuthor) {
        this.title = articleTitle;
        this.body = articleBody;
        this.tags = articleTags;
        this.author = articleAuthor;
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Class<?> oEffectiveClass;
        if (o instanceof HibernateProxy hibernateProxy) {
            oEffectiveClass = hibernateProxy
                    .getHibernateLazyInitializer()
                    .getPersistentClass();
        } else {
            oEffectiveClass = o.getClass();
        }
        Class<?> thisEffectiveClass;
        if (this instanceof HibernateProxy hibernateProxy) {
            thisEffectiveClass = hibernateProxy
                    .getHibernateLazyInitializer()
                    .getPersistentClass();
        } else {
            thisEffectiveClass = this.getClass();
        }
        if (thisEffectiveClass != oEffectiveClass) {
            return false;
        }
        Article article = (Article) o;
        return getId() != null && Objects.equals(getId(), article.getId());
    }

    @Override
    public final int hashCode() {
        if (this instanceof HibernateProxy hibernateProxy) {
            return hibernateProxy
                    .getHibernateLazyInitializer()
                    .getPersistentClass()
                    .hashCode();
        }
        return getClass().hashCode();
    }

    /**
     * Enum representing the tags for web articles on a museum webpage.
     *
     * @author Evhen Malysh
     */
    public enum WebArticleTag {

        /**
         * Tag for articles related to art history and cultural heritage.
         */
        ART_HISTORY,

        /**
         * Tag for articles about current exhibitions and displays.
         */
        EXHIBITIONS,

        /**
         * Tag for articles discussing museum events and workshops.
         */
        EVENTS,

        /**
         * Tag for articles focusing on archaeological discoveries.
         */
        ARCHAEOLOGY,

        /**
         * Tag for articles featuring interviews with artists and curators.
         */
        INTERVIEWS,

        /**
         * Tag for articles exploring behind-the-scenes aspects of the museum.
         */
        BEHIND_THE_SCENES
    }
}