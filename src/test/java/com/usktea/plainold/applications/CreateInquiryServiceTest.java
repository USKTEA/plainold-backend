package com.usktea.plainold.applications;

import com.usktea.plainold.dtos.CreateInquiryRequest;
import com.usktea.plainold.exceptions.GuestIsNotAuthorized;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.inquiry.Inquiry;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Role;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.InquiryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
class CreateInquiryServiceTest {
    private GetUserService getUserService;
    private GetProductService getProductService;
    private InquiryRepository inquiryRepository;
    private CreateInquiryService createInquiryService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        getProductService = mock(GetProductService.class);
        inquiryRepository = mock(InquiryRepository.class);
        createInquiryService = new CreateInquiryService(
                getUserService, getProductService, inquiryRepository
        );
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("notExists@gmail.com");
        ProductId productId = new ProductId(1L);
        CreateInquiryRequest createInquiryRequest = CreateInquiryRequest.fake(productId);

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class, () -> createInquiryService.create(username, createInquiryRequest));
    }

    @Test
    void whenGuestTryToCreateInquiry() {
        Username username = new Username("guest");
        ProductId productId = new ProductId(1L);
        CreateInquiryRequest createInquiryRequest = CreateInquiryRequest.fake(productId);

        given(getUserService.find(username)).willReturn(Users.fake(username, Role.GUEST));

        assertThrows(GuestIsNotAuthorized.class,
                () -> createInquiryService.create(username, createInquiryRequest));
    }

    @Test
    void whenProductNotExists() {
        Username username = new Username("tjrxo1234@mgail.com");
        ProductId productId = new ProductId(1L);
        CreateInquiryRequest createInquiryRequest = CreateInquiryRequest.fake(productId);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getProductService.find(productId)).willThrow(ProductNotFound.class);

        assertThrows(ProductNotFound.class,
                () -> createInquiryService.create(username, createInquiryRequest));
    }

    @Test
    void whenCreateSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(1L);
        CreateInquiryRequest createInquiryRequest = CreateInquiryRequest.fake(productId);
        Inquiry inquiry = Inquiry.fake(productId);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getProductService.find(productId)).willReturn(Product.fake(productId));
        given(inquiryRepository.save(any(Inquiry.class))).willReturn(inquiry);

        Long saved = createInquiryService.create(username, createInquiryRequest);

        assertThat(saved).isNotNull();

        verify(inquiryRepository).save(any(Inquiry.class));
    }
}
