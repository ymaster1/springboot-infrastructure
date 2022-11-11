package cn.me.ppx.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ym
 * @date ${DATE} ${TIME}
 */
public class Main {
    public static void main(String[] args) {
//        System.out.println(is_int(BigDecimal.valueOf(13.10)));
//        System.out.println(up(BigDecimal.valueOf(45.00)));
//        System.out.println(up(BigDecimal.valueOf(45)));
//        System.out.println(BigDecimal.valueOf(100.0).remainder(BigDecimal.TEN));
//        System.out.println(BigDecimal.valueOf(100).remainder(BigDecimal.TEN));
//        System.out.println(Arrays.toString(BigDecimal.valueOf(100.00).divideAndRemainder(BigDecimal.TEN)));
        System.out.println(BigDecimal.valueOf(100).compareTo(BigDecimal.valueOf(10)));
    }

    /**
     * 判断bigDecimal是否是整数
     *
     * @param bd
     * @return
     */
    public static boolean is_int(BigDecimal bd) {
        return bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
    }

    /**
     * 整数四舍五入
     *
     * @param bigDecimal
     * @return
     */
    public static BigDecimal half_up(BigDecimal bigDecimal) {
        return bigDecimal.movePointLeft(1).setScale(0, RoundingMode.HALF_UP).movePointRight(1);
    }

    /**
     * 整数向上取整
     *
     * @param v
     * @return
     */
    public static BigDecimal up(BigDecimal v) {
        if (v.remainder(BigDecimal.TEN).equals(BigDecimal.ZERO)||v.remainder(BigDecimal.TEN).equals(BigDecimal.valueOf(0.0))) {
            return v;
        } else {
            if (v.compareTo(BigDecimal.TEN) < 0) {
                return BigDecimal.TEN;
            } else {
                return v.movePointLeft(1).setScale(0, RoundingMode.FLOOR).add(BigDecimal.ONE).movePointRight(1);
            }

        }


    }

    public void batchSell(List<Integer> ids, int count, int goodsId, BigDecimal minAmount, BigDecimal maxAmount, String operator) {
        // 浮动值
        BigDecimal floatValue = (maxAmount.subtract(minAmount)).divide(BigDecimal.valueOf(count), 2, BigDecimal.ROUND_HALF_UP);
        // 最低浮动价格
        BigDecimal minFloatValue = floatValue.add(minAmount);
        List<BigDecimal> amountList = new ArrayList<>(count);
        for (int i = 1; i <= count; i++) {
            amountList.add(minFloatValue.add(floatValue.multiply(BigDecimal.valueOf(i))));
        }

    }
}