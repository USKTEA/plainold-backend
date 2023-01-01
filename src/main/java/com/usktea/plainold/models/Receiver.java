package com.usktea.plainold.models;

import com.usktea.plainold.dtos.ReceiverDto;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Receiver {
    @AttributeOverride(name = "value", column = @Column(name = "receiver"))
    private Name name;

    @AttributeOverride(name = "value", column = @Column(name = "receiverPhoneNumber"))
    private PhoneNumber phoneNumber;

    public Receiver() {
    }

    public Receiver(Name name, PhoneNumber phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static Receiver of(ReceiverDto receiverDto) {
        return new Receiver(
                new Name(receiverDto.getName()),
                new PhoneNumber(receiverDto.getPhoneNumber())
        );
    }

    public static Receiver fake(Name name) {
        return new Receiver(name, new PhoneNumber("010-2222-2222"));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Receiver otherReceiver = (Receiver) other;

        return Objects.equals(name, otherReceiver.name)
                && Objects.equals(phoneNumber, otherReceiver.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber);
    }

    public Name getName() {
        return name;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public ReceiverDto toDto() {
        return new ReceiverDto(name.value(), phoneNumber.value());
    }
}
