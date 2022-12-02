package cn.me.ppx.infrastructure.common.leetcode.arr.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ym
 * 两数之和
 * @date 2022/11/29 11:30
 */
public class Q_1 {
    /**
     * 使用hash表将出现过的值存起来，和当前值进行比较
     * @param arr
     * @param val
     * @return
     */
    public static int[] sum(int[] arr, int val) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(val - arr[i])) {
                return new int[]{map.get(val - arr[i]), i};
            }
            map.put(arr[i], i);
        }
        return new int[0];
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(sum(new int[]{2, 7, 11, 15}, 22)));
    }
}
