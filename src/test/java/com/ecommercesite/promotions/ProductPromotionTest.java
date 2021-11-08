package com.ecommercesite.promotions;

import com.ecommercesite.inventory.Product;
import com.ecommercesite.shoppingcart.CartItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductPromotionTest {


    @Test
    void whenPromotionIsAppliedToOnlyOneProductInCart() {
        // Given
        ProductPromotion underTest = new ProductPromotion(
                new Product(0001, "Water Bottle",BigDecimal.valueOf(24.95)),
                2,
                BigDecimal.valueOf(22.99));
        Product product = new Product(0001, "Water Bottle", BigDecimal.valueOf(24.95));
        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem(product));
        BigDecimal total = BigDecimal.valueOf(24.95);

        // When
        BigDecimal result = underTest.apply(cart, total);

        // Then
        assertEquals(BigDecimal.valueOf(24.95), result);
    }

    @Test
    void whenPromotionIsAppliedToTwoProductsInCart() {
        // Given
        ProductPromotion underTest = new ProductPromotion(
                new Product(0001, "Water Bottle",BigDecimal.valueOf(24.95)),
                2,
                BigDecimal.valueOf(22.99));
        Product product = new Product(0001, "Water Bottle", BigDecimal.valueOf(24.95));
        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem(product));
        cart.get(0).incrementQuantity();
        BigDecimal total = BigDecimal.valueOf(49.90);

        // When
        BigDecimal result = underTest.apply(cart, total);

        // Then
        assertEquals(BigDecimal.valueOf(45.98), result);
    }

    @Test
    void whenPromotionIsAppliedToThreeProductsInCart() {
        // Given
        ProductPromotion underTest = new ProductPromotion(
                new Product(0001, "Water Bottle",BigDecimal.valueOf(24.95)),
                2,
                BigDecimal.valueOf(22.99));
        Product product = new Product(0001, "Water Bottle", BigDecimal.valueOf(24.95));
        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem(product));
        cart.get(0).incrementQuantity();
        cart.get(0).incrementQuantity();
        BigDecimal total = BigDecimal.valueOf(74.85);

        // When
        BigDecimal result = underTest.apply(cart, total);

        // Then
        assertEquals(BigDecimal.valueOf(68.97), result);
    }


}