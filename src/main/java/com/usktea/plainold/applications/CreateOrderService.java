package com.usktea.plainold.applications;

import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.models.Money;
import com.usktea.plainold.models.Order;
import com.usktea.plainold.models.OrderLine;
import com.usktea.plainold.models.OrderNumber;
import com.usktea.plainold.models.Orderer;
import com.usktea.plainold.models.Payment;
import com.usktea.plainold.models.Product;
import com.usktea.plainold.models.ShippingInformation;
import com.usktea.plainold.models.UserName;
import com.usktea.plainold.repositories.OrderRepository;
import com.usktea.plainold.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CreateOrderService {
    private final OrderNumberService orderNumberService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public CreateOrderService(OrderNumberService orderNumberService,
                              OrderRepository orderRepository,
                              ProductRepository productRepository) {
        this.orderNumberService = orderNumberService;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order create(UserName userName,
                        List<OrderLine> orderLines,
                        Orderer orderer,
                        ShippingInformation shippingInformation,
                        Payment payment,
                        Money shippingFee,
                        Money cost) {
        // TODO UserName 유효성 검사 필요
        OrderNumber orderNumber = orderNumberService.nextOrderNumber(userName);

        List<Long> productIds = orderLines
                .stream()
                .map((orderLine -> orderLine.getProductId()))
                .collect(Collectors.toList());

        List<Product> products = getProductsByIds(productIds);

        checkIsSoldOut(products);

        Order order = new Order(orderNumber, userName, orderLines, orderer,
                shippingInformation, payment, shippingFee, cost);

        Order saved = orderRepository.save(order);

        return saved;
    }

    private List<Product> getProductsByIds(List<Long> productIds) {
        return productIds.stream()
                .map((productId) -> productRepository.findById(productId)
                        .orElseThrow(ProductNotFound::new))
                .collect(Collectors.toList());
    }

    private void checkIsSoldOut(List<Product> products) {
        products.stream().forEach(Product::checkIsSoldOut);
    }
}
