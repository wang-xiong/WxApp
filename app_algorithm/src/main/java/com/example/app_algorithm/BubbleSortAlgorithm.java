package com.example.app_algorithm;

public class BubbleSortAlgorithm {

    public static void main(String[] args) {
        int[] source = {10, 8, 9, 2, 30, 18};
        printlnArray(bubbleSort(source));
        printlnArray(bubbleSort2(source));
    }

    private static void printlnArray(int[] source) {
        for (int num : source) {
            System.out.print(num + "; ");
        }
        System.out.println();
        System.out.println("--------");
    }

    //冒泡排序核心思路：从后向前冒泡，每一次冒泡确定一个位置（从前到后的位置）
    private static int[] bubbleSort(int[] source) {
        //1.冒泡的次数，最后一个位置不需要确定，所以需要冒泡次数：length - 1次
        for (int i = 0; i < source.length - 1; i++) {
            //2.每次冒泡的方式，从后向前，比较大小，如果小交互位置，需要比较的次数是j>i。即当前需要确定位置的泡前一个位置，i代码此处冒泡需要确定的位置。
            for (int j = source.length - 1; j > i; j--) {
                if (source[j] < source[j - 1]) {
                    int temp = source[j];
                    source[j] = source[j - 1];
                    source[j - 1] = temp;
                }
            }
        }
        return source;
    }

    //冒泡排序核心思路：从前想后冒泡，每一次冒泡确定一个位置（从前到后的位置）
    private static int[] bubbleSort2(int[] source) {
        //1.冒泡的次数，最后一个位置不需要确定，所以需要冒泡次数：length - 1次
        for (int i = 0; i < source.length - 1; i++) {
            //2.每次冒泡的方式，从前向后，比较大小，如果小交互位置，需要比较的次数是j>i。即当前需要确定位置的泡前一个位置，i代码此处冒泡需要确定的位置。
            for (int j = 0; j < source.length - 1 - i; j++) {
                if (source[j] > source[j + 1]) {
                    int temp = source[j];
                    source[j] = source[j + 1];
                    source[j + 1] = temp;
                }
            }
        }
        return source;
    }
}
