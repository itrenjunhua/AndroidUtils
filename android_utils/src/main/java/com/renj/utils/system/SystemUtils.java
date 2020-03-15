package com.renj.utils.system;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2018-04-04   14:07
 * <p>
 * 描述：打开系统界面，获取系统信息等相关工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class SystemUtils {
    /**
     * 打开系统设置界面
     */
    public static void openSettingActivity(@NonNull Context context, @NonNull String packageName) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", packageName, null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", packageName);
        }
        context.startActivity(localIntent);
    }

    /**
     * 打开系统网络设置页面
     *
     * @param context
     */
    public static void openNetWorkActivity(Context context) {
        Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
        context.startActivity(intent);
    }

    /**
     * 跳转到应用市场，需要注意可能出现不同手机品牌的适配问题(小米、三星、华为、乐视等)，暂时测试华为p8(ALE_UL00)没问题
     *
     * @param context
     * @param packageName
     */
    public static void goToMarket(@NonNull Context context, @NonNull String packageName) {
        // 获取手机就品牌和型号
        // Log.i("CommonUtils", Build.BRAND + " ------ " + Build.MODEL);
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 三星品牌手机跳转到应用市场
     *
     * @param context
     * @param packageName
     */
    public static void goToSamsungappsMarket(@NonNull Context context, @NonNull String packageName) {
        Uri uri = Uri.parse("http://www.samsungapps.com/appquery/appDetail.as?appId=" + packageName);
        Intent goToMarket = new Intent();
        goToMarket.setClassName("com.sec.android.app.samsungapps", "com.sec.android.app.samsungapps.Main");
        goToMarket.setData(uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 乐视手机跳转到应用市场
     *
     * @param context
     * @param packageName
     */
    public static void goToLeTVStoreDetail(@NonNull Context context, @NonNull String packageName) {
        Intent intent = new Intent();
        intent.setClassName("com.letv.app.appstore", "com.letv.app.appstore.appmodule.details.DetailsActivity");
        intent.setAction("com.letv.app.appstore.appdetailactivity");
        intent.putExtra("packageName", packageName);
        context.startActivity(intent);
    }
}
