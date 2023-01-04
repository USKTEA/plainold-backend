package com.usktea.plainold.applications;

import com.usktea.plainold.dtos.OrderRequest;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.exceptions.ProductSoldOut;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.product.ProductStatus;
import com.usktea.plainold.repositories.OrderRepository;
import com.usktea.plainold.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
class CreateOrderServiceTest {
    private CreateOrderService createOrderService;
    private OrderNumberService orderNumberService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        orderNumberService = new OrderNumberService();

        orderRepository = mock(OrderRepository.class);
        productRepository = mock(ProductRepository.class);

        createOrderService = new CreateOrderService(orderNumberService, orderRepository, productRepository);
    }

    @Test
    void whenCreateOrderSuccess() {
        ProductId productId = new ProductId(1L);

        given(productRepository.findById(any()))
                .willReturn(Optional.of(Product.fake(productId)));

        OrderRequest orderRequest = OrderRequest.fake();

        Order order = createOrderService.placeOrder(orderRequest);

        given(orderRepository.save(any())).willReturn(order);

        verify(orderRepository).save(any());
    }

    @Test
    void whenProductIsNotExists() {
        given(productRepository.findById(any()))
                .willReturn(Optional.empty());

        OrderRequest orderRequest = OrderRequest.fake();

        assertThrows(ProductNotFound.class, () -> createOrderService.placeOrder(orderRequest));
    }

    @Test
    void whenProductIsSoldOut() {
        given(productRepository.findById(any()))
                .willReturn(Optional.of(Product.fake(ProductStatus.SOLD_OUT)));

        OrderRequest orderRequest = OrderRequest.fake();

        assertThrows(ProductSoldOut.class, () -> createOrderService.placeOrder(orderRequest));
    }
}
