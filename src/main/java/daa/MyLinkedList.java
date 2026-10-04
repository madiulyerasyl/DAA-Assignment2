package daa;

public class MyLinkedList {

    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private Metrics metrics;

    public MyLinkedList(Metrics metrics) {
        head = null;
        tail = null;
        size = 0;
        this.metrics = metrics;
    }

    public void add(int x) {
        Node newNode = new Node(x);

        if (head == null) {
            head = newNode;
            tail = newNode;
            metrics.moves += 2;
        } else {
            tail.next = newNode;
            tail = newNode;
            metrics.moves += 2;
        }

        size++;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
            metrics.steps++;
        }

        return current.data;
    }

    public boolean contains(int x) {
        Node current = head;

        while (current != null) {
            metrics.comparisons++;

            if (current.data == x) {
                return true;
            }

            current = current.next;
            metrics.steps++;
        }

        return false;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == size) {
            add(x);
            return;
        }

        Node newNode = new Node(x);

        if (index == 0) {
            newNode.next = head;
            head = newNode;
            metrics.moves += 2;
            size++;
            return;
        }

        Node current = head;

        for (int i = 0; i < index - 1; i++) {
            current = current.next;
            metrics.steps++;
        }

        newNode.next = current.next;
        current.next = newNode;
        metrics.moves += 2;

        size++;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            int removed = head.data;
            head = head.next;
            metrics.moves++;

            size--;

            if (size == 0) {
                tail = null;
                metrics.moves++;
            }

            return removed;
        }

        Node current = head;

        for (int i = 0; i < index - 1; i++) {
            current = current.next;
            metrics.steps++;
        }

        Node removedNode = current.next;
        int removed = removedNode.data;

        current.next = removedNode.next;
        metrics.moves++;

        if (removedNode == tail) {
            tail = current;
            metrics.moves++;
        }

        size--;
        return removed;
    }

    public int size() {
        return size;
    }
}