package com.memoryboost.domain.entity.post;


import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> , PostRepositoryCustom {
}
