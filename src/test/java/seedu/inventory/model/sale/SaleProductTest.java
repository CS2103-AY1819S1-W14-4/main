package seedu.inventory.model.sale;

import org.junit.Test;

import seedu.inventory.testutil.Assert;

public class SaleProductTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new SaleProduct(null));
    }

    @Test
    public void constructor_invalidSku_throwsIllegalArgumentException() {
        String invalidProductSku = "";

        Assert.assertThrows(IllegalArgumentException.class, () -> new SaleProduct(invalidProductSku));
    }

    @Test
    public void isValidProductSku() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> SaleProduct.isValidProductSku(null));
    }
}