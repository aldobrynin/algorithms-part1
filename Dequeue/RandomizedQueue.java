import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n;

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        n = 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        ensureNotNull(item);
        if (items.length == n) resize(2 * items.length);
        items[n++] = item;
    }

    public Item dequeue() {
        ensureIsNotEmpty();
        int indexToDequeue = getRandomIndex();
        Item item = items[indexToDequeue];
        if (indexToDequeue != n - 1)
            items[indexToDequeue] = items[n - 1];
        items[n-- - 1] = null;
        if (n > 0 && n == items.length / 4) resize(items.length / 2);
        return item;
    }

    public Item sample() {
        ensureIsNotEmpty();
        return items[getRandomIndex()];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private int getRandomIndex() {
        return StdRandom.uniform(n);
    }

    private void ensureIsNotEmpty() {
        if (isEmpty()) throw new NoSuchElementException();
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            temp[i] = items[i];
        items = temp;
    }


    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] a;
        private int index;

        private RandomizedQueueIterator() {
            a = (Item[]) new Object[n];
            for (int i = 0; i < n; i++)
                a[i] = items[i];
            StdRandom.shuffle(a);
            index = 0;
        }

        public boolean hasNext() {
            return index != a.length;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[index++];
        }
    }

    private void ensureNotNull(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        System.out.println("hey");
    }
}
