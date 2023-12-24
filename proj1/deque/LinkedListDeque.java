package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private static class ListNode<T> {
        private T item;
        private ListNode<T> prev;
        private ListNode<T> next;

        ListNode(T item, ListNode<T> prev, ListNode<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private final ListNode<T> sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new ListNode<>(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        ListNode<T> firstNode = sentinel.next;
        sentinel.next = new ListNode<>(item, sentinel, firstNode);
        firstNode.prev = sentinel.next;
        ++size;
    }

    @Override
    public void addLast(T item) {
        ListNode<T> lastNode = sentinel.prev;
        sentinel.prev = new ListNode<>(item, lastNode, sentinel);
        lastNode.next = sentinel.prev;
        ++size;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        ListNode<T> curr = sentinel.next;
        while (curr != sentinel) {
            System.out.print(curr + " ");
            curr = curr.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        T ret = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        --size;
        return ret;
    }

    @Override
    public T removeLast() {
        if (sentinel.prev == sentinel) {
            return null;
        }
        T ret = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        --size;
        return ret;
    }

    @Override
    public T get(int index) {
        ListNode<T> curr = sentinel;
        while (curr.next != sentinel && index >= 0) {
            curr = curr.next;
            --index;
        }
        if (index >= 0) {
            return null;
        }
        return curr.item;
    }

    public T getRecursive(int index) {
        return getRecursiveHelper(sentinel, index);
    }

    private T getRecursiveHelper(ListNode<T> curr, int index) {
        if (index < 0) {
            return curr.item;
        } else if (curr.next == sentinel) {
            return null;
        }
        return getRecursiveHelper(curr.next, index - 1);
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

        ListNode<T> curr = sentinel.next;
        int i = 0;
        while (curr != sentinel) {
            if (!curr.item.equals(other.get(i))) {
                return false;
            }
            curr = curr.next;
            ++i;
        }

        return true;
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private ListNode<T> node;

        LinkedListDequeIterator() {
            node = sentinel;
        }

        public boolean hasNext() {
            return node.next != sentinel;
        }

        public T next() {
            T ret = node.next.item;
            node = node.next;
            return  ret;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }
}
