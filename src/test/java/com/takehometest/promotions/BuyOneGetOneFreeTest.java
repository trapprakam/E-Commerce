package com.takehometest.promotions;

import com.takehometest.inventory.Product;
import com.takehometest.shoppingcart.CartItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuyOneGetOneFreeTest {

    private final Product sticker = new Product(0003, "Sticker Set", BigDecimal.valueOf(3.99));
    private final Promotion underTest = new BuyOneGetOneFree(sticker);


    @Test
    void applyToOneSticker() {
        // Given
        List<CartItem> cart = new ArrayList<>();
        CartItem cartItem = new CartItem(sticker);
        // Add cart item to basket
        cart.add(cartItem);
        // Get total of items in basket
        BigDecimal total = sticker.getPrice();

        // When
        BigDecimal result = underTest.apply(cart, total);

        // Then
        assertEquals(BigDecimal.valueOf(3.99), result);
    }


    @Test
    void applyToTwoStickers() {
        // Given
        List<CartItem> cart = new ArrayList<>();
        CartItem cartItem = new CartItem(sticker);
        // Increment quantity
        cartItem.incrementQuantity();
        // Add cart item to basket
        cart.add(cartItem);
        // Get total of items in basket
        BigDecimal total = BigDecimal.valueOf(7.98);

        // When
        BigDecimal result = underTest.apply(cart, total);

        // Then
        assertEquals(BigDecimal.valueOf(3.99), result);

    }

    @Test
    void applyToThreeStickers() {
        // Given
        List<CartItem> cart = new ArrayList<>();
        CartItem cartItem = new CartItem(sticker);
        // Increment quantity
        cartItem.incrementQuantity();
        cartItem.incrementQuantity();
        // Add cart item to basket
        cart.add(cartItem);
        // Get total of items in basket
        BigDecimal total = BigDecimal.valueOf(11.97);

        // When
        BigDecimal result = underTest.apply(cart, total);

        // Then
        assertEquals(BigDecimal.valueOf(7.98), result);
    }
}