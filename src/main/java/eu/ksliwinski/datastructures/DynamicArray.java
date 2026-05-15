package eu.ksliwinski.datastructures;

/**
 * Dynamic array implementation. Replaces java.util.ArrayList
 */
public class DynamicArray<T> {
    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public DynamicArray() {
        this(DEFAULT_CAPACITY);
    }

    public DynamicArray(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative: " + initialCapacity);
        }
        this.elements = new Object[initialCapacity];
        this.size = 0;
    }

    /**
     * Adds element at the end of an array
     * @param element element to be added
     */
    public void add(T element) {
        if (size == elements.length) {
            resize(elements.length * 2);
        }
        elements[size++] = element;
    }

    /**
     * Retrieves an element at a given index
     * @param index index of an element
     * @return element at a given index
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) elements[index];
    }

    /**
     * Replaces an element at a given index
     * @param index index in an array
     * @param element element to be placed at a given index
     */
    public void set(int index, T element) {
        checkIndex(index);
        elements[index] = element;
    }

    /**
     * @return actual count of elements in an array
     */
    public int size() {
        return size;
    }

    /**
     * Checks if an array is empty.
     * @return true if empty, false if not
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears an array
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    private void resize(int newCapacity) {
        if (newCapacity == 0) newCapacity = 1;

        Object[] newElements = new Object[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds. Index: " + index + ", Size: " + size);
        }
    }
}
