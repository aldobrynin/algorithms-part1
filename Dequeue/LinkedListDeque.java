import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListDeque<Item> implements Iterable<Item> {
    private Node head;
    private Node tail;
    private int n;

    public LinkedListDeque() {
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        ensureNotNull(item);
        Node newNode = new Node(item);
        newNode.next = head;
        if (head != null)
            head.prev = newNode;
        if (n == 1) {
            newNode.next = tail;
            tail.prev = newNode;
        }
        head = newNode;
        if (n == 0) tail = newNode;
        n++;
    }

    public void addLast(Item item) {
        ensureNotNull(item);
        Node newNode = new Node(item);
        if (tail != null) tail.next = newNode;
        newNode.prev = tail;
        if (n == 1) {
            newNode.prev = head;
            head.next = newNode;
        }
        tail = newNode;
        if (n == 0) head = newNode;
        n++;
    }

    public Item removeFirst() {
        ensureNotEmpty();

        Item item = head.data;
        Node newHead = head.next;
        head = head.next;
        if (newHead != null) newHead.prev = null;
        n--;
        if (n == 0) tail = null;
        return item;
    }

    public Item removeLast() {
        ensureNotEmpty();
        Item item = tail.data;
        Node newTail = tail.prev;
        tail = tail.prev;
        if (newTail != null) newTail.next = null;
        n--;
        if (n == 0) head = null;
        return item;
    }

    private void ensureNotNull(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
    }

    private void ensureNotEmpty() {
        if (isEmpty())
            throw new NoSuchElementException();
    }

    private class Node {
        private final Item data;
        private Node prev;
        private Node next;

        private Node(Item data) {
            this.data = data;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.data;
            current = current.next;
            return item;
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> deque = new LinkedListDeque<Integer>(); //
        for (int i = 0; i < 16; i++)
            deque.addLast(i);
        for (int i = 0; i < 16; i++)
            System.out.println(deque.removeFirst());
        for (int i : deque)
            System.out.println(i);
    }
}
