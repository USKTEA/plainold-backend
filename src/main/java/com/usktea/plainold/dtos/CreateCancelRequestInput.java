package com.usktea.plainold.dtos;

import com.usktea.plainold.models.cancelRequest.Content;
import com.usktea.plainold.models.order.OrderNumber;

public class CreateCancelRequestInput {
    private OrderNumber orderNumber;
    private Content content;

    public CreateCancelRequestInput(OrderNumber orderNumber, Content content) {
        this.orderNumber = orderNumber;
        this.content = content;
    }

    public static CreateCancelRequestInput of(CreateCancelRequestInputDto createCancelRequestInputDto) {
        return new CreateCancelRequestInput(
                new OrderNumber(createCancelRequestInputDto.getOrderNumber()),
                new Content(createCancelRequestInputDto.getContent())
        );
    }

    public static CreateCancelRequestInput fake(OrderNumber orderNumber) {
        return new CreateCancelRequestInput(
                orderNumber,
                new Content("이래서 취소합니다")
        );
    }

    public OrderNumber orderNumber() {
        return orderNumber;
    }

    public Content content() {
        return content;
    }
}
