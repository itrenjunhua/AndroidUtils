package com.renj.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-11-13   15:52
 * <p>
 * 描述：操作 Bitmap 相关方法
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class BitmapUtils {
    /**
     * 压缩图片文件到指定大小
     *
     * @param filePath    图片文件路径
     * @param maxFileSize 压缩后的最大值
     */
    public static void compressBmpToFile(String filePath, int maxFileSize) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        if (null == bitmap)
            return;

        if (calculateBitmapSize(bitmap) < maxFileSize)
            return;

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 90;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        while (baos.toByteArray().length / 1024 > maxFileSize) {
            baos.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        }
        try {
            final FileOutputStream fos = new FileOutputStream(new File(filePath));
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算bitmap的大小
     *
     * @param bitmap
     * @return bitmap的大小单位KB
     */
    public static float calculateBitmapSize(Bitmap bitmap) {
        if (null == bitmap) {
            return 0.0f;
        }
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        final float size = (baos.toByteArray().length / 1024);
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }
}
