package com.example.app_algorithm;

import java.util.List;

public class ShellSortAlgorithm {

    public static void main(String[] args) {

        int[] source = {10, 8, 9, 2, 30, 18};

        printlnArray(insertSort(source));
        printlnArray(shellSort(source));
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


    private static int[] shellSort(int[] source) {
        if (source == null || source.length == 1) {
            return source;
        }
        int h = 1;
        while (h < source.length / 3) {
            h = 3 * h + 1;  //步长的设定，步长的最后一个值必须是1；
        }

        while (h >= 1) {
            //目的是任意间隔h个数组是有序数组
            for (int i = h; i < source.length; i++) {
                int temp = source[i];
                int j = i - h;
                while (j >= 0 && source[j] > source[i]) {
                    source[j + h] = source[j];
                    j -= h;
                }
                source[j + h] = temp;
            }
            h = h / 3;
        }
        return source;
    }
}
