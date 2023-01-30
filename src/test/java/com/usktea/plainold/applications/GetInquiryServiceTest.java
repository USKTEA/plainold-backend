package com.usktea.plainold.applications;

import com.usktea.plainold.dtos.GetInquiriesRequestDto;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.inquiry.Inquiry;
import com.usktea.plainold.models.inquiry.InquiryView;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.InquiryRepository;
import com.usktea.plainold.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
@SuppressWarnings("unchecked")
class GetInquiryServiceTest {
    private GetUserService getUserService;
    private ProductRepository productRepository;
    private InquiryRepository inquiryRepository;
    private GetInquiryService getInquiryService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        productRepository = mock(ProductRepository.class);
        inquiryRepository = mock(InquiryRepository.class);
        getInquiryService = new GetInquiryService(getUserService, productRepository, inquiryRepository);
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("notExists@gmail.com");
        ProductId productId = new ProductId(1L);
        Integer pageNumber = 1;

        GetInquiriesRequestDto getInquiriesRequestDto = new GetInquiriesRequestDto(username, productId, pageNumber);

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class, () -> getInquiryService.inquiries(getInquiriesRequestDto));
    }

    @Test
    void whenProductNotExists() {
        Username username = new Username("notExists@gmail.com");
        ProductId productId = new ProductId(9_999_999L);
        Integer pageNumber = 1;

        GetInquiriesRequestDto getInquiriesRequestDto = new GetInquiriesRequestDto(username, productId, pageNumber);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(productRepository.findById(productId)).willThrow(ProductNotFound.class);

        assertThrows(ProductNotFound.class, () -> getInquiryService.inquiries(getInquiriesRequestDto));
    }

    @Test
    void whenInquiriesSuccess() {
        Username username = new Username("notExists@gmail.com");
        ProductId productId = new ProductId(1L);
        Integer pageNumber = 1;

        GetInquiriesRequestDto getInquiriesRequestDto = new GetInquiriesRequestDto(username, productId, pageNumber);

        Page<Inquiry> page = new PageImpl<>(List.of(Inquiry.fake(productId)));

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(productRepository.findById(productId))
                .willReturn(Optional.of(Product.fake(productId)));

        given(inquiryRepository.findAll(any(Specification.class), any(Pageable.class)))
                .willReturn(page);

        Page<InquiryView> inquiries = getInquiryService.inquiries(getInquiriesRequestDto);

        assertThat(inquiries.getTotalElements()).isEqualTo(1);
    }
}
