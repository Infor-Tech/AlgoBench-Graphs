package eu.ksliwinski.models;

public class EdgeMinHeap {
    private final Edge[] heap;
    private int size;

    public EdgeMinHeap(int capacity) {
        this.heap = new Edge[capacity];
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(Edge edge) {
        heap[size] = edge;
        bubbleUp(size);
        size++;
    }

    public Edge extractMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        Edge min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        bubbleDown(0);
        return min;
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap[index].weight() >= heap[parentIndex].weight()) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    private void bubbleDown(int index) {
        while (index < size) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;

            if (leftChild < size && heap[leftChild].weight() < heap[smallest].weight()) {
                smallest = leftChild;
            }
            if (rightChild < size && heap[rightChild].weight() < heap[smallest].weight()) {
                smallest = rightChild;
            }
            if (smallest == index) {
                break;
            }
            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int i, int j) {
        Edge temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}
