package com.renj.utils.number;

import android.support.annotation.NonNull;

import java.math.RoundingMode;
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
     * 将字符串强制转换为 int 数据，已处理 {@link NumberFormatException}
     */
    public static int parseInt(String numberString) {
        try {
            return Integer.parseInt(numberString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将字符串强制转换为 double 数据，已处理 {@link NumberFormatException}
     */
    public static double parseDouble(String numberString) {
        try {
            return Double.parseDouble(numberString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.00;
        }
    }

    /**
     * 保留两位小数,舍弃第二位之后,向下取数据(如果小数后面是无用的0就去除)
     */
    public static String formatDouble(double value) {
        return formatDouble(value, -1);
    }

    /**
     * 保留两位小数(如果小数后面是无用的0就去除)
     *
     * @param roundMode -1:舍弃,向下取数据  0:四舍五入 1:向上取数据
     */
    public static String formatDouble(double value, int roundMode) {
        DecimalFormat df = new DecimalFormat("#.##");
        if (roundMode < 0)
            df.setRoundingMode(RoundingMode.FLOOR);
        else if (roundMode > 0)
            df.setRoundingMode(RoundingMode.CEILING);
        else
            df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(value);
    }

    /**
     * 保留一位小数,舍弃第一位之后,向下取数据(如果小数后面是无用的0就去除)
     */
    public static String formatDoubleSingle(double value) {
        return formatDoubleSingle(value, -1);
    }

    /**
     * 保留一位小数(如果小数后面是无用的0就去除)
     *
     * @param roundMode -1:舍弃,向下取数据  0:四舍五入 1:向上取数据
     */
    public static String formatDoubleSingle(double value, int roundMode) {
        DecimalFormat df = new DecimalFormat("#.#");
        if (roundMode < 0)
            df.setRoundingMode(RoundingMode.FLOOR);
        else if (roundMode > 0)
            df.setRoundingMode(RoundingMode.CEILING);
        else
            df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(value);
    }

    /**
     * 格式化double，保留2为小数 每 3 位加 "," 号分割
     *
     * @param formatNum
     * @return
     */
    public static String formatDoubleComma(@NonNull double formatNum) {
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance();
        decimalFormat.applyPattern("#,###.##");
        //decimalFormat.setGroupingSize(3);
        return decimalFormat.format(formatNum);
    }
}
