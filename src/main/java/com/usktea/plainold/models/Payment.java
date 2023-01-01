package com.usktea.plainold.models;

import com.usktea.plainold.dtos.PaymentDto;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Embeddable
public class Payment {
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @AttributeOverride(name = "value", column = @Column(name = "payer"))
    private Name payer;

    public Payment() {
    }

    public Payment(PaymentMethod method, Name payer) {
        this.method = method;
        this.payer = payer;
    }

    public static Payment of(PaymentDto paymentDto) {
        return new Payment(
                PaymentMethod.valueOf(paymentDto.getMethod()),
                new Name(paymentDto.getPayer())
        );
    }

    public static Payment fake(Name payer) {
        return new Payment(PaymentMethod.CASH, payer);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Payment otherPayment = (Payment) object;

        return method == otherPayment.method
                && Objects.equals(payer, otherPayment.payer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, payer);
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public Name getPayer() {
        return payer;
    }
}
