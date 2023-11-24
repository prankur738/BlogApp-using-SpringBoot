package io.mountblue.blogapp.repository;

import io.mountblue.blogapp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {
//
//    @Query("SELECT p FROM Post p LEFT JOIN p.tags t WHERE "+
//            "t.name LIKE CONCAT('%', :query, '%') OR "+
//            "p.title LIKE CONCAT('%', :query, '%') OR "+
//            "p.author LIKE CONCAT('%', :query, '%') OR "+
//            "p.content LIKE CONCAT('%', :query, '%') OR "+
//            "p.excerpt LIKE CONCAT('%', :query, '%')"
//    )
//    List<Post> getPostsBySearch(String query);
//
    @Query("SELECT p FROM Post p LEFT JOIN p.tags t WHERE "+
            "t.name LIKE CONCAT('%', :query, '%') OR "+
            "p.title LIKE CONCAT('%', :query, '%') OR "+
            "p.author LIKE CONCAT('%', :query, '%') OR "+
            "p.content LIKE CONCAT('%', :query, '%') OR "+
            "p.excerpt LIKE CONCAT('%', :query, '%')"
    )
    Page<Post> getPostsBySearch(@Param("query") String query, Pageable pageable);

    @Query("SELECT p from Post p LEFT JOIN p.tags t ON "+
            "COALESCE(t.name, '') IN :tags "+
            "WHERE (:authors IS NULL OR p.author IN :authors) AND "+
            "(:tags IS NULL OR t.name IN :tags)")
    Page<Post> findByAuthorInAndTags_NameIn(@Param("authors") List<String> authors,
                                            @Param("tags") List<String> tags,
                                            Pageable pageable);
//
//    @Query("SELECT p FROM Post p " +
//            "LEFT JOIN p.tags t ON COALESCE(t.name, '') IN :tagsFilter " +
//            "WHERE (:authorFilter IS NULL OR p.author IN :authorFilter) AND " +
//            " (:tagsFilter IS NULL OR t.name IN :tagsFilter) " +
////            "AND (:publishedDateFilter IS NULL OR p.publishedAt = :publishedDateFilter) " +
//            "ORDER BY " +
//            "CASE WHEN :sortDirection = 'asc' THEN p.publishedAt END ASC, " +
//            "CASE WHEN :sortDirection = 'desc' THEN p.publishedAt END DESC, " +
//            "CASE WHEN :sortDirection IS NULL THEN p.title END"
//    )
//    public Page<Post> findAllCustom(
//            @Param("authorFilter") String authorFilter,
////                                    @Param("publishedDateFilter") Date publishedDateFilter,
//            @Param("tagsFilter") List<String> tagsFilter,
//            @Param("sortDirection") String sortDirection,
//            Pageable pageable);








}

