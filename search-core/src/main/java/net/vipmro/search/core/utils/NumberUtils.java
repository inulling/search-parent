package net.vipmro.search.core.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author fengxiangyang
 * @date 2018/11/30
 */
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {
    /**
     * 保留两位小数 第三位舍去
     *
     * @param d
     * @return
     */
    public static double getDoubleFormat2Down(double d) {
        BigDecimal big = BigDecimal.valueOf(d);
        big = big.setScale(2, BigDecimal.ROUND_DOWN);
        return big.doubleValue();
    }

    /**
     * 保留两位小数 四舍六入五成双
     *
     * @param d
     * @return
     */
    public static double getDoubleFormat2Even(double d) {
        BigDecimal big = BigDecimal.valueOf(d);
        big = big.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return big.doubleValue();
    }

    /**
     * 保留n位小数 四舍六入五成双
     *
     * @param d
     * @param n
     * @return
     */
    public static double getDoubleFormatEven(double d, int n) {
        BigDecimal big = BigDecimal.valueOf(d);
        big = big.setScale(n, BigDecimal.ROUND_HALF_EVEN);
        return big.doubleValue();
    }

    /**
     * 保留整数
     *
     * @param money
     * @return
     */
    public static String getDoubleText(Double money) {
        return new DecimalFormat(",##0").format(money);
    }

    public static String format(double d, String pattern) {
        return new DecimalFormat(pattern).format(d);
    }

    /**
     * 格式化
     * ,##0.00
     *
     * @param money
     * @return
     */
    public static String getMoneyText(Double money) {
        return new DecimalFormat(",##0.00").format(money);
    }

    public static String getMoneyText(String money) {
        return new DecimalFormat("###,##0.00").format(money);
    }

    public static String toString(Number number) {
        if (number == null) {
            return null;
        } else {
            return number.toString();
        }
    }

    public static long add(Long... longs) {
        long count = 0;
        for (Long l : longs) {
            if (l != null) {
                count += l;
            }
        }
        return count;
    }
}
