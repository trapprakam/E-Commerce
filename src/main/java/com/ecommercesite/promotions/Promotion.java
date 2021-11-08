package com.ecommercesite.promotions;

import com.ecommercesite.shoppingcart.CartItem;

import java.math.BigDecimal;
import java.util.List;

public interface Promotion {

    /**
     *  Applies a promotion to a checkout cart and the grand total of the items
     *  in the cart
     *
     * @param cart A collection of cart items that a customer will like to purchase
     * @param total The grand total of the items in a cart before any promotion applied
     * @return The total of the cart after/if a promotion is applied
     */
    BigDecimal apply(List<CartItem> cart, BigDecimal total);

    /**
     * Returns the application type of the promotion
     * @return The application type, indicating when promotion should be applied
     */
    PromotionApplicationType getApplicationType();

}
