package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int ptr; // 遍历指针，从 0 开始

        @Override
        public boolean hasNext() {
            return ptr < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            T item = get(ptr);
            ptr++;
            return item;
        }
    }

    @Override
    public boolean equals(Object o) {
        // 1. 自身引用检查
        if (this == o) {
            return true;
        }
        // 2. 非空和类型检查（必须是Deque的实现类）
        if (o == null || !(o instanceof Deque)) {
            return false;
        }
        Deque<?> other = (Deque<?>) o;
        // 3. 大小检查
        if (this.size() != other.size()) {
            return false;
        }
        // 4. 元素逐一比较
        for (int i = 0; i < this.size(); i++) {
            Object thisItem = this.get(i);
            Object otherItem = other.get(i);
            // 处理null元素
            if (thisItem == null && otherItem == null) {
                continue;
            }
            if (thisItem == null || !thisItem.equals(otherItem)) {
                return false;
            }
        }
        return true;
    }

    private static final int RESIZE_CAPACITY = 16;

    private T[] items;
    private int size;
    private int capacity;
    private int front;
    private int rear;

    public ArrayDeque() {
        capacity = 8;
        items = (T[]) new Object[capacity];
        size = 0;
        front = 0;
        rear = 0;
    }

    @Override
    public void addFirst(T item) {
        if (size == capacity) {
            resize(capacity * 2);
        }
        front = (front - 1 + capacity) % capacity;
        items[front] = item;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (size == capacity) {
            resize(capacity * 2);
        }
        items[rear] = item;
        rear = (rear + 1) % capacity;
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; ++i) {
            int actualIndex = (front + i) % capacity;
            System.out.print(items[actualIndex] + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T item = items[front];
        front = (front + 1) % capacity;
        size -= 1;
        if (capacity > RESIZE_CAPACITY && size < capacity / 4) {
            resize(capacity / 2);
        }
        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        rear = (rear - 1 + capacity) % capacity;
        T item = items[rear];
        size -= 1;
        if (capacity > RESIZE_CAPACITY && size < capacity / 4) {
            resize(capacity / 2);
        }
        return item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int actualIndex = (front + index) % capacity;
        return items[actualIndex];
    }

    private void resize(int newCapacity) {
        T[] newItems = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; ++i) {
            int oldIndex = (front + i) % capacity;
            newItems[i] = items[oldIndex];
        }
        items = newItems;
        front = 0;
        rear = size;
        capacity = newCapacity;
    }

}
