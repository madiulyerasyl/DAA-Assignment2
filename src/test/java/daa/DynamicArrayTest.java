package daa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicArrayTest {

    @Test
    public void testAddAndGet() {
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(metrics);

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(3, array.size());
        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }

    @Test
    public void testAddAtIndex() {
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(metrics);

        array.add(10);
        array.add(30);
        array.add(1, 20);

        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }

    @Test
    public void testRemove() {
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(metrics);

        array.add(10);
        array.add(20);
        array.add(30);

        int removed = array.remove(1);

        assertEquals(20, removed);
        assertEquals(2, array.size());
        assertEquals(10, array.get(0));
        assertEquals(30, array.get(1));
    }

    @Test
    public void testContainsAndDuplicates() {
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(metrics);

        array.add(5);
        array.add(10);
        array.add(5);

        assertTrue(array.contains(5));
        assertTrue(array.contains(10));
        assertFalse(array.contains(100));
    }

    @Test
    public void testFirstAndLastIndex() {
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(metrics);

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(10, array.get(0));
        assertEquals(30, array.get(array.size() - 1));
    }

    @Test
    public void testInvalidIndex() {
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(metrics);

        assertThrows(IndexOutOfBoundsException.class, () -> array.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(0));
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(1, 10));
    }

    @Test
    public void testGrowth() {
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(metrics);

        for (int i = 0; i < 25; i++) {
            array.add(i);
        }

        assertEquals(25, array.size());

        for (int i = 0; i < 25; i++) {
            assertEquals(i, array.get(i));
        }
    }
}