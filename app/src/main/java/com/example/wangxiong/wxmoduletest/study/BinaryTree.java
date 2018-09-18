package com.example.wangxiong.wxmoduletest.study;

import java.util.Stack;

public class BinaryTree {

    /**
     * 1、二叉树，每个节点最多有两个子树，左子树，右子树
     * 2、满二叉树：一颗深度为K,且有2^k-1个节点的二叉树，称为满二叉树
     * 3、完全二叉树：倒数第二层是满二叉树，最下层叶子结点一定集中在左 部连续位置，一棵具有N个节点的二叉树的结构与满二叉树的前N个节点的结构相同
     * 4、二叉搜索树（二叉查找树，二叉排序树）：若它的左子树不空，则左子树上所有结点的值均小于它的根结点的值；
     * 若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值；它的左、右子树也分别为二叉排序树
     *
     * 二叉树遍历：
     * 1、前序遍历：先访问根结点，再先序遍历左子树，最后再先序遍历右子树即根—左—右。
     * 2、中序遍历：先中序遍历左子树，然后再访问根结点，最后再中序遍历右子树即左—根—右。
     * 3、后续遍历：先后序遍历左子树，然后再后序遍历右子树，最后再访问根结点即左—右—根。
     *
     */


    ////注意必须逆序建立，先建立子节点，再逆序往上建立，因为非叶子结点会使用到下面的节点，而初始化是按顺序初始化的，不逆序建立会报错
    public Node initNode() {
        Node J = new Node(8, null, null);
        Node H = new Node(4, null, null);
        Node G = new Node(2, null, null);
        Node F = new Node(7, null, J);
        Node E = new Node(5, H, null);
        Node D = new Node(1, null, G);
        Node C = new Node(9, F, null);
        Node B = new Node(3, D, E);
        Node A = new Node(6, B, C);
        return A;
    }

    private void printNode(Node node) {
        System.out.print(node.getData());
    }

    /**
     * 1、递归的方法
     * 2、栈的方法
     */

    /**
     * 先序遍历，NLR
     *
     * @param root
     */
    public void theFirstTraversal(Node root) {
        printNode(root);
        if (root.getLeftNode() != null) {
            theFirstTraversal(root.getLeftNode());
        }
        if (root.getRightNode() != null) {
            theFirstTraversal(root.getRightNode());
        }
    }

    /**
     * 中序遍历 LNR
     *
     * @param root
     */
    public void theInOrderTraversal(Node root) {
        if (root.getLeftNode() != null) {
            theFirstTraversal(root.getLeftNode());
        }
        printNode(root);
        if (root.getRightNode() != null) {
            theFirstTraversal(root.getRightNode());
        }
    }

    /**
     * 后续遍历 LRN
     *
     * @param root
     */
    public void thePostOrderTraversal(Node root) {
        if (root.getLeftNode() != null) {
            theFirstTraversal(root.getLeftNode());
        }
        if (root.getRightNode() != null) {
            theFirstTraversal(root.getRightNode());
        }
        printNode(root);
    }

    public static void test() {
        BinaryTree tree = new BinaryTree();
        Node root = tree.initNode();
        System.out.print("先序遍历");
        tree.theFirstTraversal(root);
        System.out.println("");
        System.out.println("中序遍历");
        tree.theInOrderTraversal(root);
        System.out.println("");
        System.out.println("后序遍历");
        tree.thePostOrderTraversal(root);
        System.out.println("");
    }

    public void theFirstTraversal_Stack(Node root) {
        Stack<Node> stack = new Stack<>();
        Node node = root;
        while (node != null || stack.size() > 0) {
            if (node != null) {
                printNode(node);  //压栈前先访问
                stack.push(node);
                node = node.getLeftNode();
            } else {
                node = stack.pop();
                node = node.getRightNode();
            }
        }
    }

    public void theInOrderTraversal_Stack(Node root) {
        Stack<Node> stack = new Stack<>();
        Node node = root;
        while (node != null || stack.size() > 0) {
            if (node != null) {
                stack.push(node);
                node = node.getLeftNode();
            } else {
                node = stack.pop();
                printNode(node);   //出栈后访问
                node = node.getRightNode();
            }
        }
    }

    public void thePostOrderTraversal_Stack(Node root) {
        Stack<Node> stack = new Stack<>();
        Stack<Node> output = new Stack<>();
        Node node = root;
        while (node != null || stack.size() > 0) {
            if (node != null) {
                output.push(node);
                stack.push(node);
                node = node.getRightNode();
            } else {
                node = stack.pop();
                node = node.getLeftNode();
            }
        }

        while (output.size() >0) {
            printNode(output.pop());
        }
    }


    public static void test2() {
        BinaryTree tree = new BinaryTree();
        Node root = tree.initNode();
        System.out.println("先序遍历");
        tree.theFirstTraversal_Stack(root);
        System.out.println("");
        System.out.println("中序遍历");
        tree.theInOrderTraversal_Stack(root);
        System.out.println("");
        System.out.println("后序遍历");
        tree.thePostOrderTraversal_Stack(root);
        System.out.println("");
    }
}
