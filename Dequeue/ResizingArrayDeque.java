import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArrayDeque<Item> implements Iterable<Item> {
    private Item[] items;
    private int head;
    private int tail;
    private int n;

    public ResizingArrayDeque() {
        items = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        ensureNotNull(item);
        if (size() == items.length) resize(items.length * 2);
        head--;
        if (head < 0) head = items.length - 1;
        items[head] = item;
        n++;
    }

    public void addLast(Item item) {
        ensureNotNull(item);
        if (size() == items.length) resize(items.length * 2);
        items[tail] = item;
        tail++;
        if (tail == items.length) tail = 0;
        n++;
    }

    public Item removeFirst() {
        ensureNotEmpty();
        Item item = items[head];
        items[head] = null;
        head = (head + 1) % items.length;
        n--;
        if (n > 0 && n == items.length / 4) resize(items.length / 2);
        return item;
    }

    public Item removeLast() {
        ensureNotEmpty();
        tail--;
        if (tail < 0)
            tail = items.length - 1;
        Item item = items[tail];
        items[tail] = null;
        n--;
        if (n > 0 && n == items.length / 4) resize(items.length / 2);
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

    private void resize(int capacity) {
        int currentSize = size();
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < currentSize; i++)
            copy[i] = items[(head + i) % items.length];
        items = copy;
        head = 0;
        tail = n;
    }

    private class DequeIterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = items[(i + head) % items.length];
            i++;
            return item;
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        ResizingArrayDeque<Integer> deque = new ResizingArrayDeque<Integer>(); //
        for (int i = 0; i < 16; i++)
            deque.addLast(i);
    }
}
