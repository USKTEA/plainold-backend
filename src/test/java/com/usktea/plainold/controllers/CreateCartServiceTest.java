package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.cart.CreateCartService;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.repositories.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@ActiveProfiles("test")
class CreateCartServiceTest {
    @MockBean
    private CartRepository cartRepository;

    private CreateCartService createCartService;

    @BeforeEach
    void setup() {
        cartRepository = mock(CartRepository.class);
        createCartService = new CreateCartService(cartRepository);
    }

    @Test
    void create() {
        Username username = new Username("tjrxo1234@gmail.com");

        createCartService.create(username);

        verify(cartRepository).save(any());
    }
}
