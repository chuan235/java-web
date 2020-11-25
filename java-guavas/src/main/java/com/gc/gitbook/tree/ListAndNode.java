package com.gc.gitbook.tree;

import org.junit.Test;

import java.util.*;

public class ListAndNode {

    @Test
    public void test2str() {
        TreeNode root = new TreeNode(1);
        TreeNode l1 = new TreeNode(2);
        TreeNode l2 = new TreeNode(4);
        TreeNode l3 = new TreeNode(5);
        TreeNode r1 = new TreeNode(3);
        root.left = l1;
        root.right = r1;
        l1.right = l2;
        l2.left = l3;

//        System.out.println(root.tree2Str(root));
        System.out.println(root.serialize1(root));
    }

    // 两大基本数据结构

    // 1、数组
    // 队列 栈 图 矩阵 散列表  哈希表  堆
    // 2、链表
    // 队列 栈 图 树(二叉搜索树、AVL树、红黑树、区间树、B树...)

    // 数据结构 ->  增删改查

    // 数组类  =>   遍历+根据索引取值

    // 链表 => 迭代 和 递归
    class ListNode {
        int val;
        ListNode next;

        void traverse(ListNode head) {
            // 迭代
            for (ListNode p = head; p != null; p = p.next) {
            }
        }

        void traverse2(ListNode head) {
            // 递归
            traverse2(head.next);
        }
    }

    // 树
    class TreeNode {
        int val;
        TreeNode left, right;
        // n叉树
        TreeNode[] children;
        /**
         * 同级的下一个节点
         * 1
         * 2  ->  3
         * 2.next => 3
         */
        TreeNode next;

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode() {
        }

        // lettcode 226. 翻转一颗二叉树
        public void invertTree(TreeNode root) {
            if (root == null) return;
            // 交换左右节点
            TreeNode right = root.right;
            root.right = root.left;
            root.left = right;

            invertTree(root.left);
            invertTree(root.right);
        }

        // lettcode 114. 二叉树展开为链表
        public void flatten(TreeNode root) {
            if (root == null) return;
            flatten(root.left);
            flatten(root.right);
            // 后续遍历位置
            TreeNode left = root.left;
            TreeNode right = root.right;
            // 将左子节点给到右节点
            root.right = left;
            root.left = null;
            // 将右子节点串到root右节点的最后面
            TreeNode p = root;
            while (p != null) {
                p = p.right;
            }
            p.right = right;

        }

        public void flatten1(TreeNode root) {
            LinkedList<TreeNode> list = new LinkedList<>();
            preorderTraversal(root, list);
            int size = list.size();
            for (int i = 1; i < size; i++) {
                TreeNode prev = list.get(i - 1), curr = list.get(i);
                prev.left = null;
                prev.right = curr;
            }
        }

        public void preorderTraversal(TreeNode root, LinkedList<TreeNode> result) {
            if (root == null) return;
            result.add(root);
            // 前序遍历
            preorderTraversal(root.left, result);
            preorderTraversal(root.right, result);
        }


        // lettcode 654. 最大二叉树
        public TreeNode constructMaximumBinaryTree(int[] nums) {
            return build(nums, 0, nums.length - 1);
        }

        public TreeNode build(int[] nums, int low, int high) {
            // base case
            if (low > high) return null;
            // 计算最大值
            int maxVal = 0, maxIndex = Integer.MIN_VALUE;
            for (int i = low; i <= high; i++) {
                if (maxVal < nums[i]) {
                    maxVal = nums[i];
                    maxIndex = i;
                }
            }
            TreeNode root = new TreeNode(maxVal);
            root.left = build(nums, low, maxIndex);
            root.right = build(nums, maxIndex, high);
            return root;
        }

        // lettcode 105.从前序与中序遍历结果 构造二叉树
        public TreeNode buildTree(int[] preOrder, int[] inOrder) {
            // 缓存inOrder
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < inOrder.length; i++) {
                map.put(inOrder[i], i);
            }
            return buildHelper(preOrder, 0, preOrder.length - 1,
                    inOrder, 0, inOrder.length - 1, map);
        }

        /**
         * pre  1,2,5,4,6,7,3,8,9
         * in   5,2,6,4,7 ,1, 8,3,9
         *
         * @return
         */
        public TreeNode buildHelper(int[] preOrder, int preSlow, int preHigh,
                                    int[] inOrder, int inSlow, int inHigh, HashMap<Integer, Integer> inMap) {
            // 根节点
            int rootVal = preOrder[preSlow];
            // 根节点在中序遍历中的索引
            int index = inMap.get(rootVal);
//            for (int i = inSlow; i <= inHigh; i++) {
//                if (inOrder[i] == rootVal) {
//                    index = i;
//                }
//            }
            // 根据左子树的节点总数 推导出前序遍历的左子树端
            // 怎么去顶左子树的数量?  中序遍历中  根节点的索引算出来
            int leftSize = index - inSlow;
            // 递归构建树
            TreeNode root = new TreeNode(rootVal);
            // 递归左子树
            root.left = buildHelper(preOrder, preSlow + 1, preSlow + leftSize,
                    inOrder, inSlow, index - 1, inMap);
            // 递归右子树
            root.right = buildHelper(preOrder, preSlow + leftSize + 1, preHigh,
                    inOrder, index + 1, inHigh, inMap);
            return root;
        }


        // lettcode 106.从中序与后序遍历结果 构造二叉树

        /**
         * 中序遍历 inorder = [9,3,15,20,7]
         * 后序遍历 postorder = [9,15,7,20,3]
         * https://mp.weixin.qq.com/s/OlpaDhPDTJlQ5MJ8tsARlA
         *
         * @return
         */
        public TreeNode buildTreeNode(int[] inOrder, int[] postOrder) {
            return buildTreeNodeHepler(inOrder, 0, inOrder.length - 1, postOrder, 0, postOrder.length - 1);
        }

        public TreeNode buildTreeNodeHepler(int[] inOrder, int inStart, int inEnd,
                                            int[] postOrder, int postStart, int postEnd) {
            // 根的值
            int rootVal = postOrder[postEnd];
            // 找根在中序中的位置
            int index = 0; //inMap.get(rootVal);
            for (int i = inStart; i <= inEnd; i++) {
                if (inOrder[i] == rootVal) {
                    index = i;
                }
            }
            // 左子树的数量
            int leftSize = index - inStart;
            // 构建树
            TreeNode root = new TreeNode(rootVal);
            root.left = buildTreeNodeHepler(inOrder, inStart, index - 1,
                    postOrder, postStart, postStart + leftSize - 1);
            root.left = buildTreeNodeHepler(inOrder, index + 1, inEnd,
                    postOrder, postStart + leftSize, postEnd - 1);
            return root;
        }

        // 记录所有的子树
        HashSet<String> allSubTree = new HashSet<>();
        HashMap<String, Integer> allSubTreeFreq = new HashMap<>();
        // 记录存在重复的子树
        HashSet<String> duplicateSubTreeStr = new HashSet<>();
        HashSet<TreeNode> duplicateSubTree = new HashSet<>();

        // lettcode 652. 寻找重复的子树
        public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
            findDuplicateSubTreeStr(root);
            List<TreeNode> rs = new LinkedList<>(duplicateSubTree);
            return rs;
        }

        public String findDuplicateSubTreeStr(TreeNode root) {
            if (root == null) {
                return "#";
            }
            String left = serialize(root.left);
            String right = serialize(root.right);
            String treeStr = left + "," + right + "," + root.val;
            Integer freq = allSubTreeFreq.getOrDefault(treeStr, 0);
            if (freq == 1) {
                // 第二次出现
                duplicateSubTree.add(root);
            }
            allSubTreeFreq.put(treeStr, freq + 1);
//            if (allSubTree.contains(treeStr)){
//                // 存在重复的
//                duplicateSubTreeStr.add(treeStr);
//            }else{
//                allSubTree.add(treeStr);
//            }
            return treeStr;
        }

        public String serialize(TreeNode root) {
            if (root == null) {
                return "#";
            }
            String left = serialize(root.left);
            String right = serialize(root.right);
            return left + "," + right + "," + root.val;
        }

        /**
         * 前序遍历
         * 根    左     右
         * 中序遍历
         * 左    根     右
         * 后序遍历
         * 左    右     根
         * 3
         * /     \
         * 9       20
         * / \     /  \
         * 1   10	15   7
         * <p>
         * 前  3 9 1 10 20 15 7
         * 中  1 9 10 3 15 20 7
         * 后  1 10 9 15 7 20 3
         * 1(2(4()())())(3()())
         * 1(2(4))(3)
         * leetcode 606. 根据二叉树创建字符串
         */
        public String tree2Str(TreeNode root) {
            if (root == null) {
                return "";
            }
            if (root.left == null && root.right == null) {
                return root.val + "";
            }
            if (root.right == null) {
                return root.val + "(" + tree2Str(root.left) + ")";
            }
            String left = tree2Str(root.left);
            String right = tree2Str(root.right);
            return root.val + "(" + left + ")" + "(" + right + ")";
        }

        // Lettcode 449. 序列化和反序列化二叉搜索树
        public String serialize1(TreeNode root) {
            if (root == null) {
                return "-1";
            }
            String left = serialize1(root.left);
            String right = serialize1(root.right);
            // 前序序列化实现 根-左-右
//            return root.val + "," + left + "," + right;
            // 中序  左-根-右
//            return left + ","+ root.val + "," + right;
            // 后序  左-右-根
            return left + "," + right + "," + root.val;
        }

        // 前序反序列化
        public TreeNode preDeserialize(String data) {
            String[] dataStrs = data.split(",");
            LinkedList<Integer> list = new LinkedList<>();
            // 从头遍历构建
            for (String s : dataStrs) {
                list.addLast(Integer.parseInt(s));
            }
            return preDeserializeHelper(list);
        }

        public TreeNode preDeserializeHelper(LinkedList<Integer> data) {
            if (data.isEmpty()) return null;
            Integer first = data.removeFirst();
            if (first.equals(-1)) return null;
            TreeNode root = new TreeNode(first);
            root.left = preDeserializeHelper(data);
            root.right = preDeserializeHelper(data);
            return root;
        }

        public TreeNode postDeserializeHelper(LinkedList<Integer> data) {
            if (data.isEmpty()) return null;
            // 最后一个节点是头节点
            Integer val = data.removeLast();
            if (val.equals(-1)) return null;
            TreeNode root = new TreeNode(val);
            root.right = postDeserializeHelper(data);
            root.left = postDeserializeHelper(data);
            return root;
        }

        // lettcode 116.填充每一个节点的下一个右侧节点指针
        public void connect(TreeNode root) {
            if (root == null) return;
            // 将每一层二叉树节点连接起来
            connectTwoNode(root.left, root.right);
        }

        // 将两个二叉树节点串联起来
        public void connectTwoNode(TreeNode node1, TreeNode node2) {
            if (node1 == null || node2 == null) return;
            // 前序遍历
            node1.next = node2;
            /**
             *          root
             *   node1   ->     node2
             * n1l -> n1r -> n2l -> n2r
             *
             */
            connectTwoNode(node1.left, node1.right);
            connectTwoNode(node1.right, node2.left);
            connectTwoNode(node2.left, node2.right);

        }

        // 计算二叉树的节点总数
        public int count(TreeNode root) {
            if (root == null) return 0;
            return 1 + count(root.left) + count(root.right);
        }

        // leetcode 100.相同的树
        public boolean isSameTree(TreeNode root1, TreeNode root2) {
            // 两颗都是空树
            if (root1 == null && root2 == null) return true;
            // 其中一颗是空树，则不相同
            if (root1 == null || root2 == null) return false;
            // 两颗都不是空树，且值相等
            if (root1.val == root2.val) return true;
            // 递归
            return isSameTree(root1.left, root2.left)
                    && isSameTree(root1.right, root2.right);
        }


        // leetcode 98. 验证一棵树是不是BST
        boolean isValidBST(TreeNode root, int min, int max) {
            // 左子树都要比当前节点小  右子树都要比当前节点大
            if (root == null) return true;
            if (root.left != null && root.left.val < min) return true;
            if (root.right != null && root.right.val > max) return true;
            return isValidBST(root.left, min, root.val)
                    && isValidBST(root.right, root.val, max);
        }


        // letcode 700. 找到节点
        TreeNode findNode(TreeNode root, int val) {
            if (root == null) return null;
            if (root.val == val) return root;
            if (root.val < val) {
                // 找左边
                return findNode(root.left, val);
            } else {
                // 找右边
                return findNode(root.right, val);
            }
        }

        // lettcode 701 插入一个节点
        TreeNode insertVal(TreeNode root, TreeNode parent, int val, boolean isleft) {
            // 找到插入节点的父节点
            if (root == null) {
                TreeNode node = new TreeNode(val);
                if (isleft)
                    parent.left = node;
                else
                    parent.right = node;
                // 需要在这里插入节点
                return node;
            }
            // 找到要插入的节点
            if (root.val == val) {
                // 一般不插入相同的节点
                return root;
            }
            if (val < root.val) {
                return insertVal(root.left, root, val, true);
            } else {
                return insertVal(root.right, root, val, false);
            }
        }

        // lettcode 450. 删除一个节点
        boolean deleteNode(TreeNode root, TreeNode parent, int val, boolean isLeft) {
            if (root.val == val) {
                // 删除逻辑  1、删除的节点没有任何子节点  2、有一个子节点  3、存在左右子树
                TreeNode replaceNode = null;
                if (root.left == null) {
                    replaceNode = root.right;
                } else if (root.right == null) {
                    replaceNode = root.left;
                } else {
                    // 左右子树都不为空
                    // 获取左子树的最大值或者右子树的最小值
                    replaceNode = removeMinNode(root.right, root.right);
                    // 重新指定左右子树
                    replaceNode.left = root.left;
                    replaceNode.right = root.right;
                }
                if (isLeft) {
                    parent.left = replaceNode;
                } else {
                    parent.right = replaceNode;
                }

            }
            if (val < root.val) {
                return deleteNode(root.left, root, val, true);
            } else {
                return deleteNode(root.right, root, val, false);
            }
        }

        TreeNode getMin(TreeNode root) {
            if (root.left == null) {
                return root;
            }
            return getMin(root.left);
        }

        TreeNode getMax(TreeNode root) {
            if (root.right == null) {
                return root;
            }
            return getMin(root.right);
        }

        TreeNode removeMinNode(TreeNode root, TreeNode parent) {
            if (root.left == null) {
                // 重置父节点的左子节点
                parent.left = null;
                return root;
            }
            return getMin(root.left);
        }

        void traverse(TreeNode root) {
            // 前序
            traverse(root.left);
            // 中序
            // 后序
            traverse(root.right);
        }

        // n 叉树的遍历
        void traversen(TreeNode root) {
            for (TreeNode child : root.children) {
                traverse(child);
            }
        }

        // 非递归的迭代 前序  根-左-右
        LinkedList<TreeNode> preOrder(TreeNode root) {
            LinkedList<TreeNode> result = new LinkedList<>();
            LinkedList<TreeNode> queue = new LinkedList<>();
            queue.addLast(root);
            while (!queue.isEmpty()) {
                // 循环队列
                TreeNode node = queue.removeFirst();
                result.addLast(node);
                if (node.left != null) queue.addLast(node.left);
                if (node.right != null) queue.addLast(node.right);
            }
            return result;
        }

        // 非递归中序  左-根-右
        LinkedList<TreeNode> inOrder(TreeNode root) {
            LinkedList<TreeNode> result = new LinkedList<>();
            LinkedList<TreeNode> queue = new LinkedList<>();
            if (root == null) return result;
            while (root != null || !queue.isEmpty()) {
                while (root != null) {
                    queue.addLast(root);
                    root = root.left;
                }
                // 到了叶子节点
                TreeNode leftNode = queue.removeLast();
                result.addLast(leftNode);
                // 查看当前的右节点
                if (leftNode.right != null) root = leftNode.right;
            }
            return result;
        }

        // 非递归后续  左-右-根   根-右-左
        public List<TreeNode> postOrder(TreeNode root) {
            List<TreeNode> result = new ArrayList<>();
            LinkedList<TreeNode> queue = new LinkedList<>();
            queue.addLast(root);
            while (!queue.isEmpty()) {
                // 循环队列
                TreeNode node = queue.removeFirst();
                result.add(node);
                if (node.right != null) queue.addLast(node.right);
                if (node.left != null) queue.addLast(node.left);
            }
            // 反转
            Collections.reverse(result);
            return result;
        }
    }


}
