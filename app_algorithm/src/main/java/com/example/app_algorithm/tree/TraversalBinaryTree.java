package com.example.app_algorithm.tree;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TraversalBinaryTree {

    public static void main(String[] args) {
        TreeNode root = getTestData();
        printlnArray(traversalBinaryTree(root));
    }

    // 1.之字形打印二叉树，即第一行从左到右打印，第二行从右到左打印；
    // 核心思路：每行的顺序是相反的，用两个栈来存储，
    private static List<List<Integer>> traversalBinaryTree(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        List<Integer> first = new ArrayList<>();
        first.add(root.getValue());
        result.add(first);
        stack1.push(root);

        while (stack1.isEmpty() || stack2.isEmpty()) {
            if (stack1.isEmpty() && stack2.isEmpty()) {
                break;
            }
            List<Integer> temp = new ArrayList<>();
            if (stack2.isEmpty()) {
                while (!stack1.isEmpty()) {
                    TreeNode treeNode = stack1.peek();
                    if (treeNode != null) {
                        if (treeNode.getRightNode() != null) {
                            temp.add(treeNode.getRightNode().getValue());
                            stack2.push(treeNode.getRightNode());
                        }
                        if (treeNode.getLeftNode() != null) {
                            temp.add(treeNode.getLeftNode().getValue());
                            stack2.push(treeNode.getLeftNode());
                        }
                    }
                    stack1.pop();
                }

            } else {
                while (!stack2.isEmpty()) {
                    TreeNode treeNode = stack2.peek();
                    if (treeNode != null) {
                        if (treeNode.getLeftNode() != null) {
                            temp.add(treeNode.getLeftNode().getValue());
                            stack1.push(treeNode.getLeftNode());
                        }
                        if (treeNode.getRightNode() != null) {
                            temp.add(treeNode.getRightNode().getValue());
                            stack1.push(treeNode.getRightNode());
                        }
                    }
                    stack2.pop();
                }
            }
            if (temp.size() > 0) {
                result.add(temp);
            }
        }

        return result;
    }

    private static void printlnArray(List<List<Integer>> source) {
        for (List<Integer> list : source) {
            for (Integer integer : list) {
                System.out.print(integer + ", ");
            }
            System.out.println();
        }
    }

    private static TreeNode getTestData() {
        TreeNode root = new TreeNode(10);
        addNode(root);
        addNode(root.getLeftNode());
        addNode(root.getRightNode());
        addNode(root.getLeftNode().getLeftNode());
        addNode(root.getLeftNode().getRightNode());
        addNode(root.getRightNode().getLeftNode());
        addNode(root.getRightNode().getRightNode());
        return root;
    }

    private static void addNode(TreeNode root) {
        TreeNode left = new TreeNode(7);
        TreeNode right = new TreeNode(9);
        root.setLeftNode(left);
        root.setRightNode(right);
    }
}
