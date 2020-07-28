package com.memoryboost.domain.entity.notice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice,Long> , NoticeRepositoryCustom {
}
