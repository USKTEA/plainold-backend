package com.usktea.plainold.repositories;

import com.usktea.plainold.models.answer.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByInquiryId(Long id);
}
