package com.leetcode.tree;

import java.util.*;

import static com.leetcode.tree.TreeNode.visit;

/**
 * BFS具有最短路性质
 * 广度优先遍历也叫层序遍历
 *    1
 *   / \
 *  2   5
 * / \ / \
 * 3  4 6  7
 * 1→2→5→3→4→6→7
 * 深度优先遍历用的是栈  先进后出
 * 而广度优先遍历要用队列来实现  先进先出
 */
public class BreathFirstSearch {


    private static final List<List<Integer>> TRAVERSAL_LIST = new ArrayList<>();

    /**
     * leetcdoe 102: 二叉树的层序遍历, 使用 dfs
     * 这题用 BFS 是显而易见的，但其实也可以用 DFS， 如果在面试中能用 DFS 来处理，会是一个比较大的亮点。
     * DFS 可以用递归来实现，其实只要在递归函数上加上一个「层」的变量即可，只要节点属于这一层，则把这个节点放入相当层的数组里
     *  {@link com.leetcode.tree.BreathFirstSearch#bfsWithBinaryTreeLevelOrderTraversal(com.leetcode.tree.TreeNode)}
     * @param root
     * @return
     */
    private static void dfs(TreeNode root, int level) {
        if (root == null) {
            return;
        }
        if (TRAVERSAL_LIST.size() < level + 1) {
            TRAVERSAL_LIST.add(new ArrayList<>());
        }
        List<Integer> levelList = TRAVERSAL_LIST.get(level);
        levelList.add(root.value);
        // 遍历左结点
        dfs(root.left, level + 1);
        // 遍历右结点
        dfs(root.right, level + 1);
    }

    /**
     * Leetcdoe 102: 二叉树的层序遍历, 使用 bfs
     *
     * @param root
     */
    private static List<List<Integer>> bfsWithBinaryTreeLevelOrderTraversal(TreeNode root) {
        if (root == null) {
            // 根节点为空，说明二叉树不存在，直接返回空数组
            return Arrays.asList();
        }

        // 最终的层序遍历结果
        List<List<Integer>> result = new ArrayList<>();

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            // 记录每一层
            List<Integer> level = new ArrayList<>();
            int levelNum = queue.size();
            // 遍历当前层的节点
            for (int i = 0; i < levelNum; i++) {
                TreeNode node = queue.poll();
                // 队首节点的左右子节点入队,由于 levelNum 是在入队前算的，所以入队的左右节点并不会在当前层被遍历到
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
                level.add(node.value);
            }
            result.add(level);
        }

        return result;
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
     * Leetcode 104: 求树的最大深度
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
     * Leetcode 111: 求树的最小深度
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
