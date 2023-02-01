package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.order.CreateOrderService;
import com.usktea.plainold.applications.order.GetOrderCanWriteReviewService;
import com.usktea.plainold.dtos.OrderNumberDto;
import com.usktea.plainold.dtos.OrderRequest;
import com.usktea.plainold.dtos.OrderRequestDto;
import com.usktea.plainold.dtos.OrderResultDto;
import com.usktea.plainold.exceptions.OrderCanWriteReviewNotFound;
import com.usktea.plainold.exceptions.OrderNotCreated;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final CreateOrderService createOrderService;
    private final GetOrderCanWriteReviewService getOrderCanWriteReviewService;

    public OrderController(CreateOrderService createOrderService,
                           GetOrderCanWriteReviewService getOrderCanWriteReviewService) {
        this.createOrderService = createOrderService;
        this.getOrderCanWriteReviewService = getOrderCanWriteReviewService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResultDto create(
            @RequestAttribute Username username,
            @Valid @RequestBody OrderRequestDto orderRequestDto
    ) {
        try {
            OrderRequest orderRequest = OrderRequest.of(username, orderRequestDto);

            Order order = createOrderService.placeOrder(orderRequest);

            return order.toOrderResultDto();
        } catch (Exception exception) {
            throw new OrderNotCreated(exception.getMessage());
        }
    }

    @GetMapping
    public OrderNumberDto orderCanWriteReview(
            @RequestAttribute Username username,
            @RequestParam Long productId
    ) {
        Order order = getOrderCanWriteReviewService.order(username, new ProductId(productId));

        return new OrderNumberDto(order.orderNumber());
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

    @ExceptionHandler(OrderCanWriteReviewNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String orderCanWriteReviewNotFound(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(ProductNotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String productNotFound(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(UserNotExists.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userNotFound(Exception exception) {
        return exception.getMessage();
    }
}
