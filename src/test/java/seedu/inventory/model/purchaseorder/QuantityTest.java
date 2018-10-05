package seedu.inventory.model.purchaseorder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.inventory.testutil.Assert;


public class QuantityTest {


    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Quantity(null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidQuantity = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Quantity(invalidQuantity));
    }

    @Test
    public void constructor_validQuantity() {
        assertNotNull(new Quantity("10"));
    }

    @Test
    public void isValidQuantity() {
        // null quanitity
        Assert.assertThrows(NullPointerException.class, () -> Quantity.isValidQuantity(null));

        // invalid Quantity numbers
        assertFalse(Quantity.isValidQuantity("")); // empty string
        assertFalse(Quantity.isValidQuantity(" ")); // spaces only
        assertFalse(Quantity.isValidQuantity("0")); // 0 quantity
        assertFalse(Quantity.isValidQuantity("012")); // starts with 0
        assertFalse(Quantity.isValidQuantity("-213")); // negative number
        assertFalse(Quantity.isValidQuantity("Quantity")); // non-numeric
        assertFalse(Quantity.isValidQuantity("asd123")); // alphabets within digits
        assertFalse(Quantity.isValidQuantity("31 221")); // spaces within digits

        // valid Quantity numbers
        assertTrue(Quantity.isValidQuantity("121"));
        assertTrue(Quantity.isValidQuantity("12334441"));
        assertTrue(Quantity.isValidQuantity("9218391230983912")); // long Quantity numbers
    }

    @Test
    public void testToString() {
        Quantity quantity = new Quantity("10");
        String expected = "10";
        assertEquals(expected, quantity.toString());
    }

    @Test
    public void testEqualsSymmetric() {
        Quantity q1 = new Quantity("10"); // equals and hashCode check name field value
        Quantity q2 = new Quantity("10");
        assertTrue(q1.equals(q2) && q2.equals(q1));
        assertTrue(q1.hashCode() == q2.hashCode());
    }
}