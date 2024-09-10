package com.leetcode.tree;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

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


}
