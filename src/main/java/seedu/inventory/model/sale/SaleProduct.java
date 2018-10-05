package seedu.inventory.model.sale;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.AppUtil.checkArgument;

/**
 * Represents a Product in the sale order.
 * Guarantees: immutable; name is valid as declared in {@link #isValidProductSku(String)}
 */
public class SaleProduct {

    public static final String MESSAGE_SKU_CONSTRAINTS = "Product SKU should be alphanumeric and not empty.";
    public static final String SKU_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String productSku;

    /**
     * Constructs a {@code SaleProduct}.
     *
     * @param productSku A valid product SKU.
     */
    public SaleProduct(String productSku) {
        requireNonNull(productSku);
        checkArgument(isValidProductSku(productSku), MESSAGE_SKU_CONSTRAINTS);
        this.productSku = productSku;
    }

    /**
     * Returns true if a given string is a valid product SKU.
     */
    public static boolean isValidProductSku(String test) {
        return test.matches(SKU_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SaleProduct // instanceof handles nulls
                && productSku.equals(((SaleProduct) other).productSku)); // state check
    }

    @Override
    public int hashCode() {
        return productSku.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + productSku + ']';
    }

}