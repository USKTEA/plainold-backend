package com.usktea.plainold.applications;

import com.usktea.plainold.dtos.OptionDto;
import com.usktea.plainold.dtos.OrderItemDto;
import com.usktea.plainold.dtos.OrderRequest;
import com.usktea.plainold.exceptions.InvalidProductOption;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.exceptions.ProductSoldOut;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.option.Option;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.product.ProductStatus;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.repositories.OptionRepository;
import com.usktea.plainold.repositories.OrderRepository;
import com.usktea.plainold.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
class CreateOrderServiceTest {
    private CreateOrderService createOrderService;
    private OrderNumberService orderNumberService;

    @MockBean
    private GetUserService getUserService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private OptionRepository optionRepository;

    @BeforeEach
    void setUp() {
        orderNumberService = new OrderNumberService();
        getUserService = mock(GetUserService.class);

        orderRepository = mock(OrderRepository.class);
        productRepository = mock(ProductRepository.class);
        optionRepository = mock(OptionRepository.class);

        createOrderService = new CreateOrderService(
                orderNumberService, getUserService,
                orderRepository, productRepository, optionRepository
        );
    }

    @Test
    void whenCreateOrderSuccess() {
        ProductId productId = new ProductId(1L);
        Username username = new Username("tjrxo1234@gmail.com");

        given(productRepository.findById(any()))
                .willReturn(Optional.of(Product.fake(productId)));

        OrderRequest orderRequest = OrderRequest.fake();

        Order order = createOrderService.placeOrder(orderRequest);

        given(orderRepository.save(any()))
                .willReturn(order);

        verify(orderRepository).save(any());
    }

    @Test
    void whenUserIsNotExists() {
        ProductId productId = new ProductId(1L);
        Username username = new Username("notExists@gmail.com");

        doThrow(UserNotExists.class).when(getUserService).find(username);

        OrderRequest orderRequest = OrderRequest.fake(username);

        assertThrows(UserNotExists.class,
                () -> createOrderService.placeOrder(orderRequest));
    }

    @Test
    void whenProductIsNotExists() {
        given(productRepository.findById(any()))
                .willReturn(Optional.empty());

        OrderRequest orderRequest = OrderRequest.fake();

        assertThrows(ProductNotFound.class,
                () -> createOrderService.placeOrder(orderRequest));
    }

    @Test
    void whenProductIsSoldOut() {
        given(productRepository.findById(any()))
                .willReturn(Optional.of(Product.fake(ProductStatus.SOLD_OUT)));

        OrderRequest orderRequest = OrderRequest.fake();

        assertThrows(ProductSoldOut.class,
                () -> createOrderService.placeOrder(orderRequest));
    }

    @Test
    void whenOptionIsNotValid() {
        ProductId productId = new ProductId(1L);

        OptionDto optionDto = new OptionDto("XL", "InvalidColor");

        OrderItemDto orderItemDto = OrderItemDto.fake(optionDto);

        OrderRequest orderRequest = OrderRequest.fake(List.of(orderItemDto));

        given(productRepository.findById(any()))
                .willReturn(Optional.of(Product.fake(productId)));

        given(optionRepository.findAllByProductIdIn(List.of(productId)))
                .willReturn(List.of(Option.fake(productId)));

        assertThrows(InvalidProductOption.class,
                () -> createOrderService.placeOrder(orderRequest));
    }
}
