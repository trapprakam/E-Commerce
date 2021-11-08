package com.ecommercesite.promotions;

import com.ecommercesite.inventory.Product;
import com.ecommercesite.shoppingcart.CartItem;

import java.math.BigDecimal;
import java.util.List;

public class BuyOneGetOneFree implements Promotion {

    private static final PromotionApplicationType applicationType = PromotionApplicationType.BEFORE;
    private final Product product;

    public BuyOneGetOneFree(Product product) {
        this.product = product;
    }

    @Override
    public BigDecimal apply(List<CartItem> cart, BigDecimal total) {
        CartItem cartItem = new CartItem(product);
        if (cart.contains(cartItem)) {
            int index = cart.indexOf(cartItem);
            int quantity = cart.get(index).getQuantity();
            int numberOfProductsToDiscount = quantity / 2;
            BigDecimal amountToDiscount = product.getPrice().multiply(BigDecimal.valueOf(numberOfProductsToDiscount));
            total = total.subtract(amountToDiscount);
        }
        return total;
    }

    @Override
    public PromotionApplicationType getApplicationType() {
        return applicationType;
    }
}
