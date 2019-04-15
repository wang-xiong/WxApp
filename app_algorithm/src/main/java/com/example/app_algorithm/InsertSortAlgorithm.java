package com.example.app_algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InsertSortAlgorithm {

    public static void main(String[] args) {

        int[] source = {10, 8, 9, 2, 30, 18};

        printlnArray(insertSort(source));
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


    private static int[] insertSort(int[] source) {
        //核心思路：已经排序好的数据、未排序好的数据。将未排序好的数据依次插入排序好的数据，
        //每次从未排好的队列排队里第一个，拉出来依次和已经排序的数据进行比较，当大于待插入的数据，向后移动排序好的数据，
        //直到没有比待插入数据大的数据，即找到了插入的位置，插入数据。比喻：排队里边从前到后排队，选定一个人，比此人个子高依次后移，最后空出来的位置就是此人位置。

        //1.默认第一个为排序好的，i代表未排序好的第一个数据，所以i的范围为：1到length-1，总插入次数为length-1
        for (int i = 1; i < source.length; i++) {
            //2.保存未排序好数据的第一个数据，作为插入的数据；
            int temp = source[i];
            int j = i - 1;
            while (j >= 0 && source[j] > temp) {
                //3.比较排序好数据，当大于待插入数据，相互移动当前数据
                source[j + 1] = source[j];
                j--;
            }
            //4.最后空出来的位置，就是待插入数据的位置。
            source[j + 1] = temp;
        }
        return source;
    }



    private static int[] insertSort2(int[] a) {
        if (a == null || a.length < 2) {
            return a;
        }
        //核心思路：已经排序好的数据、未排序好的数据。将未排序好的数据依次插入排序好的数据；
        //插入过程：遍历已排序的数据，交换比当前插入数据大的数据，将交换后的数据代替插入数据。比喻：排队里边从前到后排队，每次从未排好的队列排队里第一个，依次和排好的对人，从前到后比较互换位置
        //1.i代表此处插入的元素的原来位置，默认第一个为排好序的，所以i的范围为：1到length-1，总插入次数为length-1
        for (int i = 1; i < a.length; i++) {
            //2.遍历排序好的数据，从前向后遍历
            for (int j = 0; j < i; j++) {
                //如果当前数据比待插入数据大，和待插入数据交互位置，成为新的插入数据
                if (a[j] > a[i]) {
                    int temp = a[j];
                    a[j] = a[i];
                    a[i] = temp;
                }
            }
        }
        return a;
    }

    //正常不这样比，正常用Collection集合直接比较了。
    private static List<Integer> insertSort(List<Integer> source) {
        if (source == null || source.size() < 2) {
            return source;
        }
        for (int i = 0; i < source.size(); i++) {
            for (int j = i + 1; j < source.size(); j++)
                if (source.get(j) > source.get(i)) {
                    int iNum = source.remove(i);
                    int jNum = source.remove(j - 1);
                    source.add(i, jNum);
                    source.add(j, iNum);
                }
        }
        return source;
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
