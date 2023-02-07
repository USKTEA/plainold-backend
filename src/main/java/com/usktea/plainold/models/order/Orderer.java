package com.usktea.plainold.models.order;

import com.usktea.plainold.dtos.OrdererDto;
import com.usktea.plainold.models.common.PhoneNumber;
import com.usktea.plainold.models.common.Name;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Orderer {
    @AttributeOverride(name = "value", column = @Column(name = "orderer"))
    private Name name;

    @AttributeOverride(name = "value", column = @Column(name = "ordererPhoneNumber"))
    private PhoneNumber phoneNumber;
    private Email email;

    public Orderer() {
    }

    public Orderer(Name name, PhoneNumber phoneNumber, Email email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public static Orderer of(OrdererDto ordererDto) {
        return new Orderer(
                new Name(ordererDto.getName()),
                new PhoneNumber(ordererDto.getPhoneNumber()),
                new Email(ordererDto.getEmail()));
    }

    public static Orderer fake(Name name) {
        return new Orderer(name,
                new PhoneNumber("010-1111-1111"),
                new Email("tjrxo1234@gmail.com")
        );
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Orderer otherOrderer = (Orderer) other;

        return Objects.equals(name, otherOrderer.name)
                && Objects.equals(phoneNumber, otherOrderer.phoneNumber)
                && Objects.equals(email, otherOrderer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, email);
    }

    public Name getName() {
        return name;
    }

    public OrdererDto toDto() {
        return new OrdererDto(
                name.value(),
                phoneNumber.value(),
                email.value()
        );
    }
}
