package com.usktea.plainold.applications;

import com.usktea.plainold.dtos.OrderRequest;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderLine;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
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

    public Order placeOrder(OrderRequest orderRequest) {
        // TODO UserName 유효성 검사 필요
        // TODO 주문수량, 주문금액 같은지 비교
        OrderNumber orderNumber = orderNumberService.nextOrderNumber(orderRequest.getUserName());

        List<Product> products = findProducts(orderRequest.getOrderLines());

        checkIsSoldOut(products);

        Order order = new Order(orderNumber, orderRequest);

        Order saved = orderRepository.save(order);

        return saved;
    }

    private List<Product> findProducts(List<OrderLine> orderLines) {
        List<ProductId> productIds = getProductIds(orderLines);

        return getProductsByIds(productIds);
    }

    private List<ProductId> getProductIds(List<OrderLine> orderLines) {
        return orderLines
                .stream()
                .map((orderLine -> orderLine.getProductId()))
                .collect(Collectors.toList());
    }

    private List<Product> getProductsByIds(List<ProductId> productIds) {
        return productIds.stream()
                .map((productId) -> productRepository.findById(productId)
                        .orElseThrow(ProductNotFound::new))
                .collect(Collectors.toList());
    }

    private void checkIsSoldOut(List<Product> products) {
        products.stream().forEach(Product::checkIsSoldOut);
    }
}
