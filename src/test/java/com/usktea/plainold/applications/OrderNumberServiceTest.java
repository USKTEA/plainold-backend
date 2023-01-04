package com.usktea.plainold.applications;

import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.user.UserName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
class OrderNumberServiceTest {
    @Test
    void nextOrderNumber() {
        OrderNumberService orderNumberService = new OrderNumberService();
        UserName userName = new UserName("tjrxo1234@gmail.com");

        OrderNumber orderNumber = orderNumberService.nextOrderNumber(userName);

        assertThat(orderNumber.value().contains("tjrxo1234")).isTrue();
    }

    @Test
    void whenUserNameIsNull() {
        OrderNumberService orderNumberService = new OrderNumberService();

        assertThrows(IllegalArgumentException.class, () -> orderNumberService.nextOrderNumber(null));
    }
}
