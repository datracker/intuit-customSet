package com.intuit.customSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomSetTest {

    private CustomSet customSet;

    @BeforeEach
    void setUp() {
        customSet = new CustomSet();
    }

    @Test
    void testAddItem_PositiveNumber() {
        customSet.add(100);
        assertTrue(customSet.contains(100), "CustomSet should contain 100");
    }

    @Test
    void testAddItem_PositiveNumberMax() {
        customSet.add(Integer.MAX_VALUE);
        assertTrue(customSet.contains(Integer.MAX_VALUE), "CustomSet should contain " + Integer.MAX_VALUE);
    }

    @Test
    void testAddItem_NegativeNumber() {
        customSet.add(-100);
        assertTrue(customSet.contains(-100), "CustomSet should contain -100");
    }

    @Test
    void testAddItem_NegativeNumberMin() {
        customSet.add(Integer.MIN_VALUE);
        assertTrue(customSet.contains(Integer.MIN_VALUE), "CustomSet should contain " + Integer.MIN_VALUE);
    }

    @Test
    void testAddItem_Zero() {
        customSet.add(0);
        assertTrue(customSet.contains(0), "CustomSet should contain 0");
    }

    @Test
    void testRemoveItem_PositiveNumber() {
        customSet.add(100);
        customSet.remove(100);
        assertFalse(customSet.contains(100), "CustomSet should not contain 100 after removal");
    }

    @Test
    void testRemoveItem_NegativeNumber() {
        customSet.add(-100);
        customSet.remove(-100);
        assertFalse(customSet.contains(-100), "CustomSet should not contain -100 after removal");
    }

    @Test
    void testRemoveItem_Zero() {
        customSet.add(0);
        customSet.remove(0);
        assertFalse(customSet.contains(0), "CustomSet should not contain 0 after removal");
    }

    @Test
    void testHasItem_NotAdded() {
        assertFalse(customSet.contains(200), "CustomSet should not contain 200 if not added");
    }

//    @Test
//    void testAddItem_OutOfBounds() {
//        assertThrows(IllegalArgumentException.class, () -> customSet.add(Integer.MAX_VALUE + 1),
//                "Adding an out-of-bounds value should throw IllegalArgumentException");
//        assertThrows(IllegalArgumentException.class, () -> customSet.add(Integer.MIN_VALUE - 1),
//                "Adding an out-of-bounds value should throw IllegalArgumentException");
//    }
}