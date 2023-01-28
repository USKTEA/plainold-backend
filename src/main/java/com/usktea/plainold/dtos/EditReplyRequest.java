package com.usktea.plainold.dtos;

import com.usktea.plainold.models.common.Comment;

public class EditReplyRequest {
    private Long replyId;
    private Long reviewId;
    private Comment comment;

    public EditReplyRequest(Long replyId, Long reviewId, Comment comment) {
        this.replyId = replyId;
        this.reviewId = reviewId;
        this.comment = comment;
    }

    public static EditReplyRequest of(EditReplyRequestDto editReplyRequestDto) {
        return new EditReplyRequest(
                editReplyRequestDto.getId(),
                editReplyRequestDto.getReviewId(),
                new Comment(editReplyRequestDto.getComment()
                ));
    }

    public static EditReplyRequest fake(Long replyId) {
        return new EditReplyRequest(
                replyId,
                1L,
                new Comment("좋아보이네요")
        );
    }

    public static EditReplyRequest fake(Long replyId, Long reviewId) {
        return new EditReplyRequest(
                replyId,
                reviewId,
                new Comment("좋아보이네요")
        );
    }

    public Long reviewId() {
        return reviewId;
    }

    public Long replyId() {
        return replyId;
    }

    public Comment comment() {
        return comment;
    }
}
