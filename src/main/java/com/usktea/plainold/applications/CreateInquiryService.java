package com.usktea.plainold.applications;

import com.usktea.plainold.dtos.CreateInquiryRequest;
import com.usktea.plainold.exceptions.GuestIsNotAuthorized;
import com.usktea.plainold.models.inquiry.Inquiry;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.InquiryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CreateInquiryService {
    private final GetUserService getUserService;
    private final GetProductService getProductService;
    private final InquiryRepository inquiryRepository;

    public CreateInquiryService(GetUserService getUserService,
                                GetProductService getProductService,
                                InquiryRepository inquiryRepository) {
        this.getUserService = getUserService;
        this.getProductService = getProductService;
        this.inquiryRepository = inquiryRepository;
    }

    public Long create(Username username, CreateInquiryRequest createInquiryRequest) {
        Users user = getUserService.find(username);

        if (user.isGuest()) {
            throw new GuestIsNotAuthorized();
        }

        Product product = getProductService.find(createInquiryRequest.productId());

        Inquiry inquiry = Inquiry.of(
                product.id(), createInquiryRequest, user.username(), user.nickname()
        );

        Inquiry saved = inquiryRepository.save(inquiry);

        return saved.id();
    }
}
