package daa;

public class MinHeap {

    private int[] heap;
    private int size;
    private Metrics metrics;

    public MinHeap(Metrics metrics) {
        heap = new int[10];
        size = 0;
        this.metrics = metrics;
    }

    private void grow() {
        int[] newHeap = new int[heap.length * 2];

        for (int i = 0; i < heap.length; i++) {
            newHeap[i] = heap[i];
            metrics.steps++;
            metrics.moves++;
        }

        heap = newHeap;
    }

    public void insert(int x) {
        if (size == heap.length) {
            grow();
        }

        heap[size] = x;
        bubbleUp(size);
        size++;
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;

            metrics.steps += 2;
            metrics.comparisons++;

            if (heap[parent] <= heap[index]) {
                break;
            }

            int temp = heap[parent];
            heap[parent] = heap[index];
            heap[index] = temp;

            metrics.steps += 2;
            metrics.moves += 2;

            index = parent;
        }
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException();
        }

        metrics.steps++;
        return heap[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException();
        }

        metrics.steps++;
        int min = heap[0];

        if (size == 1) {
            size--;
            return min;
        }

        metrics.steps++;
        heap[0] = heap[size - 1];
        metrics.moves++;

        size--;
        bubbleDown(0);

        return min;
    }

    private void bubbleDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size) {
                metrics.steps += 2;
                metrics.comparisons++;

                if (heap[left] < heap[smallest]) {
                    smallest = left;
                }
            }

            if (right < size) {
                metrics.steps += 2;
                metrics.comparisons++;

                if (heap[right] < heap[smallest]) {
                    smallest = right;
                }
            }

            if (smallest == index) {
                break;
            }

            int temp = heap[index];
            heap[index] = heap[smallest];
            heap[smallest] = temp;

            metrics.steps += 2;
            metrics.moves += 2;

            index = smallest;
        }
    }

    public int size() {
        return size;
    }
}