package com.usktea.plainold.applications.order;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.GuestIsNotAuthorized;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderStatus;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.OrderRepository;
import com.usktea.plainold.specifications.OrderSpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class GetUserOrderService {
    private final GetUserService getUserService;
    private final OrderRepository orderRepository;

    public GetUserOrderService(GetUserService getUserService, OrderRepository orderRepository) {
        this.getUserService = getUserService;
        this.orderRepository = orderRepository;
    }

    public List<Order> orders(Username username) {
        Users user = getUserService.find(username);

        checkUserIsNotGuest(user);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        List<Order> orders = orderRepository.findAllByUsername(user.username(), sort);

        return orders;
    }

    public List<Order> orders(Username username, OrderStatus orderStatus) {
        Users users = getUserService.find(username);

        checkUserIsNotGuest(users);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        Specification<Order> specification =
                OrderSpecification.equal(username, orderStatus);

        List<Order> orders = orderRepository.findAll(specification, sort);

        return orders;
    }

    private void checkUserIsNotGuest(Users user) {
        if (user.isGuest()) {
            throw new GuestIsNotAuthorized();
        }
    }
}
