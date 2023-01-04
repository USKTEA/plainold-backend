package com.usktea.plainold.dtos;

public class OrdererDto {
    private String name;
    private String phoneNumber;
    private String email;

    public OrdererDto() {
    }

    public OrdererDto(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public static OrdererDto fake() {
        return new OrdererDto("김뚜루", "010-1111-1111", "tjrxo1234@gmail.com");
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
