package com.renj.common.weight.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.renj.common.R;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-07-07   16:20
 * <p>
 * 描述：自定义对话框，可以设置是否显示标题、设置标题内容、主要信息、确认、取消等文字信息和监听，默认不显示标题
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CustomDialog extends Dialog implements View.OnClickListener {
    private AnimationSet mModalInAnim;
    private View mDialogView;
    private TextView tvDialogContent, tvCancel, tvOk, tvDialogTitle;
    private CustomDialogListener customDialogListener;
    private String confirmText, cancelText, dialogContentText, title;
    private int confirmColor, cancelColor, dialogContentColor, titleColor;
    private int btTextSize, dialogContentSize, titleSize;
    private boolean showTitle = false;

    /**
     * 创建对话框
     *
     * @param context 上下文
     * @return
     */
    public static CustomDialog newInstance(@NonNull Context context) {
        return new CustomDialog(context);
    }

    public CustomDialog(@NonNull Context context) {
        super(context, R.style.alert_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);

        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mModalInAnim = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.dialog_modal_in);

        tvDialogTitle = (TextView) findViewById(R.id.tv_title_dialog);
        tvDialogContent = (TextView) findViewById(R.id.tv_dialog_content);
        tvCancel = (TextView) findViewById(R.id.tv_dialog_cancel);
        tvOk = (TextView) findViewById(R.id.tv_dialog_ok);

        setTitleContent(title);
        isShowTitle(showTitle);
        setDialogContent(dialogContentText);
        setCancelText(cancelText);
        setConfirmText(confirmText);
        setButtonTextSize(btTextSize);
        setContentSize(dialogContentSize);
        setTitleSize(titleSize);
        setConfirmColor(confirmColor);
        setCancelColor(cancelColor);
        setContentColor(dialogContentColor);
        setTitleColor(titleColor);

        tvCancel.setOnClickListener(this);
        tvOk.setOnClickListener(this);
    }

    /**
     * 是否需要显示标题
     *
     * @param showTitle
     * @return
     */
    public CustomDialog isShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
        if (tvDialogTitle != null) {
            if (this.showTitle) {
                tvDialogTitle.setVisibility(View.VISIBLE);
            } else {
                tvDialogTitle.setVisibility(View.GONE);
            }
        }
        return this;
    }

    /**
     * 设置对话框标题
     *
     * @param title
     * @return
     */
    public CustomDialog setTitleContent(@NonNull String title) {
        this.title = title;
        if (tvDialogTitle != null) {
            tvDialogTitle.setText(title);
        }
        return this;
    }

    /**
     * 设置对话框内容
     *
     * @param content
     * @return
     */
    public CustomDialog setDialogContent(@NonNull String content) {
        this.dialogContentText = content;
        if (tvDialogContent != null) {
            tvDialogContent.setText(content);
        }
        return this;
    }

    /**
     * 设置取消按钮内容
     *
     * @param cancelText
     * @return
     */
    public CustomDialog setCancelText(@NonNull String cancelText) {
        this.cancelText = cancelText;
        if (tvCancel != null) tvCancel.setText(cancelText);
        return this;
    }

    /**
     * 设置确定按钮内容
     *
     * @param confirmText
     * @return
     */
    public CustomDialog setConfirmText(@NonNull String confirmText) {
        this.confirmText = confirmText;
        if (tvOk != null) tvOk.setText(confirmText);
        return this;
    }

    /**
     * 设置按钮文字大小 单位：sp
     *
     * @param btTextSize 文字大小 单位：sp
     * @return
     */
    public CustomDialog setButtonTextSize(int btTextSize) {
        this.btTextSize = btTextSize;
        if (tvCancel != null) tvCancel.setTextSize(Dimension.SP, btTextSize);
        if (tvOk != null) tvOk.setTextSize(Dimension.SP, btTextSize);
        return this;
    }

    /**
     * 设置内容文字大小 单位：sp
     *
     * @param contentSize 文字大小 单位：sp
     * @return
     */
    public CustomDialog setContentSize(int contentSize) {
        this.dialogContentSize = contentSize;
        if (tvDialogContent != null) tvDialogContent.setTextSize(Dimension.SP, contentSize);
        return this;
    }

    /**
     * 设置标题文字大小 单位：sp
     *
     * @param titleSize 文字大小 单位：sp
     * @return
     */
    public CustomDialog setTitleSize(int titleSize) {
        this.titleSize = titleSize;
        if (tvDialogTitle != null) tvDialogTitle.setTextSize(Dimension.SP, titleSize);
        return this;
    }

    /**
     * 设置确认按钮颜色
     *
     * @param confirmColor 颜色
     * @return
     */
    public CustomDialog setConfirmColor(int confirmColor) {
        this.confirmColor = confirmColor;
        if (tvOk != null) tvOk.setTextColor(confirmColor);
        return this;
    }

    /**
     * 设置取消按钮颜色
     *
     * @param cancelColor 颜色
     * @return
     */
    public CustomDialog setCancelColor(int cancelColor) {
        this.cancelColor = cancelColor;
        if (tvCancel != null) tvCancel.setTextColor(cancelColor);
        return this;
    }

    /**
     * 设置内容按钮颜色
     *
     * @param contentColor 颜色
     * @return
     */
    public CustomDialog setContentColor(int contentColor) {
        this.dialogContentColor = contentColor;
        if (tvDialogContent != null) tvDialogContent.setTextColor(contentColor);
        return this;
    }

    /**
     * 设置确认按钮颜色
     *
     * @param titleColor 颜色
     * @return
     */
    public CustomDialog setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        if (tvDialogTitle != null) tvDialogTitle.setTextColor(titleColor);
        return this;
    }

    /**
     * 设置触摸Dialog之外是否消失Dialog
     *
     * @param cancel
     * @return
     */
    public CustomDialog setCanceledOnTouchOut(boolean cancel) {
        this.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * 设置监听
     *
     * @param customDialogListener
     * @return
     */
    public CustomDialog setCustomDialogListener(CustomDialogListener customDialogListener) {
        this.customDialogListener = customDialogListener;
        return this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDialogView.startAnimation(mModalInAnim);
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if (R.id.tv_dialog_cancel == vId) {
            this.dismiss();
            if (customDialogListener != null) {
                customDialogListener.onCancel(this);
            }
        } else if (R.id.tv_dialog_ok == vId) {
            this.dismiss();
            if (customDialogListener != null) {
                customDialogListener.onConfirm(this);
            }
        }
    }

    /**
     * 按钮监听
     */
    public interface CustomDialogListener {
        /**
         * 点击确定按钮
         *
         * @param dialog
         */
        void onConfirm(CustomDialog dialog);

        /**
         * 点击取消按钮
         *
         * @param dialog
         */
        void onCancel(CustomDialog dialog);
    }
}
