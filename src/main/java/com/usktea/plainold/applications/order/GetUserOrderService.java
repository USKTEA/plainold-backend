package com.usktea.plainold.applications.order;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.GuestIsNotAuthorized;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.OrderRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
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

    private void checkUserIsNotGuest(Users user) {
        if (user.isGuest()) {
            throw new GuestIsNotAuthorized();
        }
    }
}
