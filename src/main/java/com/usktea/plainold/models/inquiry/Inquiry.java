package com.usktea.plainold.models.inquiry;

import com.usktea.plainold.models.product.ProductId;
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
public class Inquiry {
    private static final String SECRET_TITLE = "비밀글입니다.";
    private static final String SECRET_CONTENT = "비밀글입니다.";

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private ProductId productId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private InquiryType type;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @Embedded
    private Querist querist;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Inquiry() {
    }

    public Inquiry(Long id,
                   ProductId productId,
                   Status status,
                   InquiryType type,
                   Title title,
                   Content content,
                   Querist querist,
                   LocalDateTime createdAt) {
        this.id = id;
        this.productId = productId;
        this.status = status;
        this.type = type;
        this.title = title;
        this.content = content;
        this.querist = querist;
        this.createdAt = createdAt;
    }

    public static Inquiry fake(ProductId productId) {
        return new Inquiry(
                1L,
                productId,
                Status.PENDING,
                InquiryType.PUBLIC,
                new Title("사이즈 문의"),
                new Content("어떻게 입으면 좋을까요"),
                Querist.fake(new Username("tjrxo1234@gmail.com")),
                LocalDateTime.now()
        );
    }

    public static Inquiry fake(InquiryType type, Querist querist) {
        return new Inquiry(
                1L,
                new ProductId(1L),
                Status.PENDING,
                type,
                new Title("사이즈 문의"),
                new Content("어떻게 입으면 좋을까요"),
                querist,
                LocalDateTime.now()
        );
    }

    public InquiryView toView(Username username, Role role) {
        if (!checkUserAuthority(username, role)) {
            return new InquiryView(
                    id,
                    productId.value(),
                    status.name(),
                    type.name(),
                    SECRET_TITLE,
                    SECRET_CONTENT,
                    querist.toDto(),
                    format(createdAt)
            );
        }

        return new InquiryView(
                id,
                productId.value(),
                status.name(),
                type.name(),
                title.value(),
                content.value(),
                querist.toDto(),
                format(createdAt)
        );
    }

    private boolean checkUserAuthority(Username username, Role role) {
        if (Objects.equals(this.type, InquiryType.PUBLIC)) {
            return true;
        }

        if (role.isAdmin()) {
            return true;
        }

        if (this.querist.isSameUser(username)) {
            return true;
        }

        return false;
    }

    private String format(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ZonedDateTime koreaZonedDateTime = time.atZone(ZoneId.of("Asia/Seoul"));

        return formatter.format(koreaZonedDateTime);
    }
}
