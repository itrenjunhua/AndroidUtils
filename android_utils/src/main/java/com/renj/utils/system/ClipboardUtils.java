package com.renj.utils.system;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import com.renj.utils.common.Logger;
import com.renj.utils.common.UIUtils;
import com.renj.utils.res.StringUtils;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2020-06-15   19:39
 * <p>
 * 描述：剪贴板工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ClipboardUtils {
    /**
     * 将内容保存到剪贴板，并且提示 复制成功、复制失败
     *
     * @param textCase 需要复制的内容
     */
    public static void copyText(String textCase) {
        copyText(textCase, "复制成功", "复制失败");
    }

    /**
     * 将内容保存到剪贴板，并且提示 自定义复制成功提示、复制失败
     *
     * @param textCase   需要复制的内容
     * @param successMsg 成功提示
     */
    public static void copyText(String textCase, String successMsg) {
        copyText(textCase, successMsg, "复制失败");
    }

    /**
     * 将内容保存到剪贴板，并且自定义复制成功、失败提示，都为空时不提示
     *
     * @param textCase   需要复制的内容
     * @param successMsg 成功提示
     * @param failMsg    失败提示
     */
    public static void copyText(String textCase, String successMsg, String failMsg) {
        if (TextUtils.isEmpty(textCase)) {
            return;
        }
        if (clipPermissionCheck(0) == 0) {
            // 无权限，不显示弹框(显示了也没用，用户也申请不到)，直接不使用粘贴板功能
            // showPermissionDialog();
            return;
        }
        ClipboardManager cmb = (ClipboardManager) UIUtils.getContext().getSystemService(CLIPBOARD_SERVICE);
        if (cmb != null) {
            ClipData text = ClipData.newPlainText("text", textCase);
            if (text == null) {
                return;
            }
            cmb.setPrimaryClip(text);
        }
        // 不为null时提示
        if (StringUtils.notEmpty(successMsg, failMsg)) {
            if (getClipboardFirstData().equals(textCase)) {
                UIUtils.showToast(successMsg);
            } else {
                UIUtils.showToast(failMsg);
            }
        }
    }

    /**
     * 获取剪贴板内容
     *
     * @return
     */
    public static String getClipboardFirstData() {
        if (clipPermissionCheck(1) == 1) {
            ClipboardManager manager = (ClipboardManager) UIUtils.getContext().getSystemService(CLIPBOARD_SERVICE);
            if (manager == null) {
                return "";
            }
            ClipData primaryClip = manager.getPrimaryClip();
            if (primaryClip == null) {
                return "";
            }
            int clipDataSize = primaryClip.getItemCount();
            if (clipDataSize == 0) {
                return "";
            }
            ClipData.Item firstClipItem = primaryClip.getItemAt(0);
            if (firstClipItem == null) {
                return "";
            }
            CharSequence clipCharSequence = firstClipItem.getText();
            if (clipCharSequence == null) {
                return "";
            }
            return clipCharSequence.toString();
        } else {
            // 无权限，不显示弹框(显示了也没用，用户也申请不到)，直接不使用粘贴板功能
            // showPermissionDialog();
            return "";
        }
    }

    /**
     * 清除剪贴板内容
     */
    public static void clearClipboardFirstData() {
        if (clipPermissionCheck(2) == 1) {
            ClipboardManager manager = (ClipboardManager) UIUtils.getContext().getSystemService(CLIPBOARD_SERVICE);
            if (manager != null) {
                manager.setPrimaryClip(ClipData.newPlainText("", ""));
            }
        }
        // else {
        // 无权限，不显示弹框(显示了也没用，用户也申请不到)，直接不使用粘贴板功能
        // showPermissionDialog();
        // }
    }

    /**
     * 检查是否有权限
     *
     * @param type 1：读权限  2：写权限  其他：读写权限都检查
     * @return 1：有权限   0：无权限
     */
    public static int clipPermissionCheck(int type) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            try {
                Context context = UIUtils.getContext();
                PackageManager pm = context.getPackageManager();
                AppOpsManager appOpsManager = context.getSystemService(AppOpsManager.class);
                PackageInfo pkgInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
                if (type == 1) {
                    int readResult = appOpsManager.checkOpNoThrow("android:read_clipboard", pkgInfo.applicationInfo.uid, pkgInfo.packageName);
                    if (readResult == AppOpsManager.MODE_ERRORED) {
                        Logger.e("无权限: android:read_clipboard(AppOpsManager.MODE_XXX): " + readResult);
                        return 0;
                    } else {
                        return 1;
                    }
                } else if (type == 2) {
                    int writeResult = appOpsManager.checkOpNoThrow("android:write_clipboard", pkgInfo.applicationInfo.uid, pkgInfo.packageName);
                    if (writeResult == AppOpsManager.MODE_ERRORED) {
                        Logger.e("无权限: android:write_clipboard(AppOpsManager.MODE_XXX): " + writeResult);
                        return 0;
                    } else {
                        return 1;
                    }
                } else {
                    int readResult = appOpsManager.checkOpNoThrow("android:read_clipboard", pkgInfo.applicationInfo.uid, pkgInfo.packageName);
                    int writeResult = appOpsManager.checkOpNoThrow("android:write_clipboard", pkgInfo.applicationInfo.uid, pkgInfo.packageName);
                    if (readResult == AppOpsManager.MODE_ERRORED || writeResult == AppOpsManager.MODE_ERRORED) {
                        Logger.e("无权限: android:read_clipboard(AppOpsManager.MODE_XXX): "
                                + readResult + " ,android:write_clipboard(AppOpsManager.MODE_XXX): " + writeResult);
                        return 0;
                    } else {
                        return 1;
                    }
                }
            } catch (Exception e) {
                // e.printStackTrace();
                Logger.e("调用权限检查发生异常: " + e);
                return 1;
            }
        }
        return 1;
    }
}
