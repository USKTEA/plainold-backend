package com.usktea.plainold.models.like;

import com.usktea.plainold.dtos.LikeDto;
import com.usktea.plainold.exceptions.UsernameNotMatch;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Likes {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Username username;

    @Embedded
    private ProductId productId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Likes() {
    }

    public Likes(Long id,
                 Username username,
                 ProductId productId,
                 LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.productId = productId;
        this.createdAt = createdAt;
    }

    public Likes(Username username, ProductId productId) {
        this.username = username;
        this.productId = productId;
    }

    public static Likes fake(Username username) {
        return new Likes(
                1L,
                username,
                new ProductId(1L),
                LocalDateTime.now()
        );
    }

    public void authenticate(Username username) {
        if (!Objects.equals(this.username, username)) {
            throw new UsernameNotMatch();
        }
    }

    public LikeDto toDto() {
        return new LikeDto(
                id,
                username.value(),
                productId.getProductId(),
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
