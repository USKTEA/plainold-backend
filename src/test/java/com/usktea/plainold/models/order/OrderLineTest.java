package com.usktea.plainold.models.order;

import com.usktea.plainold.models.common.Money;
import com.usktea.plainold.models.common.Quantity;
import com.usktea.plainold.models.common.ItemOption;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.product.ProductName;
import com.usktea.plainold.models.product.ThumbnailUrl;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class OrderLineTest {
    @Test
    void equality() {
        ProductId productId = new ProductId(1L);
        Money price = new Money(1L);
        ProductName productName = new ProductName("T-Shirt");
        ThumbnailUrl thumbnailUrl = new ThumbnailUrl("1");
        Quantity quantity = new Quantity(2L);
        Money totalPrice = new Money(2L);
        ItemOption itemOption = new ItemOption("XL", "red");

        OrderLine orderLine1 = new OrderLine(productId, price, productName, thumbnailUrl, quantity, totalPrice, itemOption);
        OrderLine orderLine2 = new OrderLine(productId, price, productName, thumbnailUrl, quantity, totalPrice, itemOption);

        assertThat(orderLine1).isEqualTo(orderLine2);
    }
}
