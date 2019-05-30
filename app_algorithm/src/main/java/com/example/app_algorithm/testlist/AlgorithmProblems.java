package com.example.app_algorithm.testlist;


import com.example.app_algorithm.ListNote;
import com.example.app_algorithm.tree.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class AlgorithmProblems {

    //面试题库https://mp.weixin.qq.com/s/CQABJNacnsf8_s6l93JKUw

    //https://github.com/CyC2018/CS-Notes

    public static void main(String[] args) {
        print1ToMaxOfNDigits(2);
    }


    private static void printlnList(List<Integer> list) {
        for (Integer integer : list) {
            System.out.print(integer + ", ");
        }
    }


    private static TreeNode resetBuildTree(int[] preOrder, int[] inOrder) {
        Map<Integer, Integer> indexForOrders = new HashMap<>();
        for (int i = 0; i < inOrder.length; i++) {
            indexForOrders.put(inOrder[i], i);
        }
        return buildTreeNode(indexForOrders, preOrder, 0, preOrder.length - 1, 0);
    }

    private static TreeNode buildTreeNode(Map<Integer, Integer> indexForOrders, int[] preOrder, int preL, int preR, int inL) {
        if (preL > preR || indexForOrders == null) {
            return null;
        }
        TreeNode node = new TreeNode(preOrder[preL]);
        int inIndex = indexForOrders.get(node.getValue());

        int leftSize = inIndex - inL;
        node.setLeftNode(buildTreeNode(indexForOrders, preOrder, preL + 1, preL + leftSize, inL));
        node.setRightNode(buildTreeNode(indexForOrders, preOrder, preL + leftSize + 1, preR, inL + leftSize + 1));
        return node;
    }


    private static int teset8(int n) {
        if (n < 2) {
            return n;
        }
        int[] fn = new int[n + 1];
        fn[0] = 0;
        fn[1] = 1;
        for (int i = 2; i < n + 1; i++) {
            fn[i] = fn[i - 1] + fn[i - 2];
        }
        return fn[n];
    }

    private static int teset9(int n) {
        if (n < 2) {
            return n;
        }
        int pre2 = 0;
        int pre1 = 1;
        int fn = 0;
        for (int i = 2; i < n + 1; i++) {
            fn = pre1 + pre2;
            pre2 = pre1;
            pre1 = fn;
        }
        return fn;
    }

    private static int rest10(int n) {
        int[] fn = new int[n];
        Arrays.fill(fn, 1);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                fn[i] = fn[i] + fn[j];
            }
        }
        return fn[n - 1];
    }

    public static void print1ToMaxOfNDigits(int n) {
        if (n <= 0)
            return;
        char[] number = new char[n];
        print1ToMaxOfNDigits(number, 0);
    }

    private static void print1ToMaxOfNDigits(char[] number, int digit) {
        if (digit == number.length) {
            printNumber(number);
            return;
        }
        for (int i = 0; i < 10; i++) {
            number[digit] = (char) (i + '0');
            print1ToMaxOfNDigits(number, digit + 1);
        }
    }

    private static void printNumber(char[] number) {
        int index = 0;
        while (index < number.length && number[index] == '0')
            index++;
        while (index < number.length)
            System.out.print(number[index++]);
        System.out.println();
    }

    public ListNote deleteNode(ListNote head, ListNote tobeDelete) {
        if (head == null || tobeDelete == null) {
            return null;
        }

        if (head == tobeDelete) {
            head = null;
        } else {
            ListNote cur = head;
            while (cur.getNext() != tobeDelete) {
                cur = cur.getNext();
            }
            cur.setNext(null);
        }
        return head;
    }

    public ListNote deleteDuplication(ListNote head) {
        if (head == null || head.getNext() == null) {
            return null;
        }
        ListNote next = head.getNext();
        if (head.getValue() == next.getValue()) {
            while (next != null && head.getValue() == next.getValue()) {
                next = next.getNext();
            }
            return deleteDuplication(next);
        } else {
            head.setNext(deleteDuplication(next));
            return head;
        }
    }


    //EventBus
    //Rxjava
    //网络框架Retrofit
    //图片框架
    //绘制流程
    //事件分发流程
    //内存优化
    //ANR的原理
    //Http的缓存

    // final的作用:
    // 1.被final修饰的类不可以被继承;
    // 2.被final修饰的方法不可用被重写；
    // 3.被final修饰的变量不可用被改变（不可改变的是变量的引用，不是变量执行的内容）


    /**
     * 类加载的过程：加载-链接-初始化
     * 加载：找到类，对齐class文件的二进制流到jvm虚拟机，存储到方法区，构造java.lang.class对象实例，双亲委托的加载方式
     * 链接：验证：验证数据结构是否正确：格式验证，语义分析，操作验证等。
     *      准备：为类中的所以静态变量初始化默认值
     *      解析：将常量池中的符号引用转换为直接引用
     * 初始化：类的静态变量赋值为指定的值。执行静态代码块
     * <p>
     * 类什么时候被加载：当程序使用时发现类没有被加载到内存中时会加载
     */


    /**
     * View平移的多种方式
     * 1.offsetLeftAndRight()与offsetTopAndBottom()
     * 2.LayoutParams
     * 3.scrollTo与scrollBy
     * 4.layout
     * 5.动画
     */

    /**
     * java.lang.StackOverflowError和java.lang.OutOfMemoryError
     */


    /**
     * 1.网络架构，取消网络请求
     * 2.n个数其中n-1个数相同
     * 3.项目架构cc
     * 4.界面刷新
     * 5.okHttp源码，多个网络请求会怎样
     * 6.链表相交问题
     * 7.类加载
     * 8.listView动画，缓存机制
     * 9.RecyclerView使用
     * 10.反射问题
     * 11.找出数组最大子集
     * 12 Activity onPause onStop
     *
     */







}
