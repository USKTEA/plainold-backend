package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.CreateOrderService;
import com.usktea.plainold.dtos.OrderRequest;
import com.usktea.plainold.dtos.OrderRequestDto;
import com.usktea.plainold.dtos.OrderResultDto;
import com.usktea.plainold.exceptions.OrderNotCreated;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.user.UserName;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
            OrderRequest orderRequest = OrderRequest.of(userName, orderRequestDto);

            Order order = createOrderService.placeOrder(orderRequest);

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
