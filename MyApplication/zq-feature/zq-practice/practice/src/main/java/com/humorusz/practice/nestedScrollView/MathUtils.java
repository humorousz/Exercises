package com.humorusz.practice.nestedScrollView;

/**
 * Created by zhangzhiquan on 2017/11/14.
 */

class MathUtils {

    static int constrain(int amount, int low, int high) {
        return amount < low ? low : (amount > high ? high : amount);
    }

    static float constrain(float amount, float low, float high) {
        return amount < low ? low : (amount > high ? high : amount);
    }

}
