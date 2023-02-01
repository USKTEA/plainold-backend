package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.cart.AddCartItemService;
import com.usktea.plainold.applications.cart.CreateCartService;
import com.usktea.plainold.applications.cart.DeleteCartItemService;
import com.usktea.plainold.applications.cart.GetCartService;
import com.usktea.plainold.applications.cart.UpdateCartItemService;
import com.usktea.plainold.dtos.AddItemToCartRequestDto;
import com.usktea.plainold.dtos.AddItemToCartResultDto;
import com.usktea.plainold.dtos.DeleteCartItemRequestDto;
import com.usktea.plainold.dtos.DeleteCartItemResultDto;
import com.usktea.plainold.dtos.ItemsDto;
import com.usktea.plainold.dtos.UpdateItemInCartRequestDto;
import com.usktea.plainold.dtos.UpdateItemInCartResultDto;
import com.usktea.plainold.exceptions.CartNotExists;
import com.usktea.plainold.exceptions.UpdateCartItemFailed;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.cart.Item;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("carts")
public class CartController {
    private final GetCartService getCartService;
    private final CreateCartService createCartService;
    private final AddCartItemService addCartItemService;
    private final UpdateCartItemService updateCartItemService;
    private final DeleteCartItemService deleteCartItemService;

    public CartController(CreateCartService createCartService,
                          GetCartService getCartService,
                          AddCartItemService addCartItemService,
                          UpdateCartItemService updateCartItemService,
                          DeleteCartItemService deleteCartItemService) {
        this.createCartService = createCartService;
        this.getCartService = getCartService;
        this.addCartItemService = addCartItemService;
        this.updateCartItemService = updateCartItemService;
        this.deleteCartItemService = deleteCartItemService;
    }

    @GetMapping
    public ItemsDto items(
            @RequestAttribute Username username
    ) {
        try {
            List<Item> items = getCartService.cart(username);

            return new ItemsDto(items.stream()
                    .map(Item::toDto)
                    .collect(Collectors.toList()));
        } catch (CartNotExists exception) {
            createCartService.create(username);

            return new ItemsDto();
        }
    }

    @PostMapping
    public AddItemToCartResultDto addCartItem(
            @RequestAttribute Username username,
            @Valid @RequestBody AddItemToCartRequestDto addItemToCartRequestDto
    ) {
        List<Item> items = addItemToCartRequestDto
                .getItems()
                .stream()
                .map(Item::of)
                .collect(Collectors.toList());
        try {
            int counts = addCartItemService.addItem(username, items);

            return new AddItemToCartResultDto(counts);
        } catch (CartNotExists cartNotExists) {
            createCartService.create(username);

            int counts = addCartItemService.addItem(username, items);

            return new AddItemToCartResultDto(counts);
        }
    }

    @PostMapping("delete")
    public DeleteCartItemResultDto deleteCartItem(
            @RequestAttribute Username username,
            @Valid @RequestBody DeleteCartItemRequestDto deleteCartItemRequestDto
    ) {
        List<Item> items = deleteCartItemRequestDto
                .getItems()
                .stream()
                .map(Item::of)
                .collect(Collectors.toList());

        List<ProductId> deletedItemIds = deleteCartItemService.delete(username, items);

        List<Long> ids = getIds(deletedItemIds);

        return new DeleteCartItemResultDto(ids);
    }

    @PatchMapping
    public UpdateItemInCartResultDto updateCartItem(
            @RequestAttribute Username username,
            @Valid @RequestBody UpdateItemInCartRequestDto updateItemInCartRequestDto
    ) {
        try {
            List<Item> items = updateItemInCartRequestDto
                    .getItems()
                    .stream()
                    .map(Item::of)
                    .collect(Collectors.toList());

            List<ProductId> updatedItemIds = updateCartItemService
                    .updateItem(username, items);

            List<Long> ids = getIds(updatedItemIds);

            return new UpdateItemInCartResultDto(ids);
        } catch (Exception exception) {
            throw new UpdateCartItemFailed();
        }
    }

    private List<Long> getIds(List<ProductId> productIds) {
        return productIds.stream()
                .map(ProductId::value)
                .collect(Collectors.toList());
    }

    @ExceptionHandler(UpdateCartItemFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String updateCartItemFailed(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(UserNotExists.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userNotFound(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String illegalArguments(Exception exception) {
        return exception.getMessage();
    }
}
