package com.usktea.plainold.applications;

import com.usktea.plainold.dtos.OrderRequest;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.models.common.ItemOption;
import com.usktea.plainold.models.option.Option;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderLine;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.repositories.OptionRepository;
import com.usktea.plainold.repositories.OrderRepository;
import com.usktea.plainold.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class CreateOrderService {
    private final OrderNumberService orderNumberService;
    private final FindUserService findUserService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    public CreateOrderService(OrderNumberService orderNumberService,
                              FindUserService findUserService,
                              OrderRepository orderRepository,
                              ProductRepository productRepository,
                              OptionRepository optionRepository) {
        this.orderNumberService = orderNumberService;
        this.findUserService = findUserService;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
    }

    public Order placeOrder(OrderRequest orderRequest) {
        // TODO 주문수량, 주문금액 같은지 비교
        Username username = orderRequest.getUserName();

        findUserService.find(username);

        OrderNumber orderNumber = getNextOrderNumber(username);

        List<OrderLine> orderLines = orderRequest.getOrderLines();

        List<ProductId> productIds = getProductIds(orderLines);

        List<Option> options = getOptionsByProductId(productIds);

        List<Product> products = getProductsByIds(productIds);

        checkIsValidOption(options, orderLines);

        checkIsSoldOut(products);

        Order order = new Order(orderNumber, orderRequest);

        Order saved = orderRepository.save(order);

        return saved;
    }

    private OrderNumber getNextOrderNumber(Username userName) {
        return orderNumberService.nextOrderNumber(userName);
    }

    private List<Option> getOptionsByProductId(List<ProductId> productIds) {
        return optionRepository.findAllByProductIdIn(productIds);
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

    private void checkIsValidOption(List<Option> options, List<OrderLine> orderLines) {
        Map<ProductId, Option> optionMap = options
                .stream()
                .collect(Collectors.toMap(Option::getProductId, Function.identity()));

        orderLines.stream()
                .forEach((orderLine -> {
                    Option option = optionMap.get(orderLine.getProductId());

                    if (option == null) {
                        return;
                    }

                    ItemOption itemOption = orderLine.getOption();

                    option.checkIsValid(itemOption.getSize(), itemOption.getColor());
                }));
    }
}
