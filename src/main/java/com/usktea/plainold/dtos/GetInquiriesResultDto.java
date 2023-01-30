package com.usktea.plainold.dtos;

import java.util.List;

public class GetInquiriesResultDto {
    private List<InquiryViewDto> inquiries;
    private PageDto page;

    public GetInquiriesResultDto() {
    }

    public GetInquiriesResultDto(List<InquiryViewDto> inquiries, PageDto page) {
        this.inquiries = inquiries;
        this.page = page;
    }

    public List<InquiryViewDto> getInquiries() {
        return inquiries;
    }

    public PageDto getPage() {
        return page;
    }
}
