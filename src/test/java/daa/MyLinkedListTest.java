package daa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {

    @Test
    public void testAddAndGet() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    public void testAddAtIndex() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        list.add(10);
        list.add(30);

        list.add(1, 20);
        list.add(0, 5);
        list.add(list.size(), 40);

        assertEquals(5, list.get(0));
        assertEquals(10, list.get(1));
        assertEquals(20, list.get(2));
        assertEquals(30, list.get(3));
        assertEquals(40, list.get(4));
    }

    @Test
    public void testRemove() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);

        assertEquals(10, list.remove(0));
        assertEquals(30, list.remove(1));
        assertEquals(40, list.remove(list.size() - 1));

        assertEquals(1, list.size());
        assertEquals(20, list.get(0));
    }

    @Test
    public void testContainsAndDuplicates() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        list.add(5);
        list.add(10);
        list.add(5);

        assertTrue(list.contains(5));
        assertTrue(list.contains(10));
        assertFalse(list.contains(100));
    }

    @Test
    public void testOneElement() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        list.add(50);

        assertEquals(50, list.get(0));
        assertEquals(50, list.remove(0));
        assertEquals(0, list.size());
    }

    @Test
    public void testInvalidIndex() {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, 10));
    }
}