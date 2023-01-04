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

    public static ReceiverDto fake() {
        return new ReceiverDto("김뚜루", "010-1111-1111");
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
