package com.usktea.plainold.dtos;

import com.usktea.plainold.models.inquiry.Content;
import com.usktea.plainold.models.inquiry.InquiryType;
import com.usktea.plainold.models.inquiry.Title;
import com.usktea.plainold.models.product.ProductId;

public class CreateInquiryRequest {
    private ProductId productId;
    private InquiryType type;
    private Title title;
    private Content content;

    public CreateInquiryRequest(ProductId productId, InquiryType type, Title title, Content content) {
        this.productId = productId;
        this.type = type;
        this.title = title;
        this.content = content;
    }

    public static CreateInquiryRequest of(CreateInquiryRequestDto createInquiryRequestDto) {
        return new CreateInquiryRequest(
                new ProductId(createInquiryRequestDto.getProductId()),
                InquiryType.valueOf(createInquiryRequestDto.getType()),
                new Title(createInquiryRequestDto.getTitle()),
                new Content(createInquiryRequestDto.getContent())
        );
    }

    public static CreateInquiryRequest fake(ProductId productId) {
        return new CreateInquiryRequest(
                productId,
                InquiryType.PUBLIC,
                new Title("사이즈 문의"),
                new Content("이렇게 입으면 될까요")
        );
    }

    public InquiryType type() {
        return type;
    }

    public ProductId productId() {
        return productId;
    }

    public Title title() {
        return title;
    }

    public Content content() {
        return content;
    }
}
