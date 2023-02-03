package com.usktea.plainold.repositories;

import com.usktea.plainold.models.answer.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long>, JpaSpecificationExecutor {
    List<Answer> findAllByInquiryId(Long id);
}
