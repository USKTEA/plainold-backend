package com.usktea.plainold.applications.reply;

import com.usktea.plainold.models.inquiry.Inquiry;
import com.usktea.plainold.models.reply.Reply;
import com.usktea.plainold.models.reply.Status;
import com.usktea.plainold.repositories.ReplyRepository;
import com.usktea.plainold.specifications.InquirySpecification;
import com.usktea.plainold.specifications.ReplySpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class GetReplyService {
    private final ReplyRepository replyRepository;

    public GetReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public List<Reply> list(List<Long> reviewIds) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");

        Specification<Reply> specification = ReplySpecification
                .withReviewIds(reviewIds, sort)
                        .and(ReplySpecification.withStatus(Status.ACTIVE));

        List<Reply> replies = replyRepository.findAll(specification);

        return replies;
    }
}
