package com.leetcode.tree;

/**
 * DFS 和 BFS 是非常重要的两种算法，大家一定要掌握，本文为了方便讲解，
 * 只对树做了 DFS，BFS，大家可以试试如果用图的话该怎么写代码，原理其实也是一样，只不过图和树两者的表示形式不同而已，
 * DFS 一般是解决连通性问题，而 BFS 一般是解决最短路径问题，
 * 之后有机会我们会一起来学习下并查集，Dijkstra, Prism 算法等，敬请期待！
 */
public class BinaryTree {

    TreeNode root;

    // 插入节点
    public void insert(int val) {
        root = insertRec(root, val);
    }

    private TreeNode insertRec(TreeNode root, int val) {
        if (root == null) {
            root = new TreeNode(val);
            return root;
        }
        if (val < root.value) {
            root.left = insertRec(root.left, val);
        } else if (val > root.value) {
            root.right = insertRec(root.right, val);
        }
        return root;
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.insert(50);
        tree.insert(30);
        tree.insert(20);
        tree.insert(40);
        tree.insert(70);
        tree.insert(60);
        tree.insert(80);

    }


}
