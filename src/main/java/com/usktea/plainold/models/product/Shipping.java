package com.usktea.plainold.models.product;

import com.usktea.plainold.dtos.ShippingDto;
import com.usktea.plainold.models.common.Money;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Embeddable
public class Shipping {
    @Enumerated(EnumType.STRING)
    private ShippingMethod shippingMethod;

    @AttributeOverride(name = "amount", column = @Column(name = "shippingFee"))
    private Money shippingFee;

    @AttributeOverride(name = "amount", column = @Column(name = "freeShippingAmount"))
    private Money freeShippingAmount;

    public Shipping() {
    }

    public Shipping(ShippingMethod shippingMethod, Money shippingFee, Money freeShippingAmount) {
        this.shippingMethod = shippingMethod;
        this.shippingFee = shippingFee;
        this.freeShippingAmount = freeShippingAmount;
    }

    public static Shipping fake(ShippingMethod shippingMethod) {
        return new Shipping(shippingMethod, new Money(2_500L), new Money(50_000L));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Shipping otherShipping = (Shipping) other;

        return Objects.equals(shippingMethod, otherShipping.shippingMethod)
                && Objects.equals(shippingFee, otherShipping.shippingFee)
                && Objects.equals(freeShippingAmount, otherShipping.freeShippingAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shippingMethod, shippingFee, freeShippingAmount);
    }

    public ShippingDto toDto() {
        return new ShippingDto(
                shippingMethod.value(),
                shippingFee.getAmount(),
                freeShippingAmount.getAmount());
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public Money getShippingFee() {
        return shippingFee;
    }

    public Money getFreeShippingAmount() {
        return freeShippingAmount;
    }
}
