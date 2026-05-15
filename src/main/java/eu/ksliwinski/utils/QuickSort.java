package eu.ksliwinski.utils;

import eu.ksliwinski.datastructures.DynamicArray;
import eu.ksliwinski.models.Edge;

public class QuickSort  {
    public static void sort(DynamicArray<Edge> arr) {
        if (arr == null || arr.size() <= 1) return;
        quickSort(arr, 0, arr.size() - 1);
    }

    private static void quickSort(DynamicArray<Edge> arr, int left, int right) {
        if (left >= right) return;
        int m = partition(arr, left, right);
        quickSort(arr, left, m);
        quickSort(arr, m + 1, right);
    }

    private static int partition(DynamicArray<Edge> arr, int left, int right) {
        Edge pivot = arr.get((left + right) / 2);
        int pivotWeight = pivot.weight();

        int l = left, r = right;
        while(true) {
            while(arr.get(l).weight() < pivotWeight) l++;
            while(arr.get(r).weight() > pivotWeight) r--;
            if(l < r) {
                Edge temp = arr.get(l);
                arr.set(l, arr.get(r));
                arr.set(r, temp);
                l++;
                r--;
            } else {
                if(r == right) r--;
                return r;
            }
        }
    }
}
