package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.GetInquiryService;
import com.usktea.plainold.dtos.GetInquiriesRequestDto;
import com.usktea.plainold.dtos.GetInquiriesResultDto;
import com.usktea.plainold.dtos.InquiryViewDto;
import com.usktea.plainold.dtos.PageDto;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.models.inquiry.Inquiry;
import com.usktea.plainold.models.inquiry.InquiryView;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("inquiries")
public class InquiryController {
    private final GetInquiryService getInquiryService;

    public InquiryController(GetInquiryService getInquiryService) {
        this.getInquiryService = getInquiryService;
    }

    @GetMapping
    public GetInquiriesResultDto list(
            @RequestAttribute(required = false) Username username,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            HttpServletResponse response
    ) {
        GetInquiriesRequestDto getInquiriesRequestDto = new GetInquiriesRequestDto(
                username, new ProductId(productId), pageNumber);

        Page<InquiryView> inquiries = getInquiryService.inquiries(getInquiriesRequestDto);

        if (inquiries.isEmpty()) {
            response.setStatus(204);
        }

        List<InquiryViewDto> inquiryViewDtos = inquiries
                .getContent()
                .stream()
                .map(InquiryView::toDto)
                .collect(Collectors.toList());

        PageDto pageDto = new PageDto(pageNumber, inquiries.getTotalPages());

        return new GetInquiriesResultDto(inquiryViewDtos, pageDto);
    }

    @ExceptionHandler(ProductNotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String productNotFound(Exception exception) {
        return exception.getMessage();
    }
}
