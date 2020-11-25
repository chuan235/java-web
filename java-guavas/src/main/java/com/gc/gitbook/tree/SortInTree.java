package com.gc.gitbook.tree;

/**
 * 排序算法与二叉树的关系
 */
public class SortInTree {


    // 快速排序  与  前序遍历
    public void quickSort(int[] nums, int low, int high) {
        if (low >= high) return;
        // 对 low-high 进行排序
        int p = partition(nums, low, high);
        // 进行递归
        quickSort(nums, 0, p - 1);
        quickSort(nums, p, high);
    }

    /**
     * 挖坑法
     * 1、取出第一个作为坑
     * 2、从后向前遍历，如果遇到比这个坑要小的，则用这个小的来填这个坑，它自己的位置就空出来了
     * 3、从前向后遍历，如果遇到比这个坑要大的，则用这个大的去填上次从后向前生成的那个坑
     * 重复2、3步骤
     * 最后把最开时取出的萝卜放到low的坑位上并返回low位置的索引，进行分割排序
     * 比low小的都在low的左边
     * 比low大的都在low的右边
     *  分开进行递归
     */
    private int partition(int[] nums, int low, int high) {
        int tmp = nums[low];
        while (low < high) {
            while (low < high && nums[high] >= tmp) {
                high--;
            }
            // 如果high的元素小于tmp，需要high的元素给到low
            nums[low] = nums[high];
            while (low < high && nums[low] <= tmp) {
                low++;
            }
            // 如果low的元素大于tmp，需要low的元素给到high
            nums[high] = nums[low];
        }
        nums[low] = tmp;
        return low;
    }


    // 归并排序  与  后序遍历
    public void mergeSort(int[] nums, int low, int high) {
        if (low >= high) return;
        int mid = (low + high) / 2;
        mergeSort(nums, low, mid);
        mergeSort(nums, mid + 1, high);
        // 合并
        merge(nums, low, mid, high);
    }

    // 合并 low-mid  mid-high 数组形成新数组   新数组在替换源数组
    private void merge(int[] nums, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = 0;
        int p1 = low;
        int p2 = mid + 1;
        // 比较左右两边的数组
        while (p1 <= mid && p2 <= high) {
            temp[i++] = nums[p1] < nums[p2] ? nums[p1++] : nums[p2++];
        }
        // 合并完成之后，查询是否存在剩余元素
        while (p1 <= mid){
            temp[i++] = nums[p1++];
        }
        while (p2 <= high){
            temp[i++] = nums[p2++];
        }
        // 将最终的排序结果给到源数组
        for (int j = 0; j < temp.length; j++) {
            nums[low+i] = temp[i];
        }
    }

    private void swap(int[] nums, int a, int b) {
        int tmp = nums[a];
        nums[a] = nums[b];
        nums[b] = tmp;
    }

}
