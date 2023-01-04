package com.usktea.plainold.dtos;

public class AddressDto {
    private String zipCode;
    private String address1;
    private String address2;

    public AddressDto() {
    }

    public AddressDto(String zipCode, String address1, String address2) {
        this.zipCode = zipCode;
        this.address1 = address1;
        this.address2 = address2;
    }

    public static AddressDto fake() {
        return new AddressDto("111111", "서울시 성동구 상원12길 34", "에이원지식산업센터");
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }
}
