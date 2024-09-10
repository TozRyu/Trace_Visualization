package com.leetcode.tree;

import lombok.extern.log4j.Log4j2;

import java.util.LinkedList;
import java.util.Stack;

/**
 *     1
 *    / \
 *   2   5
 *  / \ / \
 * 3  4 6  7
 * DFS 一条道走到黑 1→2→3 走到头再回头→2 所以是深度优先遍历
 */
@Log4j2
public class DepthFirstSearch {

    // 避免闭环
    boolean[] visited;

    static void preOrderByTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode.visit(root);
        preOrderByTraversal(root.left);
        preOrderByTraversal(root.right);

    }

    static void inOrderByTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrderByTraversal(root.left);
        TreeNode.visit(root);
        inOrderByTraversal(root.right);
    }

    static void postOrderByTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        postOrderByTraversal(root.left);
        postOrderByTraversal(root.right);
        TreeNode.visit(root);
    }

    /**
     * 1.复制二叉树
     * 2.路径规划 一些游戏开发或路径规划算法中，前序遍历有助于先访问起始点，接着探索所有可能的路径。这种遍历方式有助于实现类似深度优先搜索（DFS）的行为
     * 3.查找文件
     * 4. 序列化和反序列化 在一些二叉树的序列化和反序列化算法中，前序遍历是一种常见的方式。使用前序遍历可以将树结构转化为线性数据结构，并能通过相应的反序列化过程重建树结构。
     * 5. 二叉树构造问题  在给定二叉树的前序遍历和中序遍历序列的情况下，可以唯一确定一棵二叉树。
     * 因此，前序遍历在二叉树构造问题中是非常重要的。例如，假设给定一棵树的前序遍历和中序遍历序列，利用前序遍历可以首先确定根节点，然后通过中序遍历分割出左右子树，再递归构建整棵树。
     * 6. 树的先序打印 从上至下打印
     *
     * @param root
     */
    static void preOrderByIterative(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            TreeNode.visit(node);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    /**
     * 有序的，得到的节点值序列是按从小到大的顺序排列的。
     *    4
     *   / \
     *  2   6
     * / \ / \
     * 1  3 5  7  (1.二叉搜索树（BST）的排序输出)
     * 2.二叉搜索树(BST)的验证
     * 3.找到BST中第K大或小的元素
     * 4. 平衡二叉搜索树的构建
     * 5. 将二叉搜索树转换为排序的双向链表
     *         4
     *        / \
     *       2   6
     *      / \ / \
     *     1  3 5  7
     *       /
     *      8
     * 输出 1 2 8 3 4 5 6 7
     *
     * @param root
     */
    static void inOrderByIterative(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            TreeNode.visit(current);
            current = current.right;
        }
    }

    /**
     * 应用场景：
     * 1.删除二叉树时，可以确保先删除子节点，再删除根节点，避免内存泄漏。
     * 2.计算树的高度：可以通过递归地计算左右子树的高度，然后将其最大值加一作为当前节点的高度。只有在子节点高度确定后，才能确定父节点的高度，因此后序遍历非常适合。
     * 3.计算树中每个节点的子节点数：同样，先计算子节点的数量，然后再累加到父节点上，后序遍历保证了在计算父节点之前子节点的值已知。
     * 4. 资源释放和内存管理 与删除二叉树同理，后序遍历可以确保在释放父节点之前，先释放子节点所占用的资源，从而避免内存泄漏或非法访问。
     * 6. 文件系统遍历删除
     * 7. 构建抽象语法树（AST）
     * 在编译器设计中，后序遍历常用于遍历抽象语法树（AST），以便生成代码或执行其他语义分析。后序遍历可以确保所有子表达式已经被处理，然后再处理包含它们的父表达式。
     *
     * @param root
     */
    static void postOrderByIterative(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        LinkedList<Integer> result = new LinkedList<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.addFirst(node.value);
            TreeNode.visit(node);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        result.forEach(System.out::println);
    }

}
