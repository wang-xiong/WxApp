package com.example.app_algorithm.sort;

public class SelectionSortAlgorithm {

    public static void main(String[] args) {
        int[] source = {10, 8, 9, 2, 30, 18};
        printlnArray(selectSort(source));
    }

    private static int[] selectSort(int[] source) {
        if (source == null || source.length == 1) {
            return source;
        }

        for (int i = 0; i < source.length - 1; i++) {
            int min = i;
            //遍历找到最小的数据的位置
            for (int j = i + 1; j < source.length; j++) {
                if (source[j] < source[i]) {
                    min = j;
                }
            }
            //交互最小的数据到当前为排序数据的第一个位置
            if (min != i) {
                int temp = source[i];
                source[i] = source[min];
                source[min] = temp;
            }
        }
        return source;
    }

    private static void printlnArray(int[] source) {
        for (int num : source) {
            System.out.print(num + "; ");
        }
        System.out.println();
        System.out.println("--------");
    }
}
