package com.example.app_algorithm.sort;

public class QuickSortAlgorithm {

    public static void main(String[] args) {
        int[] source = {10, 8, 30, 9, 2, 30, 18};
        quickSort(source, 0, source.length - 1);
        printlnArray(source);
    }

    private static void quickSort(int[] source, int left, int right) {
        if (left > right) {
            return;
        }
        int i, j;
        int pivot = source[left];
        i = left;
        j = right;
        while (i != j) {
            while (j > i && source[j] > pivot) { //找到比基准点小于或者等于数的位置
                j--;
            }
            while ((j > i && source[i] <= pivot)) { //找到比基准点大的数的位置
                i++;
            }
            if (i < j) { //交互位置
                int t = source[i];
                source[i] = source[j];
                source[j] = t;
            }
        }
        //交互基准点
        source[left] = source[i];
        source[i] = pivot;
        quickSort(source, left, i - 1);
        quickSort(source, i + 1, right);
    }

    private static void printlnArray(int[] source) {
        for (int num : source) {
            System.out.print(num + "; ");
        }
        System.out.println();
        System.out.println("--------");
    }
}
