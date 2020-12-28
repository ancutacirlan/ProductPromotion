package com.product.promotion.features.notice;

import com.product.promotion.features.producer.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    List<Notice> findAllByIsDeletedFalse();
    List<Notice> findAllByProducerIdAndIsDeletedFalse(Producer producerId);
}
