package com.usktea.plainold.applications.inquiry;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.dtos.GetInquiriesRequestDto;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.models.inquiry.Inquiry;
import com.usktea.plainold.models.inquiry.InquiryView;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.InquiryRepository;
import com.usktea.plainold.repositories.ProductRepository;
import com.usktea.plainold.specifications.InquirySpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class GetInquiryService {
    private final GetUserService getUserService;
    private final ProductRepository productRepository;
    private final InquiryRepository inquiryRepository;

    public GetInquiryService(GetUserService getUserService,
                             ProductRepository productRepository,
                             InquiryRepository inquiryRepository) {
        this.getUserService = getUserService;
        this.productRepository = productRepository;
        this.inquiryRepository = inquiryRepository;
    }

    public Page<InquiryView> inquiries(GetInquiriesRequestDto getInquiriesRequestDto) {
        Integer pageNumber = getInquiriesRequestDto.pageNumber();

        Users user = getUser(getInquiriesRequestDto.username());
        Product product = getProduct(getInquiriesRequestDto.productId());

        Sort sort = Sort.by("CreatedAt").descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        Page<Inquiry> inquiries = getInquiries(product.id(), sort, pageable);

        List<InquiryView> inquiryViews = inquiries
                .getContent()
                .stream()
                .map((inquiry -> inquiry.toView(user.username(), user.role())))
                .collect(Collectors.toList());

        Page<InquiryView> page = new PageImpl<>(inquiryViews, pageable, inquiries.getTotalElements());

        return page;
    }

    private Page<Inquiry> getInquiries(ProductId id, Sort sort, Pageable pageable) {
        Specification<Inquiry> specification =
                InquirySpecification.equalProductId(id, sort)
                        .and(InquirySpecification.notDeleted());

        return inquiryRepository.findAll(specification, pageable);
    }

    private Product getProduct(ProductId productId) {
        return productRepository.findById(productId).orElseThrow(ProductNotFound::new);
    }

    private Users getUser(Username username) {
        return getUserService.find(username);
    }
}
