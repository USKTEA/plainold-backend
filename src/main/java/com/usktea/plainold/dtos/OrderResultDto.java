package com.usktea.plainold.dtos;

import com.usktea.plainold.models.common.Money;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.order.PaymentMethod;
import com.usktea.plainold.models.order.ShippingInformation;

public class OrderResultDto {
    private String orderNumber;
    private Long cost;
    private ReceiverDto receiver;
    private AddressDto shippingAddress;
    private String paymentMethod;

    public OrderResultDto() {
    }

    public OrderResultDto(OrderNumber orderNumber,
                          Money cost,
                          ShippingInformation shippingInformation,
                          PaymentMethod paymentMethod
    ) {
        this.orderNumber = orderNumber.value();
        this.cost = cost.getAmount();
        this.receiver = shippingInformation.getReceiverDto();
        this.shippingAddress = shippingInformation.getAddressDto();
        this.paymentMethod = paymentMethod.name();
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Long getCost() {
        return cost;
    }

    public ReceiverDto getReceiver() {
        return receiver;
    }

    public AddressDto getShippingAddress() {
        return shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}
