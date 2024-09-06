package com.leetcode.tree;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.LinkedList;
import java.util.Queue;

@Log4j2
@Getter
public class TreeNode {

    int value;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        value = x;
    }


    public static void visit(TreeNode root) {
        log.info("parent:{}", root.value);
    }

    public static void bfs(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        // offer：如果插入成功，返回 true；如果队列已满，无法插入，则返回 false。
        // 对于基于容量的队列（如 ArrayBlockingQueue），如果队列已满，该方法不会抛出异常，而是返回 false。
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            visit(node);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    /**
     * 最大深度递归
     *
     * @param root
     * @return
     */
    public static int maxDepthTraversal(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDeep = maxDepthTraversal(root.left);
        int rightDeep = maxDepthTraversal(root.right);
        return Math.max(leftDeep, rightDeep) + 1;
    }

    /**
     * 最小深度递归
     *
     * @param root
     * @return
     */
    public static int minDepthTraversal(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDeep = minDepthTraversal(root.left);
        int rightDeep = minDepthTraversal(root.right);
        // 如果左子树或右子树为空，返回不为空的那一侧的深度+1
        if (root.left == null || root.right == null) {
            return Math.min(leftDeep, rightDeep);
        }
        return Math.min(leftDeep, rightDeep) + 1;
    }

    /**
     * 最小深度迭代
     *
     * @param root
     * @return
     */
    public static int minDepthIterative(TreeNode root) {
        int depth = 0;
        if (root == null) {
            return depth;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size(); // 每一层节点数
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null && node.right != null) {
                    queue.offer(node.left);
                    queue.offer(node.right);
                    depth++;
                } else {
                    return depth;
                }
            }
        }

        return depth;
    }

    /**
     * 最大深度迭代
     *
     * @param root
     * @return
     */
    public static int maxDepthIterative(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int depth = 0; //记录二叉树深度

        while (!queue.isEmpty()) {
            int size = queue.size(); // 每一层节点数
            for (int i = 0; i < size; i++) {
                //poll：如果队列不为空，返回队列头部的元素；如果队列为空，则返回 null。
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            depth++;
        }
        return depth;
    }
}
