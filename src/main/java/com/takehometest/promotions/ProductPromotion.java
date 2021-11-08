package com.takehometest.promotions;

import com.takehometest.inventory.Product;
import com.takehometest.shoppingcart.CartItem;

import java.math.BigDecimal;
import java.util.List;

/**
 *  This class provides an algorithm for applying a promotion to discount a specific
 *  product based off the number of items of that product wanting to be purchase.
 */
public class ProductPromotion implements Promotion {

    private static final PromotionApplicationType applicationType = PromotionApplicationType.BEFORE;
    private final Product product;
    private final int promotionQuantity;
    private final BigDecimal discountPrice;

    public ProductPromotion(Product product, int promotionQuantity, BigDecimal discountPrice) {
        this.product = product;
        this.promotionQuantity = promotionQuantity;
        this.discountPrice = discountPrice;
    }

    /**
     * An algorithm for discounting the price of a product if a certain number of
     * that product is wanting to be purchased
     *
     * @param cart A collection of cart items that a customer will like to purchase
     * @param total The grand total of the items in a cart before any promotion applied
     * @return The total of a customer shopping cart discounted by the product current promotion, if eligible
     */
    @Override
    public BigDecimal apply(List<CartItem> cart, BigDecimal total) {
        int totalNumberOfProduct = 0;
        CartItem cartItem = new CartItem(product);
        if(cart.contains(cartItem)) {
            int productIndex = cart.indexOf(cartItem);
            totalNumberOfProduct = cart.get(productIndex).getQuantity();
            if (totalNumberOfProduct >= promotionQuantity) {
                BigDecimal discount = (product.getPrice().subtract(discountPrice))
                        .multiply(BigDecimal.valueOf(totalNumberOfProduct));
                 total = total.subtract(discount);
            }
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
