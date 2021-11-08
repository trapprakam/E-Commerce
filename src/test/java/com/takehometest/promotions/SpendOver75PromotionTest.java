package com.takehometest.promotions;

import com.takehometest.inventory.Product;
import com.takehometest.shoppingcart.CartItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpendOver75PromotionTest {

    private final SpendOver75Promotion underTest = new SpendOver75Promotion();
    private final Product waterBottle = new Product(0001, "Water Bottle", BigDecimal.valueOf(24.95));
    private final Product hoodie = new Product(0002, "Hoodie", BigDecimal.valueOf(65.00));
    private final Product stickerSet = new Product(0003, "Sticker Set", BigDecimal.valueOf(3.99));

    @Test
    void whenCustomerSpendLessThan75Pounds() {
        // Given
        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem(waterBottle));
        cart.add(new CartItem(stickerSet));
        // Increment quantity of items in the cart to have a total of 2 water bottles and 3 sticker sets in cart
        cart.get(0).incrementQuantity();
        cart.get(1).incrementQuantity();
        cart.get(1).incrementQuantity();
        BigDecimal total = BigDecimal.valueOf(61.87);

        // When
        BigDecimal result = underTest.apply(cart, total);

        // Then
        assertEquals(total, result);
    }

    @Test
    void whenCustomerSpendMoreThan75Pounds() {
        // Given
        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem(waterBottle));
        cart.add(new CartItem(stickerSet));
        cart.add(new CartItem(hoodie));
        BigDecimal total = BigDecimal.valueOf(93.94);

        // When
        BigDecimal result = underTest.apply(cart, total);

        // Then
        assertEquals(BigDecimal.valueOf(84.546), result);
    }
}