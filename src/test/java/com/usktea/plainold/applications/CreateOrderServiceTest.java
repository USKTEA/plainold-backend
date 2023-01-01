package com.usktea.plainold.applications;

import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.exceptions.ProductSoldOut;
import com.usktea.plainold.models.Money;
import com.usktea.plainold.models.Name;
import com.usktea.plainold.models.Order;
import com.usktea.plainold.models.OrderLine;
import com.usktea.plainold.models.Orderer;
import com.usktea.plainold.models.Payment;
import com.usktea.plainold.models.Product;
import com.usktea.plainold.models.ProductId;
import com.usktea.plainold.models.ProductStatus;
import com.usktea.plainold.models.Receiver;
import com.usktea.plainold.models.ShippingInformation;
import com.usktea.plainold.models.UserName;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
class CreateOrderServiceTest {
    private ProductId productId;
    private UserName userName;
    private List<OrderLine> orderLines;
    private Orderer orderer;
    private ShippingInformation shippingInformation;
    private Payment payment;
    private Money shippingFee;
    private Money cost;

    private CreateOrderService createOrderService;
    private OrderNumberService orderNumberService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productId = new ProductId(1L);
        Name name = new Name("김뚜루");

        userName = new UserName("tjrxo1234@gmail.com");

        orderLines = List.of(OrderLine.fake(productId));
        orderer = Orderer.fake(name);
        Receiver receiver = Receiver.fake(name);
        shippingInformation = ShippingInformation.fake(receiver);
        payment = Payment.fake(name);
        shippingFee = new Money(1L);
        cost = new Money(1L);

        orderNumberService = new OrderNumberService();
        orderRepository = mock(OrderRepository.class);
        productRepository = mock(ProductRepository.class);
        createOrderService = new CreateOrderService(orderNumberService, orderRepository, productRepository);
    }

    @Test
    void whenCreateOrderSuccess() {
        given(productRepository.findById(any()))
                .willReturn(Optional.of(Product.fake(productId.value())));

        Order order = createOrderService.create(userName, orderLines, orderer,
                shippingInformation, payment, shippingFee, cost);

        given(orderRepository.save(any())).willReturn(order);

        verify(orderRepository).save(any());
    }

    @Test
    void whenProductIsNotExists() {
        given(productRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(ProductNotFound.class, () -> createOrderService.create(userName, orderLines, orderer,
                shippingInformation, payment, shippingFee, cost));
    }

    @Test
    void whenProductIsSoldOut() {
        given(productRepository.findById(any()))
                .willReturn(Optional.of(Product.fake(ProductStatus.SOLD_OUT)));

        assertThrows(ProductSoldOut.class, () -> createOrderService.create(userName, orderLines, orderer,
                shippingInformation, payment, shippingFee, cost));
    }
}
