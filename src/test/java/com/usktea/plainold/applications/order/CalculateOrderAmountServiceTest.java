package com.usktea.plainold.applications.order;

import com.usktea.plainold.applications.payment.CalculateOrderAmountService;
import com.usktea.plainold.dtos.OrderItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class CalculateOrderAmountServiceTest {
    @Test
    void calculate() {
        CalculateOrderAmountService calculateOrderAmountService = new CalculateOrderAmountService();

        List<OrderItemDto> orderItems = List.of(OrderItemDto.fake());

        Integer amount = calculateOrderAmountService.calculate(orderItems);

        assertThat(amount).isEqualTo(12_500);
    }
}
