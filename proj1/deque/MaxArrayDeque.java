package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> c;
    public MaxArrayDeque(Comparator<T> c) {
        this.c = c;
    }

    public T max() {
        return max(c);
    }

    public T max(Comparator<T> comparator) {
        if (isEmpty()) {
            return null;
        }
        int size = size();
        T ret = get(0);
        for (int i = 1; i < size; ++i) {
            if (comparator.compare(ret, get(i)) < 0) {
                ret = get(i);
            }
        }
        return ret;
    }
}
