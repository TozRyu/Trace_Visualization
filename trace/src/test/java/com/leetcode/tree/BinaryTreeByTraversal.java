package com.leetcode.tree;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class BinaryTreeByTraversal {


    static void preOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode.visit(root);
        preOrder(root.left);
        preOrder(root.right);

    }

    static void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        TreeNode.visit(root);
        inOrder(root.right);
    }

    static void postOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        postOrder(root.left);
        postOrder(root.right);
        TreeNode.visit(root);
    }

}
