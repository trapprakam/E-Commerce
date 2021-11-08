package com.ecommercesite.shoppingcart;

import com.ecommercesite.inventory.Product;
import com.ecommercesite.promotions.Promotion;
import com.ecommercesite.promotions.PromotionApplicationType;
import com.ecommercesite.promotions.SpendOver75Promotion;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class CheckoutTest {

    @Mock
    private Promotion spendOver75Promotion;
    @Mock
    private Promotion productPromotion;
    private Checkout underTest;

    @BeforeEach
    void setUp() {
        // Mock
        MockitoAnnotations.initMocks(this);
        List<Promotion> promotions = new ArrayList<>();
        promotions.add(spendOver75Promotion);
        promotions.add(productPromotion);
        underTest = new Checkout(promotions);
    }

    @Test
    void shouldApplyMultiplePromotions() {
        // Given
        // Items: 0001,0001,0002,0003
        List<Product> items = new ArrayList<>();
        items.add(new Product(0001, "Water Bottle", BigDecimal.valueOf(24.95)));
        items.add(new Product(0001, "Water Bottle", BigDecimal.valueOf(24.95)));
        items.add(new Product(0002, "Hoodie", BigDecimal.valueOf(65.00)));
        items.add(new Product(0003, "Sticker Set", BigDecimal.valueOf(3.99)));

        // Create cart and mock promotion algorithms
        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem(items.get(0)));
        cart.get(0).incrementQuantity();
        cart.add(new CartItem(items.get(2)));
        cart.add(new CartItem(items.get(3)));
        given(productPromotion.getApplicationType()).willReturn(PromotionApplicationType.BEFORE);
        given(productPromotion.apply(cart, BigDecimal.valueOf(118.89))).willReturn(BigDecimal.valueOf(114.97));
        given(spendOver75Promotion.getApplicationType()).willReturn(PromotionApplicationType.AFTER);
        given(spendOver75Promotion.apply(cart, BigDecimal.valueOf(114.97))).willReturn(BigDecimal.valueOf(103.473));

        // When
        underTest.scan(items);

        // Then
        // Total Price: £103.47
        assertEquals(BigDecimal.valueOf(103.47), underTest.total());
    }

    @Test
    void shouldApply10PercentDiscountForSpendingOver75Pounds(){
        // Given
        // Items: 0002,0002,0003
        List<Product> items = new ArrayList<>();
        items.add(new Product(0002, "Hoodie", BigDecimal.valueOf(65.00)));
        items.add(new Product(0002, "Hoodie", BigDecimal.valueOf(65.00)));
        items.add(new Product(0003, "Sticker Set", BigDecimal.valueOf(3.99)));

        // Create cart and mock promotion algorithms
        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem(items.get(0)));
        cart.get(0).incrementQuantity();
        cart.add(new CartItem(items.get(2)));
        given(productPromotion.getApplicationType()).willReturn(PromotionApplicationType.BEFORE);
        given(productPromotion.apply(cart, BigDecimal.valueOf(133.99))).willReturn(BigDecimal.valueOf(133.99));
        given(spendOver75Promotion.getApplicationType()).willReturn(PromotionApplicationType.AFTER);
        given(spendOver75Promotion.apply(cart, BigDecimal.valueOf(133.99))).willReturn(BigDecimal.valueOf(120.591));

        // When
        underTest.scan(items);

        // Then
        // Total Price: £120.59
        assertEquals(BigDecimal.valueOf(120.59), underTest.total());
    }

    @Test
    void shouldApplyWaterBottlePromotion(){
        // Given
        // Items: 0001,0001,0001
        List<Product> items = new ArrayList<>();
        BigDecimal waterBottlePrice = new BigDecimal("24.95");
        items.add(new Product(0001, "Water Bottle", waterBottlePrice));
        items.add(new Product(0001, "Water Bottle", waterBottlePrice));
        items.add(new Product(0001, "Water Bottle", waterBottlePrice));

        // Create cart and mock promotion algorithms
        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem(items.get(0)));
        cart.get(0).incrementQuantity();
        cart.get(0).incrementQuantity();
        given(productPromotion.getApplicationType()).willReturn(PromotionApplicationType.BEFORE);
        given(productPromotion.apply(cart, BigDecimal.valueOf(74.85))).willReturn(BigDecimal.valueOf(68.97));
        given(spendOver75Promotion.getApplicationType()).willReturn(PromotionApplicationType.AFTER);
        given(spendOver75Promotion.apply(cart, BigDecimal.valueOf(68.97))).willReturn(BigDecimal.valueOf(68.97));

        // When
        underTest.scan(items);

        // Then
        // Total Price: £68.97
        assertEquals(BigDecimal.valueOf(68.97), underTest.total());
    }

    @Test
    void shouldAllowForPromotionRulesToChange() {
        // Given
        List<Promotion> justSpendOver75Promotion = new ArrayList<>();
        justSpendOver75Promotion.add(new SpendOver75Promotion());

        // When
        // Current promotional rules
        List<Promotion> defaultPromotionalRules = underTest.getPromotionalRules();
        // Then
        assertEquals(spendOver75Promotion, defaultPromotionalRules.get(0));
        assertEquals(productPromotion, defaultPromotionalRules.get(1));

        // When
        // Change promotional rules
        underTest.setPromotionalRules(justSpendOver75Promotion);
        // Then
        // Assert that the promotional rules were changed
        assertNotEquals(defaultPromotionalRules, underTest.getPromotionalRules());
        // Assert that the current promotional rules being applied by the checkout
        // are the rules that we want the checkout system to currently apply
        assertEquals(justSpendOver75Promotion, underTest.getPromotionalRules());
    }

    @Test
    void shouldScanItemsInAnyOrderAndApplyPromotion() {
        // Given
        // Items: 0001,0001,0002,0003
        Product waterBottle1 = new Product(0001, "Water Bottle", BigDecimal.valueOf(24.95));
        Product waterBottle2 = new Product(0001, "Water Bottle", BigDecimal.valueOf(24.95));
        Product hoodie = new Product(0002, "Hoodie", BigDecimal.valueOf(65.00));
        Product stickerSet = new Product(0003, "Sticker Set", BigDecimal.valueOf(3.99));

        // Create cart and mock promotion algorithms
        List<CartItem> cart = new ArrayList<>();

        // When
        cart.add(new CartItem(waterBottle1));
        given(productPromotion.getApplicationType()).willReturn(PromotionApplicationType.BEFORE);
        given(productPromotion.apply(cart, BigDecimal.valueOf(24.95))).willReturn(BigDecimal.valueOf(24.95));
        given(spendOver75Promotion.getApplicationType()).willReturn(PromotionApplicationType.AFTER);
        given(spendOver75Promotion.apply(cart, BigDecimal.valueOf(24.95))).willReturn(BigDecimal.valueOf(24.95));
        underTest.scan(waterBottle1);
        // Then
        assertEquals(BigDecimal.valueOf(24.95), underTest.total());
        // When
        cart.add(new CartItem(hoodie));
        given(productPromotion.getApplicationType()).willReturn(PromotionApplicationType.BEFORE);
        given(productPromotion.apply(cart, BigDecimal.valueOf(89.95))).willReturn(BigDecimal.valueOf(89.95));
        given(spendOver75Promotion.getApplicationType()).willReturn(PromotionApplicationType.AFTER);
        given(spendOver75Promotion.apply(cart, BigDecimal.valueOf(89.95))).willReturn(BigDecimal.valueOf(80.955));
        underTest.scan(hoodie);
        // Then
        assertEquals(BigDecimal.valueOf(80.96), underTest.total());
        // When
        cart.add(new CartItem(stickerSet));
        given(productPromotion.getApplicationType()).willReturn(PromotionApplicationType.BEFORE);
        given(productPromotion.apply(cart, BigDecimal.valueOf(93.94))).willReturn(BigDecimal.valueOf(93.94));
        given(spendOver75Promotion.getApplicationType()).willReturn(PromotionApplicationType.AFTER);
        given(spendOver75Promotion.apply(cart, BigDecimal.valueOf(93.94))).willReturn(BigDecimal.valueOf(84.546));
        underTest.scan(stickerSet);
        // Then
        assertEquals(BigDecimal.valueOf(84.55), underTest.total());
        // When
        cart.get(0).incrementQuantity();
        given(productPromotion.getApplicationType()).willReturn(PromotionApplicationType.BEFORE);
        given(productPromotion.apply(cart, BigDecimal.valueOf(118.89))).willReturn(BigDecimal.valueOf(114.97));
        given(spendOver75Promotion.getApplicationType()).willReturn(PromotionApplicationType.AFTER);
        given(spendOver75Promotion.apply(cart, BigDecimal.valueOf(114.97))).willReturn(BigDecimal.valueOf(103.473));
        underTest.scan(waterBottle2);
        // Then
        assertEquals(BigDecimal.valueOf(103.47), underTest.total());

    }

}