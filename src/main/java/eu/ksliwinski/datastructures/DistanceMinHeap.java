package eu.ksliwinski.datastructures;

public class DistanceMinHeap {
    public static class Node {
        public int vertex;
        public long distance;

        public Node(int vertex, long distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }

    private final Node[] heap;
    private int size;

    public DistanceMinHeap(int capacity) {
        this.heap = new Node[capacity];
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(int vertex, long distance) {
        heap[size] = new Node(vertex, distance);
        bubbleUp(size);
        size++;
    }

    public Node extractMin() {
        if (isEmpty()) throw new IllegalStateException("Heap is empty");

        Node min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        bubbleDown(0);

        return min;
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap[index].distance >= heap[parentIndex].distance) {
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

            if (leftChild < size && heap[leftChild].distance < heap[smallest].distance) {
                smallest = leftChild;
            }
            if (rightChild < size && heap[rightChild].distance < heap[smallest].distance) {
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
        Node temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}
