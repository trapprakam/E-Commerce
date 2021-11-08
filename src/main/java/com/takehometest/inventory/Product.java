package com.takehometest.inventory;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *  This class represents a product entity
 */
public class Product {

    private final long id;
    private final String name;
    private final BigDecimal price;

    public Product(long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    /**
     * Returns the unique ID for this product
     *
     * @return A unique ID in respect to the product
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the name of this product
     *
     * @return The name of the product
     */
    public String getName() {
        return name;
    }

    /**
     *  Returns the current price of this product
     *
     * @return The product current price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     *
     * Compares this object/class to a given object.
     * This function will compare each object by their fields
     *
     * @param o A given object to compare this class/object with
     * @return true if the objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && name.equals(product.name) && price.equals(product.price);
    }

    /**
     *  Returns a hashcode for this object
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
