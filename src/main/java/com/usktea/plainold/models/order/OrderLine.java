package com.usktea.plainold.models.order;

import com.usktea.plainold.dtos.OrderItemDto;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.product.ProductName;
import com.usktea.plainold.models.common.Quantity;
import com.usktea.plainold.models.product.ThumbnailUrl;
import com.usktea.plainold.models.common.Money;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderLine {
    @AttributeOverride(name = "value", column = @Column(name = "productId"))
    private ProductId productId;

    @AttributeOverride(name = "amount", column = @Column(name = "price"))
    private Money price;

    @AttributeOverride(name = "value", column = @Column(name = "productName"))
    private ProductName productName;

    private ThumbnailUrl thumbnailUrl;

    @AttributeOverride(name = "amount", column = @Column(name = "quantity"))
    private Quantity quantity;

    @AttributeOverride(name = "amount", column = @Column(name = "totalPrice"))
    private Money totalPrice;

    public OrderLine() {
    }

    public OrderLine(ProductId productId,
                     Money price,
                     ProductName productName,
                     ThumbnailUrl thumbnailUrl,
                     Quantity quantity,
                     Money totalPrice) {
        this.productId = productId;
        this.price = price;
        this.productName = productName;
        this.thumbnailUrl = thumbnailUrl;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public static OrderLine of(OrderItemDto orderItemDto) {
        return new OrderLine(
                new ProductId(orderItemDto.getProductId()),
                new Money(orderItemDto.getPrice()),
                new ProductName(orderItemDto.getName()),
                new ThumbnailUrl(orderItemDto.getThumbnailUrl()),
                new Quantity(orderItemDto.getQuantity()),
                new Money(orderItemDto.getTotalPrice()));
    }

    public static OrderLine fake(ProductId productId) {
        return new OrderLine(
                productId,
                new Money(1L),
                new ProductName("T-Shirt"),
                new ThumbnailUrl("1"),
                new Quantity(1L),
                new Money(1L));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        OrderLine otherOrderLine = (OrderLine) object;

        return Objects.equals(productId, otherOrderLine.productId)
                && Objects.equals(price, otherOrderLine.price)
                && Objects.equals(productName, otherOrderLine.productName)
                && Objects.equals(thumbnailUrl, otherOrderLine.thumbnailUrl)
                && Objects.equals(quantity, otherOrderLine.quantity)
                && Objects.equals(totalPrice, otherOrderLine.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, price, productName,
                thumbnailUrl, quantity, totalPrice);
    }

    public ProductId getProductId() {
        return productId;
    }
}
