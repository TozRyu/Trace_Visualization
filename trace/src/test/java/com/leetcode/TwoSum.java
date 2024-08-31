package com.leetcode;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.tck.ObservationRegistryAssert;

import java.lang.reflect.Method;
import java.util.HashMap;

public class TwoSum {
    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        twoSum(nums, target);
    }

    public static int[] better(int[] nums, int target) {
        int[] indexs = new int[2];
        HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (hash.containsKey(nums[i])) {
                indexs[0] = i;
                indexs[1] = hash.get(nums[i]);
                return indexs;
            }
            // 将数据存入 key为补数 ，value为下标
            hash.put(target - nums[i], i);
        }
        return indexs;
    }

    public static void twoSum(int[] nums, int target) {
        int n1 = 0;
        int n2 = 0;

        for (int num : nums) {
            if (num < target && n1 == 0) {
                n1 = num;
            } else if (num < target && n1 + num == target) {
                n2 = num;
                break;
            }
        }
        System.out.println(n1 + " " + n2);
    }

}
