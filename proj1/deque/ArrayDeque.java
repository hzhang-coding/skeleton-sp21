package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int head;
    private int size;
    private static final double USAGE_RATIO = 0.25;
    private static final int FACTOR = 2;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        head = 0;
        size = 0;
    }

    private void grow() {
        T[] newItems = (T[]) new Object[FACTOR * items.length];
        System.arraycopy(items, head, newItems, 0, items.length - head);
        System.arraycopy(items, 0, newItems, items.length - head, head);
        head = items.length;
        items = newItems;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            grow();
        }
        items[head] = item;
        head = (head + items.length + 1) % items.length;
        ++size;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            grow();
        }
        items[(head + items.length - size - 1) % items.length] = item;
        ++size;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int curr = (head + items.length - 1) % items.length;
        int end = (head + items.length - size - 1) % items.length;
        while (curr != end) {
            System.out.print(items[curr] + " ");
            curr = (curr + items.length - 1) % items.length;
        }
        System.out.println();
    }

    private void shrink() {
        int tail = (head + items.length - size) % items.length;
        T[] newItems = (T[]) new Object[items.length / 2];
        if (head > tail) {
            System.arraycopy(items, tail, newItems, 0, size);
        } else {
            System.arraycopy(items, tail, newItems, 0, items.length - tail);
            System.arraycopy(items, 0, newItems, items.length - tail, head);
        }
        head = size;
        items = newItems;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        head = (head + items.length - 1) % items.length;
        T ret = items[head];
        items[head] = null;
        --size;
        if (items.length >= 16 && (1.0 * size / items.length) < USAGE_RATIO) {
            shrink();
        }
        return ret;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        int tail = (head + items.length - size) % items.length;
        T ret = items[tail];
        items[tail] = null;
        --size;
        if (items.length >= 16 && (1.0 * size / items.length) < USAGE_RATIO) {
            shrink();
        }
        return ret;
    }

    @Override
    public T get(int index) {
        return items[(head + items.length - index - 1) % items.length];
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Deque)) {
            return false;
        }

        Deque<?> other = (Deque<?>) obj;

        if (this.size != other.size()) {
            return false;
        }

        int curr = (head + items.length - 1) % items.length;
        int end = (head + items.length - size - 1) % items.length;
        int i = 0;
        while (curr != end) {
            if (!items[curr].equals(other.get(i))) {
                return false;
            }
            curr = (curr + items.length - 1) % items.length;
            ++i;
        }

        return true;
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int curr;

        ArrayDequeIterator() {
            curr = (head + items.length - 1) % items.length;
        }

        public boolean hasNext() {
            int end = (head + items.length - size - 1) % items.length;
            return curr != end;
        }

        public T next() {
            T ret = items[curr];
            curr = (curr + items.length - 1) % items.length;
            return  ret;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
}
