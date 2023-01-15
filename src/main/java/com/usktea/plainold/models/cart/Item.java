package com.usktea.plainold.models.cart;

import com.usktea.plainold.dtos.ItemDto;
import com.usktea.plainold.dtos.OptionDto;
import com.usktea.plainold.models.common.ItemOption;
import com.usktea.plainold.models.common.Money;
import com.usktea.plainold.models.common.Quantity;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.product.ProductName;
import com.usktea.plainold.models.product.ThumbnailUrl;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Item {
    private ProductId productId;

    @AttributeOverride(name = "amount", column = @Column(name = "price"))
    private Money price;

    @AttributeOverride(name = "value", column = @Column(name = "productName"))
    private ProductName productName;

    private ThumbnailUrl thumbnailUrl;

    @AttributeOverride(name = "amount", column = @Column(name = "shippingFee"))
    private Money shippingFee;

    @AttributeOverride(name = "amount", column = @Column(name = "freeShippingAmount"))
    private Money freeShippingAmount;

    @AttributeOverride(name = "amount", column = @Column(name = "quantity"))
    private Quantity quantity;

    private ItemOption itemOption;

    public Item() {
    }

    public Item(ProductId productId,
                Money price,
                ProductName productName,
                ThumbnailUrl thumbnailUrl,
                Money shippingFee,
                Money freeShippingAmount,
                Quantity quantity,
                ItemOption itemOption) {
        this.productId = productId;
        this.price = price;
        this.productName = productName;
        this.thumbnailUrl = thumbnailUrl;
        this.shippingFee = shippingFee;
        this.freeShippingAmount = freeShippingAmount;
        this.quantity = quantity;
        this.itemOption = itemOption;
    }

    public static Item of(ItemDto itemDto) {
        return new Item(
                new ProductId(itemDto.getProductId()),
                new Money(itemDto.getPrice()),
                new ProductName(itemDto.getName()),
                new ThumbnailUrl(itemDto.getThumbnailUrl()),
                new Money(itemDto.getShippingFee()),
                new Money(itemDto.getFreeShippingAmount()),
                new Quantity(itemDto.getQuantity()),
                new ItemOption(itemDto.getOption())
        );
    }

    public static Item fake(ProductId productId) {
        return new Item(
                productId,
                new Money(10_000L),
                new ProductName("T-Shirt"),
                new ThumbnailUrl("1"),
                new Money(2_500L),
                new Money(50_000L),
                new Quantity(1L),
                new ItemOption("L", "Black")
        );
    }

    public boolean isSame(Item otherItem) {
        return Objects.equals(this.productId, otherItem.productId)
                && Objects.equals(this.price, otherItem.price)
                && Objects.equals(this.productName, otherItem.productName)
                && Objects.equals(this.itemOption, otherItem.itemOption)
                && Objects.equals(this.shippingFee, otherItem.shippingFee)
                && Objects.equals(this.freeShippingAmount, otherItem.freeShippingAmount);
    }

    public Item increaseQuantity(Quantity quantity) {
        return new Item(
                productId,
                price,
                productName,
                thumbnailUrl,
                shippingFee,
                freeShippingAmount,
                this.quantity.add(quantity),
                itemOption
        );
    }

    public Item updateQuantity(Quantity quantity) {
        return new Item(
                productId,
                price,
                productName,
                thumbnailUrl,
                shippingFee,
                freeShippingAmount,
                quantity,
                itemOption
        );
    }

    public ProductId getProductId() {
        return productId;
    }

    public Money getPrice() {
        return price;
    }

    public ProductName getProductName() {
        return productName;
    }

    public ThumbnailUrl getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Money getShippingFee() {
        return shippingFee;
    }

    public Money getFreeShippingAmount() {
        return freeShippingAmount;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public ItemOption getItemOption() {
        return itemOption;
    }


    public ItemDto toDto() {
        return new ItemDto(
                productId.getProductId(),
                price.getAmount(),
                productName.getValue(),
                thumbnailUrl.getThumbnailUrl(),
                shippingFee.getAmount(),
                freeShippingAmount.getAmount(),
                quantity.getAmount(),
                new OptionDto(itemOption.getSize().name(), itemOption.getColor())
        );
    }
}
