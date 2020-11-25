package com.gc.gitbook.treetest;

import com.gc.gitbook.tree.TreeNode;
import org.junit.Test;

public class TreeTest {

    @Test
    public void testKthDfs(){
        TreeNode root = new TreeNode(30);
        TreeNode l1 = new TreeNode(20);
        TreeNode l1l = new TreeNode(10);
        TreeNode l1r = new TreeNode(22);
        TreeNode r1 = new TreeNode(50);
        TreeNode r1l = new TreeNode(40);
        TreeNode r1r = new TreeNode(60);

        root.setLeft(l1);
        root.setRight(r1);
        l1.setLeft(l1l);
        l1.setRight(l1r);
        r1.setLeft(r1l);
        r1.setRight(r1r);
        System.out.println(root.kthDfs(root, 2));

    }




}
