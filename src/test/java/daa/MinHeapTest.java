package daa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {

    @Test
    public void testInsertAndPeekMin() {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(metrics);

        heap.insert(20);
        heap.insert(10);
        heap.insert(30);
        heap.insert(5);

        assertEquals(4, heap.size());
        assertEquals(5, heap.peekMin());
    }

    @Test
    public void testExtractMin() {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(metrics);

        heap.insert(20);
        heap.insert(5);
        heap.insert(15);
        heap.insert(10);

        assertEquals(5, heap.extractMin());
        assertEquals(10, heap.peekMin());
        assertEquals(3, heap.size());
    }

    @Test
    public void testDuplicates() {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(metrics);

        heap.insert(5);
        heap.insert(5);
        heap.insert(10);

        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(10, heap.extractMin());
    }

    @Test
    public void testOneElement() {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(metrics);

        heap.insert(42);

        assertEquals(42, heap.peekMin());
        assertEquals(42, heap.extractMin());
        assertEquals(0, heap.size());
    }

    @Test
    public void testEmptyHeap() {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(metrics);

        assertThrows(IllegalStateException.class, heap::peekMin);
        assertThrows(IllegalStateException.class, heap::extractMin);
    }

    @Test
    public void testNonDecreasingOrder() {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(metrics);

        int[] values = {30, 5, 20, 1, 15, 8, 40, 3};

        for (int value : values) {
            heap.insert(value);
        }

        int previous = heap.extractMin();

        while (heap.size() > 0) {
            int current = heap.extractMin();

            assertTrue(previous <= current);

            previous = current;
        }
    }

    @Test
    public void testGrowth() {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(metrics);

        for (int i = 30; i >= 0; i--) {
            heap.insert(i);
        }

        assertEquals(31, heap.size());

        for (int expected = 0; expected <= 30; expected++) {
            assertEquals(expected, heap.extractMin());
        }
    }
}