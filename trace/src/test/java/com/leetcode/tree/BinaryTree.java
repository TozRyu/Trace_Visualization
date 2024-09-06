package com.leetcode.tree;

import org.junit.jupiter.api.Test;

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
        BinaryTreeByIterative.postOrder(tree.root);
    }

}
