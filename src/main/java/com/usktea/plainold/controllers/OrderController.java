package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.CreateOrderService;
import com.usktea.plainold.dtos.OrderRequestDto;
import com.usktea.plainold.dtos.OrderResultDto;
import com.usktea.plainold.exceptions.OrderNotCreated;
import com.usktea.plainold.models.Money;
import com.usktea.plainold.models.Order;
import com.usktea.plainold.models.OrderLine;
import com.usktea.plainold.models.Orderer;
import com.usktea.plainold.models.Payment;
import com.usktea.plainold.models.ShippingInformation;
import com.usktea.plainold.models.UserName;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final CreateOrderService createOrderService;

    public OrderController(CreateOrderService createOrderService) {
        this.createOrderService = createOrderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResultDto create(
            @Valid @RequestBody OrderRequestDto orderRequestDto
    ) {
        //TODO 인터셉터에서 가져와야함
        UserName userName = new UserName("tjrxo1234@gmail.com");

        try {
            List<OrderLine> orderLines = orderRequestDto
                    .getOrderItems()
                    .stream()
                    .map((orderItemDto -> OrderLine.of(orderItemDto)))
                    .collect(Collectors.toList());

            Orderer orderer = Orderer.of(orderRequestDto.getOrderer());

            ShippingInformation shippingInformation
                    = ShippingInformation.of(orderRequestDto.getShippingInformation());

            Payment payment = Payment.of(orderRequestDto.getPayment());

            Money shippingFee = new Money(orderRequestDto.getShippingFee().getAmount());

            Money cost = new Money(orderRequestDto.getCost().getAmount());

            Order order = createOrderService.create(userName, orderLines, orderer,
                    shippingInformation, payment, shippingFee, cost);

            return order.toOrderResultDto();
        } catch (Exception exception) {
            throw new OrderNotCreated(exception.getMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String missingRequestInformation() {
        return "누락된 주문내용이 있습니다";
    }

    @ExceptionHandler(OrderNotCreated.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String orderNotCreated(Exception exception) {
        return exception.getMessage();
    }
}

// TODO OrderRequestDto에 있는 모든 VO 유효성 검증해야함