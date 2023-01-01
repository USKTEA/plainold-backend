package com.usktea.plainold.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderNumberTest {

    @Test
    void equality() {
        OrderNumber orderNumber1 = new OrderNumber("tjrxo1234-202211221212");
        OrderNumber orderNumber2 = new OrderNumber("tjrxo1234-202211221212");
        OrderNumber orderNumber3 = new OrderNumber("rlatjr1234-202211221212");

        assertThat(orderNumber1).isEqualTo(orderNumber2);
        assertThat(orderNumber1).isNotEqualTo(orderNumber3);
    }
}
