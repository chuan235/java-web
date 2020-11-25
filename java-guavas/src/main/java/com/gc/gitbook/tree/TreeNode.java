package com.gc.gitbook.tree;

import com.google.common.collect.BiMap;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class TreeNode {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode l1 = new TreeNode(21);
        TreeNode r1 = new TreeNode(22);
        TreeNode l1l = new TreeNode(31);
        TreeNode l1r = new TreeNode(32);
        TreeNode r1l = new TreeNode(33);
        TreeNode r1r = new TreeNode(34);

        root.left = l1;
        root.right = r1;
        l1.left = l1l;
        l1.right = l1r;
        r1.left = r1l;
        r1.right = r1r;

//        System.out.println(root.isSymmetric(root));
//        root.levelOrder3(root);
        root.bstSequences(root);

    }

    int val;
    TreeNode left, right;

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    // 前序遍历中根节点的索引
    public static final int[] ROOT_INDEX = new int[]{
            0,
            1 << 1,//2
            1 << 2,//4
            1 << 3,//8
            1 << 4,//16
            1 << 5,//32
            1 << 6,//64
            1 << 7,//128
            1 << 8,//256
            1 << 9,//512
            1 << 10,//1024
            1 << 11,//2048
            1 << 12,//4096
            1 << 13,//8192
            1 << 14
    };


    // 剑指 Offer 55 - II. 平衡二叉树
    // 输入一棵二叉树的根节点，判断该树是不是平衡二叉树。
    // 如果某二叉树中任意节点的左右子树的深度相差不超过1，那么它就是一棵平衡二叉树。
    public boolean isBalanced(TreeNode root) {
//        balancedHelper(root, 1);
//        if (maxDeep - minDeep > 1) {
//            return false;
//        }
//        return true;
        if (root == null) return true;
        // 左子树是平衡树 + 右子树是平衡树  + 左右子树的深度差不大于1
        return Math.abs(maxDepth(root.left) - maxDepth(root.right)) <= 1
                && isBalanced(root.left)
                && isBalanced(root.right);
    }


    int minDeep = Integer.MAX_VALUE;
    int maxDeep = Integer.MIN_VALUE;

    public void balancedHelper(TreeNode root, int curDeep) {
        // 前序遍历出结果
        if (root == null) {
            System.out.print("null->");
            return;
        }
        if (root.left == null && root.right == null) {
            minDeep = Math.min(minDeep, curDeep);
        }
        maxDeep = Math.max(maxDeep, curDeep);
        System.out.print(root.val + "->");
        balancedHelper(root.left, curDeep + 1);
        balancedHelper(root.right, curDeep + 1);

    }

    // 剑指 Offer 55 - I. 二叉树的最大深度
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    // 剑指 Offer 28. 对称的二叉树
    public boolean isSymmetric(TreeNode root) {
        // root左边使用中序遍历
        //String inStr = tra(root.left, false);
        // root右边使用后序遍历
        //String postStr = tra(root.right, true);
        // 判断结果是否一致
        //return inStr.equals(postStr);
        // 递归
        if (root == null) return true;
        return metricHelper(root.left, root.right);

    }

    public boolean metricHelper(TreeNode node1, TreeNode node2) {
        // 满足三个条件
        // node1.val = node2.val
        // node1.left.val = node2.right.val
        // node1.right.val = node2.left.val
        if (node1 == null && node2 == null) {
            return true;
        }
        // 直接不成立的情况
        if (node1 == null || node2 == null || node1.val != node2.val) {
            return false;
        }
        // 递归
        return metricHelper(node1.left, node2.right)
                && metricHelper(node1.right, node2.left);
    }

    public String tra(TreeNode root, boolean isPost) {
        if (root == null) return "";
        if (isPost) {
            String right = tra(root.right, isPost);
            String left = tra(root.left, isPost);
            return right + "->" + root.val + "->" + left;
        } else {
            // 中序
            String left = tra(root.left, isPost);
            String right = tra(root.right, isPost);
            return left + "->" + root.val + "->" + right;
        }
    }

    // 剑指 Offer 27. 二叉树的镜像  返回一个二叉树的镜像二叉树
    public TreeNode mirrorTree(TreeNode root) {
        // 前序
        if (root == null) return null;
        TreeNode leftRoot = mirrorTree(root.right);
        TreeNode rightRoot = mirrorTree(root.left);
        root.left = leftRoot;
        root.right = rightRoot;
        return root;
    }

    // 递推公式  max(f(root.left), f(root.right), preTime/2) + root.val
    public double minimalExecTime(TreeNode root) {
        double[] res = execTime(root, 2);
        return res[0];
    }

    public double[] execTime(TreeNode root, int cpu) {
        if (root == null) {
            return new double[]{0, 0};
        }
        // 获取左右子节点的值
        double[] left = execTime(root.left, cpu);
        double[] right = execTime(root.right, cpu);
        // 计算preTime
        double sumTime = left[1] + right[1];
        // 当前节点执行的最小消耗时间
        double minTime = Math.max(Math.max(left[0], right[0]), sumTime / cpu) + root.val;
        return new double[]{minTime, sumTime + root.val};
    }


    /**
     * 剑指 Offer 68 - II. 二叉树的最近公共祖先
     *
     * @param root 根
     * @param p    找到p、q最近的父节点  自己可以作为自己的父节点
     * @param q
     * @return
     */
    public TreeNode lastestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        // 中序  左  中  右
        // p -> 左  q -> 右   root
        // p q -> 左 递归左边
        // p q -> 右 递归右边
        boolean pIsLeft = isLeft(root, p);
        boolean qIsLeft = isLeft(root, q);

        if (pIsLeft && qIsLeft) {
            // left
            return lastestCommonAncestor(root.left, p, q);
        } else if (!pIsLeft && !qIsLeft) {
            // right
            return lastestCommonAncestor(root.right, p, q);
        } else {
            return root;
        }


    }

    // 剑指 Offer 68 - I. 二叉搜索树的最近公共祖先
    public TreeNode preLastestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 前序遍历
        if (root == null || root == p || root == q) return root;
        // 递归左右
        TreeNode left = preLastestCommonAncestor(root.left, p, q);
        TreeNode right = preLastestCommonAncestor(root.right, p, q);
        // 讨论情况
        // 左右都是空，找不到，正常的是不存在
//        if(left == null && right == null) return root;
        // 只有左边是空  此时p q都在right分枝上
        if (left == null) return right;
        // 同理右边是空
        if (right == null) return left;
        // 一左一右 root
        return root;

    }

    /**
     * 判断node是不是root的左子节点
     *
     * @param root
     * @param node
     * @return
     */
    public boolean isLeft(TreeNode root, TreeNode node) {
        if (root == null) return false;
        if (root.val == node.val) return true;
        return isLeft(root.left, node) || isLeft(root.right, node);
    }

    // 剑指 Offer 32 - I. 从上到下打印二叉树
    public void levelOrder(TreeNode root) {
        System.out.print(root.val + "->");
        levelOrderHelper(root.left, root.right);
    }

    public void levelOrderHelper(TreeNode node1, TreeNode node2) {
        if (node1 == null) {
            return;
        }
        if (node2 == null) {
            return;
        }
        System.out.print(node1.val + "->" + node2.val + "->");
        levelOrderHelper(node1.left, node1.right);
        levelOrderHelper(node2.left, node2.right);
    }

    // 剑指 Offer 32 - II. 从上到下打印二叉树 II  每一层是一个数组
    public void levelOrder2(TreeNode root) {
        LinkedList<List<Integer>> list = new LinkedList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.addLast(root);
        while (!queue.isEmpty()) {
            // 初始化
            List<Integer> tmp = new ArrayList<>();
            // 遍历queue
            for (int i = queue.size(); i > 0; i--) {
                // 取出并移除第一位
                TreeNode node = queue.poll();
                // 添加到tmp
                tmp.add(node.val);
                // 左右子节点
                // left,right  [1],[21,22],[31,32,33,34]
                // right,left  [1],[22,21],[34,33,32,31]
                // 左右交替
                if (node.right != null) queue.addLast(node.right);
                if (node.left != null) queue.addLast(node.left);
//              System.out.println("row="+row+", add "+ node.right.val+"  "+ node.left.val);
            }
            // 添加集合
            list.addLast(tmp);
        }
        // 遍历list
        list.stream().forEach(e -> e.stream().forEach(i -> System.out.println(i)));
    }


    // 剑指 Offer 32 - II. 从上到下打印二叉树 III  第一行 从左到右，第二行 从右到左，第三行  左到右 ...
    public void levelOrder3(TreeNode root) {
        LinkedList<List<Integer>> list = new LinkedList<>();
        // 左到右
        LinkedList<TreeNode> queue1 = new LinkedList<>();
        // 右到左
        LinkedList<TreeNode> queue2 = new LinkedList<>();
        queue1.addLast(root);
        queue2.addLast(root);
        int row = 0;
        while (!queue1.isEmpty()) {
            row++;
            List<Integer> tmp = new ArrayList<>();
            for (int i = queue1.size(); i > 0; i--) {
                TreeNode node = queue1.poll();
                TreeNode rightNode = queue2.poll();
                tmp.add(node.val);
                // 当前是奇数行
                if (row % 2 == 0) {
                    if (node.left != null) queue1.addLast(rightNode.left);
                    if (node.right != null) queue1.addLast(rightNode.right);
                } else {
                    // 下一次的顺序
                    if (node.right != null) queue1.addLast(rightNode.right);
                    if (node.left != null) queue1.addLast(rightNode.left);
                }
                if (node.left != null) queue2.addLast(rightNode.left);
                if (node.right != null) queue2.addLast(rightNode.right);
            }
            list.addLast(tmp);
        }
        // 遍历list
        list.stream().forEach(e -> e.stream().forEach(i -> System.out.println(i)));
    }

    // 返回二叉搜索树第K大的节点  右 > 根 > 左  中序遍历的逆序即可
    // 1、遍历的时候计数，统计当前节点的序号
    // 2、递归到第k个节点时，应记录结果res
    // 3、提前终止
    // 定义全局的res和k
    int res, k;

    public int kthLargest(TreeNode root, int k) {
        this.k = k;
        dfs(root);
        return res;
    }

    void dfs(TreeNode root) {
        // 中序逆序
        if (root == null) return;
        dfs(root.right);
        if (k == 0) return;
        if (--k == 0) res = root.val;
        dfs(root.left);

    }

    // dfs
    public int kthDfs(TreeNode root, int k) {
        LinkedList<TreeNode> result = new LinkedList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        if (root == null) return 0;
        while (root != null || !queue.isEmpty()) {
            while (root != null) {
                // 一直向右边找
                queue.addLast(root);
                root = root.right;
            }
            // root == null  可用从queue中取
            TreeNode rightNode = queue.removeLast();
            result.addLast(rightNode);
            // 看看是否存在左节点
            if (rightNode.left != null) root = rightNode.left;
        }
        return result.get(k).val;
    }

    // 剑指 Offer 33. 二叉搜索树的后序遍历序列
    public boolean verifyPostorder(int[] postorder) {
        // 判断数据量少的时候
        if (postorder == null || postorder.length == 1 || postorder.length == 2) {
            return true;
        }
        // 左 右 根  左 < 根 < 右
        // 三指针遍历
        int lt = 0, rt = 1, pt = 2;
        int len = postorder.length;
        boolean res = true;
        // 这种算法只能判断每一个元素都存在的BST，如果第一个元素不是左节点而时右节点  则会出现问题
        while (pt <= len - 1) {
            // 根
            if (postorder[pt] < postorder[lt] || postorder[pt] > postorder[rt]) {
                res = false;
                break;
            }
            // 设置步长
            lt += 2;
            rt += 2;
            pt += 2;

        }
        return res;
    }

    /**
     * 递归算法正确的解
     * 左子树都要小于根，右子树都要大于根 => 后序
     * 左 右  根
     */
    public boolean verifyPostorder2(int[] postOrder) {
        return recur(postOrder, 0, postOrder.length - 1);

    }

    public boolean recur(int[] postOrder, int i, int j) {
        if (i > j) return true;
        // 双指针
        // 左子树
        int p = i;
        while (postOrder[p] < postOrder[j]) p++;
        // 缓存分割点
        int q = p;
        // 右子树
        while (postOrder[p] > postOrder[j]) p++;
        // 递归
        return p == j && recur(postOrder, i, q - 1) && recur(postOrder, q, j - 1);
    }

    // 617. 将两个二叉树合并为一个二叉树
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) return t2;
        if (t2 == null) return t1;
        t1.val += t2.val;
        t1.left = mergeTrees(t1.left, t2.left);
        t1.right = mergeTrees(t1.right, t2.right);
        return t1;
    }

    // 110. 判断一棵树是否是高度平衡的二叉树
    public boolean isBalanced2(TreeNode root) {
        if (root == null) return true;
        // 计算左右高度差
        return Math.abs(height(root.left) - height(root.right)) <= 1
                && isBalanced2(root.left)
                && isBalanced2(root.right);
    }

    public int height(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(height(root.left), height(root.right));
    }

    // leetcode 814. 二叉树剪枝
    public TreeNode pruneTree(TreeNode root) {
        // 左子树全部是0 左子树可以直接减掉
        // 右子树全部是0 右子树可以直接减掉
        if (root == null) return null;
        root.left = pruneTree(root.left);
        root.right = pruneTree(root.right);
        // 后序位置  左右子节点是null 则返回null
        if (root.val == 0 && root.left == null && root.right == null) return null;
        return root;
    }

    // 同上 814 第二种解法  判断子树是否存在1
    public boolean containsOne(TreeNode root) {
        if (root == null) return false;
        boolean left = containsOne(root.left);
        boolean right = containsOne(root.right);
        if (left) root.left = null;
        if (right) root.right = null;
        return root.val == 1 || left || right;
    }

    //leetcode 965. 单值二叉树
    public boolean isUnivalTree(TreeNode root) {
        return univalTreeHelper(root, root.val);
    }

    boolean univalTreeHelper(TreeNode root, int val) {
        if (root == null) return true;
        return root.val == val
                && univalTreeHelper(root.left, val)
                && univalTreeHelper(root.right, val);
    }

    public List<List<Integer>> bstSequences(TreeNode root) {
        // 从左到右遍历  从右到左遍历
        List<List<Integer>> result = new ArrayList<>();
        // 队列和栈
        LinkedList<TreeNode> queue = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        // root
        queue.addLast(root);
        stack.push(root);
        while (!queue.isEmpty()) {
            List<Integer> leftResult = new ArrayList<>();
            List<Integer> rightResult = new ArrayList<>();
            while (!queue.isEmpty()) {
                TreeNode top = stack.pop();
                if (null == top) {
                    rightResult.add(-1);
                    continue;
                }
                rightResult.add(top.val);
                // 入栈 先左后右
                stack.push(top.left);
                stack.push(top.right);

                /** 队列 左->右 **/
                TreeNode first = queue.removeFirst();
                if (first == null) {
                    leftResult.add(-1);
                    continue;
                }
                leftResult.add(first.val);
                queue.addLast(first.left);
                queue.addLast(first.right);
            }
            result.add(leftResult);
            result.add(rightResult);
        }

        return result;
    }

    // 判断两个二叉树是否相等
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        if (root1 == root2) return true;
        if (root1 == null || root2 == null || root1.val != root2.val) return false;
        return flipEquiv(root1.left, root2.left)
                && flipEquiv(root1.right, root2.right)
                || flipEquiv(root1.left, root2.right)
                && flipEquiv(root1.right, root2.left);
    }

    // 第二种方法 1、首先将两棵树都转为标准的BST 2、输入遍历结果查看结果是否一致
    public boolean flipEquiv2(TreeNode root1, TreeNode root2) {
        LinkedList<Integer> queue1 = new LinkedList<>();
        LinkedList<Integer> queue2 = new LinkedList<>();
        flipHelper(root1, queue1);
        flipHelper(root1, queue2);
        return queue1.equals(queue2);
    }

    public void flipHelper(TreeNode root, LinkedList<Integer> queue) {
        if (root != null) {
            queue.addLast(root.val);
            int L = root.left == null ? -1 : root.left.val;
            int R = root.right == null ? -1 : root.right.val;
            if (L > R) {
                // 修改遍历顺序
                flipHelper(root.right, queue);
                flipHelper(root.left, queue);
            } else {
                flipHelper(root.left, queue);
                flipHelper(root.right, queue);
            }
        }
    }

    public int diameterOfBinaryTree(TreeNode root) {
        // 最大的直径必定是  叶子节点到叶子节点   叶子节点->共同的父节点 的高度之和
        // 叶子节点之间  路径的最大值
        // 叶子节点之间的路径 ?  -> 中序遍历  左-根-右
        // 1、找出所有的叶子节点  对应的序列的索引  左右子树不对
        if (root == null) return 0;
        // 左到右的距离
        int height1 = height2(root.left) + height2(root.right);
        int rs = Math.max(height1 + 2, Math.max(diameterOfBinaryTree(root.left), diameterOfBinaryTree(root.right)));

        return rs;
    }

    // 求一棵树的高度
    public int height2(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(height2(root.left), height2(root.right));
    }

    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        // x y把整棵树分为了三部分
        // x及x的子树  y及y的子树  x，y之间的部分  尝试计算这三部分的节点数目
        // 1、找到x节点  计算x及其子树的数量
        int xCount = treeCount(findNode(root, x));
        if (xCount > n / 2) return false;
        // 只要输入的节点 子树 没有超过整个树的一半，则二号用户是肯定可以赢的
        return true;
    }

    // 计算二叉树节点的子树数量
    public int treeCount(TreeNode root) {
        if (root == null) return 0;
        return 1 + treeCount(root.left) + treeCount(root.right);
    }

    public TreeNode findNode(TreeNode root, int val) {
        if (root == null) return null;
        // dfs
        TreeNode rs = null;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.addLast(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.removeLast();
            if (node.val == val) {
                rs = node;
                break;
            }
            if (node.left != null) queue.addLast(node.left);
            if (node.right != null) queue.addLast(node.right);
        }
        return rs;
    }

    // lettcode 99. 恢复二叉搜索树 该树中的两个节点被错误地交换。请在不改变其结构的情况下，恢复这棵树
    public void recoverTree(TreeNode root){

    }

}
