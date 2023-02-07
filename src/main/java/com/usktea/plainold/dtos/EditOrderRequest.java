package com.usktea.plainold.dtos;

import com.usktea.plainold.models.common.Name;
import com.usktea.plainold.models.order.Address;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.order.Receiver;
import com.usktea.plainold.models.order.ZipCode;

public class EditOrderRequest {
    private OrderNumber orderNumber;
    private Receiver receiver;
    private Address address;
    private String message;

    public EditOrderRequest(OrderNumber orderNumber,
                            Receiver receiver,
                            Address address,
                            String message) {
        this.orderNumber = orderNumber;
        this.receiver = receiver;
        this.address = address;
        this.message = message;
    }

    public static EditOrderRequest of(EditShippingInformationRequestDto editShippingInformationRequestDto) {
        return new EditOrderRequest(
                new OrderNumber(editShippingInformationRequestDto.getOrderNumber()),
                Receiver.of(editShippingInformationRequestDto.getReceiver()),
                Address.of(editShippingInformationRequestDto.getAddress()),
                editShippingInformationRequestDto.getMessage()
        );
    }

    public static EditOrderRequest fake(OrderNumber orderNumber) {
        return new EditOrderRequest(
                orderNumber,
                Receiver.fake(new Name("김뚜루")),
                Address.fake(new ZipCode("111111")),
                "배송메시지"
        );
    }

    public OrderNumber orderNumber() {
        return orderNumber;
    }

    public Receiver receiver() {
        return receiver;
    }

    public Address address() {
        return address;
    }

    public String message() {
        return message;
    }
}
