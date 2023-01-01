package com.usktea.plainold.dtos;

public class ReceiverDto {
    private String name;
    private String phoneNumber;

    public ReceiverDto() {
    }

    public ReceiverDto(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
