package com.usktea.plainold.models.reply;

import com.usktea.plainold.dtos.ReplyDto;
import com.usktea.plainold.exceptions.ReplierNotMatch;
import com.usktea.plainold.models.common.Comment;
import com.usktea.plainold.models.user.Username;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Reply {
    @Id
    @GeneratedValue
    private Long id;

    private Long reviewId;

    @Embedded
    private Parent parent;

    @Embedded
    private Comment comment;

    @Embedded
    private Replier replier;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Reply() {
    }

    public Reply(Long id,
                 Long reviewId,
                 Parent parent,
                 Comment comment,
                 Replier replier,
                 LocalDateTime createdAt) {
        this.id = id;
        this.reviewId = reviewId;
        this.parent = parent;
        this.comment = comment;
        this.replier = replier;
        this.createdAt = createdAt;
    }

    public Reply(Long reviewId,
                 Parent parent,
                 Comment comment,
                 Replier replier) {
        this.reviewId = reviewId;
        this.parent = parent;
        this.comment = comment;
        this.replier = replier;
        this.status = Status.ACTIVE;
    }

    public static Reply fake(Long id) {
        return new Reply(
                id,
                1L,
                new Parent(1L),
                new Comment("좋아보이네요"),
                Replier.fake("tjrxo1234@gmail.com"),
                LocalDateTime.now()
        );
    }

    public static Reply fake(Username username) {
        return new Reply(
                1L,
                1L,
                new Parent(1L),
                new Comment("좋아보이네요"),
                Replier.fake(username.value()),
                LocalDateTime.now()
        );
    }

    public ReplyDto toDto() {
        if (Objects.isNull(parent)) {
            return new ReplyDto(
                    id,
                    reviewId,
                    null,
                    comment.getValue(),
                    replier.toDto(),
                    format(createdAt)
            );
        }

        return new ReplyDto(
                id,
                reviewId,
                parent.getValue(),
                comment.getValue(),
                replier.toDto(),
                format(createdAt)
        );
    }

    private String format(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ZonedDateTime koreaZonedDateTime = time.atZone(ZoneId.of("Asia/Seoul"));

        return formatter.format(koreaZonedDateTime);
    }

    public void edit(Username username, Comment comment) {
        checkReplierIsSame(username);

        this.comment = comment;
    }

    public void delete(Username username) {
        checkReplierIsSame(username);

        this.status = Status.DELETED;
    }

    public void delete() {
        this.status = Status.DELETED;
    }

    public boolean isFirstReply() {
        if (Objects.isNull(parent)) {
            return true;
        }

        return false;
    }

    private void checkReplierIsSame(Username username) {
        if (!Objects.equals(replier.getUsername(), username)) {
            throw new ReplierNotMatch();
        }
    }

    public Long id() {
        return id;
    }

    public Comment comment() {
        return comment;
    }

    public Status status() {
        return status;
    }
}
