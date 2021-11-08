package com.ecommercesite.promotions;

import com.ecommercesite.shoppingcart.CartItem;

import java.math.BigDecimal;
import java.util.List;

/**
 *  This class provides an algorithm for applying a promotion for if a customer
 *  spend over £75, then they will get a 10% discount.
 */
public class SpendOver75Promotion implements Promotion {

    private static final PromotionApplicationType applicationType = PromotionApplicationType.AFTER;

    /**
     * The algorithm for discounting the grand total of a customer shopping cart
     * if they spend over £75.
     *
     * @param cart A collection of cart items that a customer will like to purchase
     * @param total The grand total of the items in a cart before any promotion applied
     * @return The total of a customer shopping cart discounted by 10%, if eligible
     */
    @Override
    public BigDecimal apply(List<CartItem> cart, BigDecimal total) {
        if(total.compareTo(BigDecimal.valueOf(75.00)) >= 0) {
            BigDecimal decimalDiscountPercent = new BigDecimal(Double.toString(0.90));
            total = total.multiply(decimalDiscountPercent);
        }
        return total;
    }

    /**
     * Returns the application type of the promotion,
     * indicating when this promotion should be applied
     * @return The application type of this promotion,
     */
    @Override
    public PromotionApplicationType getApplicationType() {
        return applicationType;
    }


}
