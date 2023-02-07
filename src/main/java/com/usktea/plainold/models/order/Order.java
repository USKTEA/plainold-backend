package com.usktea.plainold.models.order;

import com.usktea.plainold.dtos.EditOrderRequest;
import com.usktea.plainold.dtos.OrderDetailDto;
import com.usktea.plainold.dtos.OrderLineDto;
import com.usktea.plainold.dtos.OrderRequest;
import com.usktea.plainold.dtos.OrderResultDto;
import com.usktea.plainold.dtos.OrderSummaryDto;
import com.usktea.plainold.exceptions.EmptyOrderLines;
import com.usktea.plainold.exceptions.OrderCannotBeEdited;
import com.usktea.plainold.exceptions.OrderNotBelongToUser;
import com.usktea.plainold.models.common.Money;
import com.usktea.plainold.models.user.Username;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "purchaseOrder")
public class Order {
    @Id
    @Embedded
    private OrderNumber orderNumber;

    @ElementCollection
    private List<OrderLine> orderLines = new ArrayList<>();

    @Embedded
    private Username username;

    @Embedded
    private Orderer orderer;

    @Embedded
    private ShippingInformation shippingInformation;

    @Embedded
    private Payment payment;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "shippingFee"))
    private Money shippingFee;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "cost"))
    private Money cost;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Order() {
    }

    public Order(OrderNumber orderNumber, OrderRequest orderRequest) {
        this.orderNumber = orderNumber;
        setOrderLines(orderRequest.getOrderLines());
        this.username = orderRequest.getUsername();
        this.orderer = orderRequest.getOrderer();
        this.shippingInformation = orderRequest.getShippingInformation();
        this.payment = orderRequest.getPayment();
        this.status = OrderStatus.PAYMENT_WAITING;
        this.shippingFee = orderRequest.getShippingFee();
        this.cost = orderRequest.getCost();
    }

    public Order(OrderNumber orderNumber,
                 OrderRequest orderRequest,
                 LocalDateTime createdAt) {
        this.orderNumber = orderNumber;
        setOrderLines(orderRequest.getOrderLines());
        this.username = orderRequest.getUsername();
        this.orderer = orderRequest.getOrderer();
        this.shippingInformation = orderRequest.getShippingInformation();
        this.payment = orderRequest.getPayment();
        this.status = OrderStatus.PAYMENT_WAITING;
        this.shippingFee = orderRequest.getShippingFee();
        this.cost = orderRequest.getCost();
        this.createdAt = createdAt;
    }

    public Order(OrderNumber orderNumber,
                 OrderRequest orderRequest,
                 OrderStatus status,
                 LocalDateTime createdAt) {
        this.orderNumber = orderNumber;
        setOrderLines(orderRequest.getOrderLines());
        this.username = orderRequest.getUsername();
        this.orderer = orderRequest.getOrderer();
        this.shippingInformation = orderRequest.getShippingInformation();
        this.payment = orderRequest.getPayment();
        this.status = status;
        this.shippingFee = orderRequest.getShippingFee();
        this.cost = orderRequest.getCost();
        this.createdAt = createdAt;
    }

    public static Order fake(OrderNumber orderNumber) {
        OrderRequest orderRequest = OrderRequest.fake();

        return new Order(orderNumber, orderRequest, LocalDateTime.now());
    }

    public static Order fake(OrderNumber orderNumber, OrderStatus status) {
        OrderRequest orderRequest = OrderRequest.fake();

        return new Order(orderNumber, orderRequest, status, LocalDateTime.now());
    }

    private void setOrderLines(List<OrderLine> orderLines) {
        if (orderLines.size() == 0) {
            throw new EmptyOrderLines();
        }

        this.orderLines = orderLines;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Order otherOrder = (Order) object;

        return Objects.equals(orderNumber, otherOrder.orderNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber);
    }

    public void edit(Username username, EditOrderRequest editOrderRequest) {
        authenticate(username);
        ensureEditable();

        this.shippingInformation = new ShippingInformation(
                editOrderRequest.receiver(),
                editOrderRequest.address(),
                editOrderRequest.message()
        );
    }

    private void ensureEditable() {
        if (!Objects.equals(this.status, OrderStatus.PAYMENT_WAITING)
                && !Objects.equals(this.status, OrderStatus.PREPARING)) {
            throw new OrderCannotBeEdited();
        }
    }

    public void authenticate(Username username) {
        if (!Objects.equals(this.username, username)) {
            throw new OrderNotBelongToUser();
        }
    }

    public boolean checkHasSameOrder(List<OrderNumber> orderNumbers) {
        if (orderNumbers.contains(this.orderNumber)) {
            return false;
        }

        return true;
    }

    private String format(LocalDateTime createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ZonedDateTime koreaZonedDateTime = createdAt.atZone(ZoneId.of("Asia/Seoul"));

        return formatter.format(koreaZonedDateTime);
    }

    public OrderDetailDto toDetailDto() {
        return new OrderDetailDto(
                orderNumber,
                orderLines,
                orderer,
                shippingInformation,
                status,
                shippingFee,
                cost,
                payment.getMethod(),
                format(createdAt)
        );
    }

    public OrderSummaryDto toSummaryDto() {
        return new OrderSummaryDto(
                orderNumber.value(),
                orderLinesToDto(),
                status.value(),
                format(createdAt)
        );
    }

    public OrderResultDto toOrderResultDto() {
        return new OrderResultDto(orderNumber, cost, shippingInformation);
    }

    private List<OrderLineDto> orderLinesToDto() {
        return orderLines.stream()
                .map(OrderLine::toDto)
                .collect(Collectors.toList());
    }

    public OrderNumber orderNumber() {
        return orderNumber;
    }
}
