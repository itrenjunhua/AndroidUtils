package com.renj.common.utils;

import java.text.DecimalFormat;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2018-05-22   17:27
 * <p>
 * 描述：数字处理工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class NumberUtils {

    /**
     * 将字符串强制转换为 int 数据
     */
    public static int valueOfInteger(String numberString) {
        try {
            return Integer.valueOf(numberString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将字符串强制转换为 double 数据
     */
    public static double valueOfDouble(String numberString) {
        try {
            return Double.valueOf(numberString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.00;
        }
    }

    /**
     * 将数字转换为指定格式
     *
     * @param number 数字
     * @param format 格式 如 {@code #0.00} 如果为空，使用默认 {@code #0.00}
     * @return
     */
    public static String numberFormat(double number, String format) {
        DecimalFormat decimalFormat;
        if (StringUtils.isEmpty(format))
            decimalFormat = new DecimalFormat("#0.00");
        else
            decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(number);
    }
}
