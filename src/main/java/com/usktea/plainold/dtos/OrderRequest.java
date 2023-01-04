package com.usktea.plainold.dtos;

import com.usktea.plainold.models.common.Money;
import com.usktea.plainold.models.order.OrderLine;
import com.usktea.plainold.models.order.Orderer;
import com.usktea.plainold.models.order.Payment;
import com.usktea.plainold.models.order.ShippingInformation;
import com.usktea.plainold.models.user.UserName;

import java.util.List;
import java.util.stream.Collectors;

public class OrderRequest {
    private UserName userName;
    private List<OrderLine> orderLines;
    private Orderer orderer;
    private ShippingInformation shippingInformation;
    private Payment payment;
    private Money shippingFee;
    private Money cost;

    public OrderRequest(UserName userName, OrderRequestDto orderRequestDto) {
        setUserName(userName);
        setOrderLines(orderRequestDto.getOrderItems());
        setOrderer(orderRequestDto.getOrderer());
        setShippingInformation(orderRequestDto.getShippingInformation());
        setPayment(orderRequestDto.getPayment());
        setShippingFee(orderRequestDto.getShippingFee());
        setCost(orderRequestDto.getCost());
    }

    public static OrderRequest of(UserName userName, OrderRequestDto orderRequestDto) {
        return new OrderRequest(userName, orderRequestDto);
    }

    public static OrderRequest fake() {
        return new OrderRequest(
                new UserName("tjrxo1234@gmail.com"),
                OrderRequestDto.fake()
        );
    }

    public UserName getUserName() {
        return userName;
    }

    private void setUserName(UserName userName) {
        this.userName = userName;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    private void setOrderLines(List<OrderItemDto> orderItems) {
        List<OrderLine> orderLines = orderItems
                .stream()
                .map((orderItemDto -> OrderLine.of(orderItemDto)))
                .collect(Collectors.toList());

        this.orderLines = orderLines;
    }

    public Orderer getOrderer() {
        return orderer;
    }

    private void setOrderer(OrdererDto ordererDto) {
        this.orderer = Orderer.of(ordererDto);
    }

    public ShippingInformation getShippingInformation() {
        return shippingInformation;
    }

    private void setShippingInformation(ShippingInformationDto shippingInformationDto) {
        this.shippingInformation = ShippingInformation.of(shippingInformationDto);
    }

    public Payment getPayment() {
        return payment;
    }

    private void setPayment(PaymentDto paymentDto) {
        this.payment = Payment.of(paymentDto);
    }

    public Money getShippingFee() {
        return shippingFee;
    }

    private void setShippingFee(ShippingFeeDto shippingFeeDto) {
        this.shippingFee = new Money(shippingFeeDto.getAmount());
    }

    public Money getCost() {
        return cost;
    }

    private void setCost(CostDto costDto) {
        this.cost = new Money(costDto.getAmount());
    }
}
