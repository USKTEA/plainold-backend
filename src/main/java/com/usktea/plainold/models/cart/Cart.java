package com.usktea.plainold.models.cart;

import com.usktea.plainold.exceptions.CartItemNotExists;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
public class Cart {
    @Id
    @Embedded
    private Username username;

    @ElementCollection
    private List<Item> cartItems = new ArrayList<>();

    public Cart() {
    }

    public Cart(Username username) {
        this.username = username;
    }

    public static Cart fake(Username username) {
        return new Cart(username);
    }

    public void addItem(List<Item> items) {
        items.stream().forEach((item) -> {
            Item found = findFormCartItems(item);

            if (Objects.nonNull(found)) {
                int index = cartItems.indexOf(found);

                Item increased = found.increaseQuantity(item.getQuantity());

                cartItems.remove(index);
                cartItems.add(index, increased);

                return;
            }

            cartItems.add(item);
        });
    }

    public List<ProductId> deleteItem(List<Item> items) {
        if (cartItems.size() == 0) {
            throw new CartItemNotExists();
        }

        List<ProductId> deletedIds = items.stream().map((item) -> {
            Item found = findFormCartItems(item);

            if (Objects.isNull(found)) {
                throw new CartItemNotExists();
            }

            int index = cartItems.indexOf(found);

            cartItems.remove(index);

            return item.getProductId();
        }).collect(Collectors.toList());

        return deletedIds;
    }

    public List<ProductId> updateItem(List<Item> items) {
        if (cartItems.size() == 0) {
            throw new CartItemNotExists();
        }

        List<ProductId> updatedIds = items.stream().map((item) -> {
            Item found = findFormCartItems(item);

            if (Objects.isNull(found)) {
                throw new CartItemNotExists();
            }

            int index = cartItems.indexOf(found);

            Item updated = found.updateQuantity(item.getQuantity());

            cartItems.remove(index);
            cartItems.add(index, updated);

            return item.getProductId();
        }).collect(Collectors.toList());

        return updatedIds;
    }

    private Item findFormCartItems(Item item) {
        return cartItems.stream().filter((cartItem) -> {
            if (cartItem.isSame(item)) {
                return true;
            }

            return false;
        }).findFirst().orElse(null);
    }

    public List<Item> items() {
        return cartItems;
    }

    public int countItems() {
        return cartItems.size();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Cart otherCart = (Cart) object;

        return Objects.equals(username, otherCart.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
