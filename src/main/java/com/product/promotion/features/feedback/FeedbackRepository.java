package com.product.promotion.features.feedback;

import com.product.promotion.features.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {

    List<Feedback> findAllByNoticeIdAndIsDeletedFalse(Notice notice);


}
