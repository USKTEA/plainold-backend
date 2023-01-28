package com.usktea.plainold.dtos;

import com.usktea.plainold.models.common.Comment;
import com.usktea.plainold.models.reply.Parent;
import org.springframework.security.core.parameters.P;

public class CreateReplyRequest {
    private Long reviewId;
    private Parent parent;
    private Comment comment;

    public CreateReplyRequest(Long reviewId, Parent parent, Comment comment) {
        this.reviewId = reviewId;
        this.parent = parent;
        this.comment = comment;
    }

    public static CreateReplyRequest of(CreateReplyRequestDto createReplyRequestDto) {
        return new CreateReplyRequest(
                createReplyRequestDto.getReviewId(),
                new Parent(createReplyRequestDto.getParent()),
                new Comment(createReplyRequestDto.getComment())
        );
    }

    public static CreateReplyRequest fake(Long reviewId) {
        return new CreateReplyRequest(
                reviewId,
                new Parent(1L),
                new Comment("좋아보이는 상품")
        );
    }

    public Long reviewId() {
        return reviewId;
    }

    public Parent parent() {
        return parent;
    }

    public Comment comment() {
        return comment;
    }
}
