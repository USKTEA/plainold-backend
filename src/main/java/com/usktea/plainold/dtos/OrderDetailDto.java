package com.usktea.plainold.dtos;

import com.usktea.plainold.models.common.Money;
import com.usktea.plainold.models.common.Name;
import com.usktea.plainold.models.order.OrderLine;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.order.OrderStatus;
import com.usktea.plainold.models.order.Orderer;
import com.usktea.plainold.models.order.PaymentMethod;
import com.usktea.plainold.models.order.Receiver;
import com.usktea.plainold.models.order.ShippingInformation;
import com.usktea.plainold.models.product.ProductId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDetailDto {
    private String orderNumber;
    private List<OrderLineDto> orderLines;
    private OrdererDto orderer;
    private ShippingInformationDto shippingInformation;
    private String status;
    private String payment;
    private Long cost;
    private Long shippingFee;
    private String createdAt;

    public OrderDetailDto(OrderNumber orderNumber,
                          List<OrderLine> orderLines,
                          Orderer orderer,
                          ShippingInformation shippingInformation,
                          OrderStatus status,
                          Money shippingFee,
                          Money cost,
                          PaymentMethod payment,
                          String createdAt) {
        this.orderNumber = orderNumber.value();
        setOrderLines(orderLines);
        this.orderer = orderer.toDto();
        this.shippingInformation = shippingInformation.toDto();
        this.status = status.value();
        this.shippingFee = shippingFee.getAmount();
        this.cost = cost.getAmount();
        this.payment = payment.name();
        this.createdAt = createdAt;
    }

    public static OrderDetailDto fake(OrderNumber orderNumber) {
        Name name = new Name("김뚜루");

        return new OrderDetailDto(
                orderNumber,
                List.of(OrderLine.fake(new ProductId(1L))),
                Orderer.fake(name),
                ShippingInformation.fake(Receiver.fake(name)),
                OrderStatus.PAYMENT_WAITING,
                new Money(1L),
                new Money(1L),
                PaymentMethod.CASH,
                LocalDateTime.now().toString()
        );
    }

    private void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines.stream()
                .map(OrderLine::toDto)
                .collect(Collectors.toList());
    }

    public String orderNumber() {
        return orderNumber;
    }

    public List<OrderLineDto> orderLines() {
        return orderLines;
    }

    public OrdererDto orderer() {
        return orderer;
    }

    public ShippingInformationDto shippingInformation() {
        return shippingInformation;
    }

    public String status() {
        return status;
    }

    public Long shippingFee() {
        return shippingFee;
    }

    public String payment() {
        return payment;
    }

    public Long cost() {
        return cost;
    }

    public String createdAt() {
        return createdAt;
    }
}
