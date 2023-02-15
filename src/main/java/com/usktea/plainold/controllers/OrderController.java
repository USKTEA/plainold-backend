package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.order.CancelOrderService;
import com.usktea.plainold.applications.order.EditOrderService;
import com.usktea.plainold.applications.order.CreateOrderService;
import com.usktea.plainold.applications.order.GetOrderCanWriteReviewService;
import com.usktea.plainold.applications.order.GetOrderDetailService;
import com.usktea.plainold.applications.order.GetUserOrderService;
import com.usktea.plainold.dtos.CancelOrderRequestDto;
import com.usktea.plainold.dtos.CancelOrderResultDto;
import com.usktea.plainold.dtos.EditOrderRequest;
import com.usktea.plainold.dtos.EditShippingInformationRequestDto;
import com.usktea.plainold.dtos.EditShippingInformationResultDto;
import com.usktea.plainold.dtos.GetOrderDetailResultDto;
import com.usktea.plainold.dtos.GetUserOrderResultDto;
import com.usktea.plainold.dtos.OrderDetailDto;
import com.usktea.plainold.dtos.OrderNumberDto;
import com.usktea.plainold.dtos.OrderRequest;
import com.usktea.plainold.dtos.OrderRequestDto;
import com.usktea.plainold.dtos.OrderResultDto;
import com.usktea.plainold.exceptions.CancelOrderFailed;
import com.usktea.plainold.exceptions.EditOrderFailed;
import com.usktea.plainold.exceptions.NotHaveAuthorityToGetOrders;
import com.usktea.plainold.exceptions.OrderCanWriteReviewNotFound;
import com.usktea.plainold.exceptions.OrderNotBelongToUser;
import com.usktea.plainold.exceptions.OrderNotCreated;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.order.OrderStatus;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final CreateOrderService createOrderService;
    private final GetOrderCanWriteReviewService getOrderCanWriteReviewService;
    private final GetUserOrderService getUserOrderService;
    private final GetOrderDetailService getOrderDetailService;
    private final EditOrderService editOrderService;
    private final CancelOrderService cancelOrderService;

    public OrderController(CreateOrderService createOrderService,
                           GetOrderCanWriteReviewService getOrderCanWriteReviewService,
                           GetUserOrderService getUserOrderService,
                           GetOrderDetailService getOrderDetailService,
                           EditOrderService editOrderService,
                           CancelOrderService cancelOrderService) {
        this.createOrderService = createOrderService;
        this.getOrderCanWriteReviewService = getOrderCanWriteReviewService;
        this.getUserOrderService = getUserOrderService;
        this.getOrderDetailService = getOrderDetailService;
        this.editOrderService = editOrderService;
        this.cancelOrderService = cancelOrderService;
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

    @GetMapping("me")
    public GetUserOrderResultDto userOrders(
            @RequestAttribute Username username,
            @RequestParam(required = false) String status,
            HttpServletResponse response
    ) {
        try {
            List<Order> orders = null;

            if (Objects.nonNull(status)) {
                OrderStatus orderStatus = OrderStatus.valueOf(status);

                orders = getUserOrderService.orders(username, orderStatus);
            }

            if (Objects.isNull(status)) {
                orders = getUserOrderService.orders(username);
            }

            if (orders.isEmpty()) {
                response.setStatus(204);
            }

            return new GetUserOrderResultDto(orders.stream()
                    .map(Order::toSummaryDto)
                    .collect(Collectors.toList()));
        } catch (Exception exception) {
            throw new NotHaveAuthorityToGetOrders();
        }
    }

    @GetMapping("{orderNumber}")
    public GetOrderDetailResultDto detail(
            @RequestAttribute Username username,
            @PathVariable String orderNumber
    ) {
        OrderDetailDto orderDetail = getOrderDetailService.getOrder(
                username, new OrderNumber(orderNumber));

        return new GetOrderDetailResultDto(orderDetail);
    }

    @PatchMapping("shippingInformation")
    public EditShippingInformationResultDto editShippingInformation(
            @RequestAttribute Username username,
            @Valid @RequestBody EditShippingInformationRequestDto editShippingInformationRequestDto
    ) {
        try {
            EditOrderRequest editOrderRequest = EditOrderRequest.of(editShippingInformationRequestDto);

            OrderNumber orderNumber = editOrderService.edit(username, editOrderRequest);

            return new EditShippingInformationResultDto(orderNumber.value());
        } catch (OrderNotBelongToUser orderNotBelongToUser) {
            throw orderNotBelongToUser;
        } catch (Exception exception) {
            throw new EditOrderFailed(exception.getMessage());
        }
    }

    @PatchMapping("orderStatus")
    public CancelOrderResultDto cancel(
            @RequestAttribute Username username,
            @Valid @RequestBody CancelOrderRequestDto cancelOrderRequestDto
    ) {
        try {
            OrderNumber orderNumber = new OrderNumber(cancelOrderRequestDto.getOrderNumber());

            OrderNumber canceled = cancelOrderService.cancel(username, orderNumber);

            return new CancelOrderResultDto(canceled.value());
        } catch (OrderNotBelongToUser orderNotBelongToUser) {
            throw orderNotBelongToUser;
        } catch (Exception exception) {
            throw new CancelOrderFailed(exception.getMessage());
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidRequestInformation() {
        return "잘못된 요청내용이 있습니다";
    }

    @ExceptionHandler(OrderNotCreated.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String orderNotCreated(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(NotHaveAuthorityToGetOrders.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String notHaveAuthorityToGetOrders(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(OrderCanWriteReviewNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String orderCanWriteReviewNotFound(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(EditOrderFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String editOrderFail(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CancelOrderFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String cancelOrderFail(Exception exception) {
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

    @ExceptionHandler(OrderNotBelongToUser.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String orderNotBelongToUser(Exception exception) {
        return exception.getMessage();
    }
}
