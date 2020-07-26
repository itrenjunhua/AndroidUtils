package com.renj.utils.common;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.widget.TextView;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-12-27   15:43
 * <p>
 * 描述：按钮倒计时器
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ButtonCountDownTimer extends CountDownTimer {
    private TextView textView;
    private boolean timeOnBefore;
    private String tickMessage;
    private String finishMessage;

    /**
     * @param millisInFuture    默认时长
     * @param countDownInterval 间隔时长
     * @param textView          按钮控件
     * @param finishMessage     倒计时完成后显示文案
     */
    public ButtonCountDownTimer(long millisInFuture, long countDownInterval, @NonNull TextView textView, @NonNull String finishMessage) {
        this(millisInFuture, countDownInterval, textView, "", finishMessage);
    }

    /**
     * @param millisInFuture    默认时长
     * @param countDownInterval 间隔时长
     * @param textView          按钮控件
     * @param tickMessage       倒计时时显示文案  "剩余秒数 tickMessage"
     * @param finishMessage     倒计时完成后显示文案
     */
    public ButtonCountDownTimer(long millisInFuture, long countDownInterval, @NonNull TextView textView,
                                @NonNull String tickMessage, @NonNull String finishMessage) {
        this(millisInFuture, countDownInterval, textView, true, tickMessage, finishMessage);
    }

    /**
     * @param millisInFuture    默认时长
     * @param countDownInterval 间隔时长
     * @param textView          按钮控件
     * @param timeOnBefore      时间显示在前面还是后面  true：前面（那么倒计时改变过程的显示为 "剩余秒数 tickMessage"）
     * @param tickMessage       倒计时时显示文案
     * @param finishMessage     倒计时完成后显示文案
     */
    public ButtonCountDownTimer(long millisInFuture, long countDownInterval, @NonNull TextView textView,
                                boolean timeOnBefore, @NonNull String tickMessage, @NonNull String finishMessage) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.timeOnBefore = timeOnBefore;
        this.tickMessage = tickMessage;
        this.finishMessage = finishMessage;
    }

    public void startTimer() {
        textView.setEnabled(false);
        start();

        if (onCountDownListener != null)
            onCountDownListener.onStart();
    }

    public void cancelTimer() {
        cancel();
    }

    public void cancelTimer(boolean enable, @NonNull String cancelMessage) {
        cancel();
        textView.setEnabled(enable);
        textView.setText(cancelMessage);

        if (onCountDownListener != null)
            onCountDownListener.onCancel();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (timeOnBefore)
            textView.setText((millisUntilFinished / 1000) + tickMessage);
        else
            textView.setText(tickMessage + (millisUntilFinished / 1000));

        if (onCountDownListener != null)
            onCountDownListener.onTick(textView, millisUntilFinished);
    }

    @Override
    public void onFinish() {
        textView.setEnabled(true);
        textView.setText(finishMessage);

        if (onCountDownListener != null)
            onCountDownListener.onFinish();
    }


    private OnCountDownListener onCountDownListener;

    public void setOnCountDownListener(OnCountDownListener onCountDownListener) {
        this.onCountDownListener = onCountDownListener;
    }

    /**
     * 各种状态监听
     */
    public interface OnCountDownListener {
        /**
         * 开始倒计时
         */
        void onStart();

        /**
         * 正在倒计时
         *
         * @param textView            控件
         * @param millisUntilFinished 剩余时间 毫秒
         */
        void onTick(TextView textView, long millisUntilFinished);

        /**
         * 取消倒计时
         */
        void onCancel();

        /**
         * 完成倒计时
         */
        void onFinish();
    }

    public static class SimpleCountDown implements OnCountDownListener {

        /**
         * {@inheritDoc}
         */
        @Override
        public void onStart() {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onTick(TextView textView, long millisUntilFinished) {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onCancel() {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onFinish() {

        }
    }

}
