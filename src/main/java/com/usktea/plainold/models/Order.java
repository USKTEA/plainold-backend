package com.usktea.plainold.models;

import com.usktea.plainold.dtos.OrderResultDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "purchaseOrder")
public class Order {
    @Id
    @Embedded
    private OrderNumber orderNumber;

    @ElementCollection
    private List<OrderLine> orderLines = new ArrayList<>();

    @Embedded
    private UserName userName;

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

    public Order(OrderNumber orderNumber,
                 UserName userName,
                 List<OrderLine> orderLines,
                 Orderer orderer,
                 ShippingInformation shippingInformation,
                 Payment payment,
                 Money shippingFee,
                 Money cost) {
        this.orderNumber = orderNumber;
        this.userName = userName;
        this.orderLines = orderLines;
        this.orderer = orderer;
        this.shippingInformation = shippingInformation;
        this.payment = payment;
        this.status = OrderStatus.PAYMENT_WAITING;
        this.shippingFee = shippingFee;
        this.cost = cost;
    }

    public static Order fake(OrderNumber orderNumber) {
        Name name = new Name("김뚜루");

        return new Order(
                orderNumber,
                new UserName("tjrxo1234@gmail.com"),
                List.of(OrderLine.fake(new ProductId(1L))),
                Orderer.fake(name),
                ShippingInformation.fake(Receiver.fake(name)),
                Payment.fake(name),
                new Money(1L),
                new Money(1L)
        );
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

    public OrderResultDto toOrderResultDto() {
        return new OrderResultDto(orderNumber, cost, shippingInformation);
    }
}
