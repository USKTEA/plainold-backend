package com.usktea.plainold.models.cancelRequest;

import com.usktea.plainold.dtos.CancelRequestDto;
import com.usktea.plainold.exceptions.CancelRequestNotBelongToUser;
import com.usktea.plainold.models.order.OrderNumber;
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
public class CancelRequest {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Username username;

    private OrderNumber orderNumber;

    @Embedded
    private Content content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public CancelRequest() {
    }

    public CancelRequest(Long id, Content content) {
        this.id = id;
        this.content = content;
    }

    public CancelRequest(Username username, OrderNumber orderNumber, Content content) {
        this.username = username;
        this.orderNumber = orderNumber;
        this.content = content;
    }

    public CancelRequest(Long id,
                         Username username,
                         OrderNumber orderNumber,
                         Content content,
                         LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.orderNumber = orderNumber;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static CancelRequest fake(Long id) {
        return new CancelRequest(
                id,
                new Content("이래서 취소합니다")
        );
    }

    public static CancelRequest fake(Username username) {
        return new CancelRequest(
                1L,
                username,
                new OrderNumber("tjrxo1234-11111111"),
                new Content("이래서 취소합니다"),
                LocalDateTime.now()
        );
    }

    public CancelRequestDto toDto() {
        return new CancelRequestDto(
                id,
                orderNumber.value(),
                content.getValue(),
                format(createdAt)
        );
    }

    public void verifyUser(Username username) {
        if (!Objects.equals(this.username, username)) {
            throw new CancelRequestNotBelongToUser();
        }
    }
    
    private String format(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ZonedDateTime koreaZonedDateTime = time.atZone(ZoneId.of("Asia/Seoul"));

        return formatter.format(koreaZonedDateTime);
    }

    public Long id() {
        return id;
    }

    public OrderNumber orderNumber() {
        return orderNumber;
    }
}
