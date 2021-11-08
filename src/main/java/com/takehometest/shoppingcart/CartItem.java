package com.takehometest.shoppingcart;

import com.takehometest.inventory.Product;

import java.util.Objects;

/**
 * This class represents a cart item entity
 */
public class CartItem {

    private final Product product;
    private int quantity;

    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    /**
     * Returns the specific product of this cart item
     *
     * @return The product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Returns the total quantity of this cart item
     *
     * @return  The total quantity for this cart item
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Increments the total quantity for this cart item by 1
     */
    public void incrementQuantity() {
        quantity++;
    }

    /**
     * Decrease the total quantity for this cart item by 1
     */
    public void decrementQuantity(){
        if (quantity > 0) {
            quantity--;
        }
    }

    /**
     *
     * Compares this object/class to a given object.
     * This function will compare each object by the product field only
     *
     * @param o A given object to compare this class/object with
     * @return true if the objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return product.equals(cartItem.product);
    }

    /**
     *  Returns a hashcode for this object
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(product);
    }
}
