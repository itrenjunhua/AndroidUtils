package com.renj.utils.res;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

import com.renj.utils.collection.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2018-11-12   15:52
 * <p>
 * 描述：操作字符串的工具类，包含对字符串的常用操作
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class StringUtils {
    /**
     * 判断字符串是否为空
     *
     * @param value 需要判断的字符串
     * @return 如果为 "" 、"null"、{@code null} 返回 true，否则 返回 false
     */
    public static boolean isEmpty(String value) {
        return null == value || "".equals(value) || "null".equals(value);
    }

    /**
     * 判断多个字符串是否为空
     *
     * @param args 需要判断的字符串
     * @return 如果有一个为空，则返回true，只有全部不为空才返回false
     */
    public static boolean isEmpty(String... args) {
        if (null == args) return true;
        if (0 == args.length) return true;

        for (String arg : args) {
            if (isEmpty(arg)) return true;
        }
        return false;
    }

    /**
     * 字符串不为空
     */
    public static boolean notEmpty(String value) {
        return !isEmpty(value);
    }

    /**
     * 判断多个字符串是否不为空
     *
     * @param args 需要判断的字符串
     * @return 如果有一个为空，则返回false，只有全部不为空才返回true
     */
    public static boolean notEmpty(String... args) {
        if (null == args) return false;
        if (0 == args.length) return false;

        for (String arg : args) {
            if (isEmpty(arg)) return false;
        }
        return true;
    }

    /**
     * 判断多个字符串是否相等
     *
     * @param args 需要判断的字符串数组
     * @return 如果其中有一个为空字符串或者null，则返回false，只有全相等才返回true
     */
    public static boolean isEquals(String... args) {
        if (args == null) return false;
        if (args.length <= 1) return false;
        String last = args[0];
        if (isEmpty(last)) return false;
        for (int i = 1; i < args.length; i++) {
            String str = args[i];
            if (isEmpty(str)) return false;
            if (!str.equals(last)) return false;
            last = str;
        }
        return true;
    }

    /**
     * 对 "" 、"null"、{@code null} 进行处理，返回 ""，否则返回 原字符串
     *
     * @param value 需要处理的字符串
     * @return 返回 "" 或者 原字符串
     */
    public static String handlerString(String value) {
        if (isEmpty(value)) return "";
        return value;
    }

    /**
     * 添加下划线
     */
    public static SpannableString underLine(String str) {
        SpannableString spannableString = new SpannableString(str);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableString.setSpan(underlineSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 返回一个高亮spannable
     *
     * @param content 全部文本内容
     * @param color   高亮颜色
     * @param start   起始高亮位置
     * @param end     结束高亮位置
     * @return 高亮spannable
     */
    public static CharSequence getHighLightText(String content, int color, int start, int end) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        start = Math.max(start, 0);
        end = Math.min(end, content.length());
        if (start >= end) return "";

        SpannableString spannable = new SpannableString(content);
        CharacterStyle span = new ForegroundColorSpan(color);
        spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    /**
     * 修改金额不同位置的字体大小和颜色
     *
     * @param priceStr   价格原始字符串
     * @param isHasFlag  是否有 ¥ 标记
     * @param flagSize   ¥ 标记字体大小
     * @param flagColor  ¥ 标记字体颜色
     * @param intSize    整数部分字体大小
     * @param intColor   整数部分字体颜色
     * @param floatSize  小数部分字体大小
     * @param floatColor 小数部分字体颜色
     * @return
     */
    public static CharSequence getMoneyStyle(String priceStr, boolean isHasFlag,
                                             int flagSize, int flagColor,
                                             int intSize, int intColor,
                                             int floatSize, int floatColor) {
        if (isEmpty(priceStr)) return "";

        List<SpannableStyleModule> spannableStyleModules = new ArrayList<>();
        if (isHasFlag) {
            spannableStyleModules.add(SpannableStyleModule.createSizeColorModule(0, 1, flagSize, flagColor));
        }

        if (priceStr.contains(".")) {
            String[] strings = priceStr.split("\\.");
            int intStart = isHasFlag ? 1 : 0;
            int intEnd = intStart + strings[0].length();
            spannableStyleModules.add(SpannableStyleModule.createSizeColorModule(intStart, intEnd, intSize, intColor));

            int floatEnd = priceStr.length();
            spannableStyleModules.add(SpannableStyleModule.createSizeColorModule(intEnd, floatEnd, floatSize, floatColor));
        } else {
            int intStart = isHasFlag ? 1 : 0;
            int intEnd = priceStr.length();
            spannableStyleModules.add(SpannableStyleModule.createSizeColorModule(intStart, intEnd, intSize, intColor));
        }
        return getStringSpannable(priceStr, spannableStyleModules);
    }

    /**
     * 修改文字不同位置的字体大小和颜色
     *
     * @return
     */
    public static CharSequence getStringSpannable(String content, List<SpannableStyleModule> spannableStyleModules) {
        if (isEmpty(content)) return "";
        if (ListUtils.isEmpty(spannableStyleModules)) return content;

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(content);
        for (SpannableStyleModule spannableStyleModule : spannableStyleModules) {
            spannableStyleModule.start = Math.max(spannableStyleModule.start, 0);
            spannableStyleModule.end = Math.min(spannableStyleModule.end, content.length());

            if (spannableStyleModule.start >= spannableStyleModule.end) continue;

            if (spannableStyleModule.colorValue != 0) {
                ForegroundColorSpan flagColorSpan = new ForegroundColorSpan(spannableStyleModule.colorValue);
                stringBuilder.setSpan(flagColorSpan, spannableStyleModule.start, spannableStyleModule.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (spannableStyleModule.textSize > 0) {
                AbsoluteSizeSpan flagSizeSpan = new AbsoluteSizeSpan(spannableStyleModule.textSize, true);
                stringBuilder.setSpan(flagSizeSpan, spannableStyleModule.start, spannableStyleModule.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (spannableStyleModule.boldText) {
                StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
                stringBuilder.setSpan(styleSpan, spannableStyleModule.start, spannableStyleModule.end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }
        return stringBuilder;
    }

    /**
     * 格式化手机或电话号码
     *
     * @param number 号码
     * @param type   0：11位手机号码；1 其他,每四位加一个分割符
     * @param symbol 中间分隔符
     */
    @NonNull
    public static String formatNumber(@NonNull String number, int type, @NonNull String symbol) {
        if (type == 0) {
            //手机号，在最前面加一个空格
            number = " " + number.trim();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < number.length(); i++) {
            sb.append(number.charAt(i));
            if (i % 4 == 0) {
                sb.append(symbol);
            }
        }
        if (number.length() % 4 == 0) {
            String str = sb.toString();
            return str.substring(0, str.length() - 1).trim();
        }
        return sb.toString().trim(); // trim掉可能是手机号的空格
    }

    /**
     * 将字符串中的 symbol 内容去除
     *
     * @param numberStr 字符串
     * @param symbol    需要去除的元素
     */
    public static String replaceAll(@NonNull String numberStr, @NonNull String symbol) {
        if (isEmpty(numberStr)) return "";

        return numberStr.replaceAll(symbol, "");
    }

    public static class SpannableStyleModule {
        public int start;
        public int end;
        public int textSize;
        public int colorValue;
        public boolean boldText;

        private SpannableStyleModule(int start, int end, int textSize, int colorValue, boolean boldText) {
            this.start = start;
            this.end = end;
            this.textSize = textSize;
            this.colorValue = colorValue;
            this.boldText = boldText;
        }

        public static SpannableStyleModule createSizeModule(int start, int end, int textSize) {
            return createSpannableStyleModule(start, end, textSize, 0, false);
        }

        public static SpannableStyleModule createSizeModule(int start, int end, int textSize, boolean boldText) {
            return createSpannableStyleModule(start, end, textSize, 0, boldText);
        }

        public static SpannableStyleModule createColorModule(int start, int end, int colorValue) {
            return createSpannableStyleModule(start, end, 0, colorValue, false);
        }

        public static SpannableStyleModule createColorModule(int start, int end, int colorValue, boolean boldText) {
            return createSpannableStyleModule(start, end, 0, colorValue, boldText);
        }

        public static SpannableStyleModule createSizeColorModule(int start, int end, int textSize, int colorValue) {
            return createSpannableStyleModule(start, end, textSize, colorValue, false);
        }

        public static SpannableStyleModule createSizeColorModule(int start, int end, int textSize, int colorValue, boolean boldText) {
            return createSpannableStyleModule(start, end, textSize, colorValue, boldText);
        }

        public static SpannableStyleModule createSpannableStyleModule(int start, int end, int textSize, int colorValue, boolean boldText) {
            return new SpannableStyleModule(start, end, textSize, colorValue, boldText);
        }
    }
}
