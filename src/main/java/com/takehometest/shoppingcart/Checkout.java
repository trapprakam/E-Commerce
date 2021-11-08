package com.takehometest.shoppingcart;

import com.takehometest.inventory.Product;
import com.takehometest.promotions.Promotion;
import com.takehometest.promotions.PromotionApplicationType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to encapsulate the check-out system for
 * the e-commerce site
 */
public class Checkout {

    private List<CartItem> cart;
    private BigDecimal grandTotal;
    private BigDecimal totalWithOrWithoutDiscount;
    private List<Promotion> promotionalRules;

    public Checkout(List<Promotion> promotionalRules) {
        this.promotionalRules = promotionalRules;
        this.cart = new ArrayList<>();
        this.grandTotal = BigDecimal.ZERO;
        this.totalWithOrWithoutDiscount = BigDecimal.ZERO;
    }

    /**
     * Adds the item to the cart and update the total of the cart
     *
     * @param item A Product to be purchased
     */
    public void scan(Product item) {
        CartItem cartItem = new CartItem(item);
        if(!cart.contains(cartItem)) {
            cart.add(cartItem);
        } else {
            int itemIndex = cart.indexOf(cartItem);
            cart.get(itemIndex)
                    .incrementQuantity();
        }
        updateTotal(item.getPrice());
        applyPromotion();
    }

    /**
     * Adds the collection of items to the cart and update the total of the cart
     *
     * @param items A collection of products
     */
    public void scan(List<Product> items) {
        for(Product item: items) {
            scan(item);
        }
    }

    /**
     * Update the total amount of the cart based off the last item scanned
     *
     * @param price The price of the last item scanned
     */
    private void updateTotal(BigDecimal price) {
        grandTotal = grandTotal.add(price);
    }

    /**
     * Applies current promotions to the current items in the cart
     */
    private void applyPromotion() {
        totalWithOrWithoutDiscount = grandTotal;
        for(Promotion promotion: promotionalRules) {
            if(promotion.getApplicationType() == PromotionApplicationType.BEFORE) {
                totalWithOrWithoutDiscount = promotion.apply(cart, totalWithOrWithoutDiscount);
            }
        }
        for(Promotion promotion: promotionalRules) {
            if(promotion.getApplicationType() == PromotionApplicationType.AFTER) {
                totalWithOrWithoutDiscount = promotion.apply(cart, totalWithOrWithoutDiscount);
            }
        }
    }

    /**
     * Used to change/update the promotional rules the checkout system should use
     *
     * @param promotionalRules A collection of the current promotions
     */
    public void setPromotionalRules(List<Promotion> promotionalRules) {
        this.promotionalRules = promotionalRules;
    }

    /**
     * Returns the current promotions used by the checkout system
     *
     * @return Collection of promotions
     */
    public List<Promotion> getPromotionalRules() {
        return promotionalRules;
    }

    /**
     * Returns the total amount of the cart with or without promotions applied
     *
     * @return Current total of the cart with or without promotions applied
     */
    public BigDecimal total() {
        return totalWithOrWithoutDiscount.setScale(2, RoundingMode.HALF_UP);
    }
}
