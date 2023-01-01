package com.usktea.plainold.models;

import com.usktea.plainold.dtos.OrderItemDto;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderLine {
    private Long productId;
    private Long price;
    private String productName;
    private String thumbnailUrl;
    private Long quantity;
    private Long totalPrice;

    public OrderLine() {
    }

    public OrderLine(ProductId productId,
                     Money price,
                     ProductName productName,
                     ThumbnailUrl thumbnailUrl,
                     Quantity quantity,
                     Money totalPrice) {
        this.productId = productId.value();
        this.price = price.amount();
        this.productName = productName.value();
        this.thumbnailUrl = thumbnailUrl.value();
        this.quantity = quantity.amount();
        this.totalPrice = totalPrice.amount();
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

    public Long getProductId() {
        return productId;
    }
}
