package com.usktea.plainold.models.answer;

import com.usktea.plainold.dtos.AnswerDto;
import com.usktea.plainold.exceptions.AnswerCannotBeEdited;
import com.usktea.plainold.exceptions.NotHaveDeleteAnswerAuthority;
import com.usktea.plainold.exceptions.NotHaveEditAnswerAuthority;
import com.usktea.plainold.models.user.Role;
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
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    private Long inquiryId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Embedded
    private Answerer answerer;

    @Embedded
    private Content content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Answer() {
    }

    public Answer(Long id,
                  Long inquiryId,
                  Status status,
                  Answerer answerer,
                  Content content,
                  LocalDateTime createdAt) {
        this.id = id;
        this.inquiryId = inquiryId;
        this.status = status;
        this.answerer = answerer;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Answer(Long id, Answerer answerer, Content content) {
        this.inquiryId = id;
        this.status = Status.ACTIVE;
        this.answerer = answerer;
        this.content = content;
    }

    public static Answer fake(Long inquiryId) {
        return new Answer(
                1L,
                inquiryId,
                Status.ACTIVE,
                Answerer.fake(new Username("admin@admin.com")),
                new Content("맞습니다"),
                LocalDateTime.now()
        );
    }

    public static Answer fake(Status status) {
        return new Answer(
                1L,
                1L,
                status,
                Answerer.fake(new Username("admin@admin.com")),
                new Content("맞습니다"),
                LocalDateTime.now()
        );
    }

    public void delete() {
        this.status = Status.DELETED;
    }

    public void delete(Role role) {
        if(!role.isAdmin()) {
            throw new NotHaveDeleteAnswerAuthority();
        }

        this.status = Status.DELETED;
    }

    public void edit(Role role, Content content) {
        if (!role.isAdmin()) {
            throw new NotHaveEditAnswerAuthority();
        }

        if (Objects.equals(this.status, Status.DELETED)) {
            throw new AnswerCannotBeEdited();
        }

        this.content = content;
    }

    public AnswerDto toDto() {
        return new AnswerDto(
                id,
                inquiryId,
                answerer.toDto(),
                content.getValue(),
                format(createdAt)
        );
    }

    private String format(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ZonedDateTime koreaZonedDateTime = time.atZone(ZoneId.of("Asia/Seoul"));

        return formatter.format(koreaZonedDateTime);
    }

    public Long id() {
        return id;
    }

}
