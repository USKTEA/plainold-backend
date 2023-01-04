package com.usktea.plainold.models.order;

import com.usktea.plainold.dtos.OrderRequest;
import com.usktea.plainold.dtos.OrderResultDto;
import com.usktea.plainold.exceptions.EmptyOrderLines;
import com.usktea.plainold.models.user.UserName;
import com.usktea.plainold.models.common.Money;
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

    public Order(OrderNumber orderNumber, OrderRequest orderRequest) {
        setOrderLines(orderRequest.getOrderLines());
        this.orderNumber = orderNumber;
        this.userName = orderRequest.getUserName();
        this.orderer = orderRequest.getOrderer();
        this.shippingInformation = orderRequest.getShippingInformation();
        this.payment = orderRequest.getPayment();
        this.status = OrderStatus.PAYMENT_WAITING;
        this.shippingFee = orderRequest.getShippingFee();
        this.cost = orderRequest.getCost();
    }

    private void setOrderLines(List<OrderLine> orderLines) {
        if (orderLines.size() == 0) {
            throw new EmptyOrderLines();
        }

        this.orderLines = orderLines;
    }

    public static Order fake(OrderNumber orderNumber) {
        OrderRequest orderRequest = OrderRequest.fake();

        return new Order(orderNumber, orderRequest);
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
