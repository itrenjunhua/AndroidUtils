package com.renj.utils.common;

import android.os.CountDownTimer;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2020-11-17   15:56
 * <p>
 * 描述：倒计时封装</br></br>
 * <pre>
 * 创建计时器、设置监听并且启动计时器：
 *  CustomCountDownTimer customCountDownTimer=
 *          CustomCountDownTimer.create(60*1000,1000)
 *          .setOnCountDownListener(new SimpleCountDown(){
 *          })
 *          .startTimer();
 *
 * 取消计时器：
 *  customCountDownTimer.cancelTimer();
 * </pre>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CustomCountDownTimer extends CountDownTimer {
    /**
     * @param millisInFuture    总时长 单位：毫秒
     * @param countDownInterval 间隔时长 单位：毫秒
     */
    protected CustomCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    /**
     * 创建倒计时封装类 {@link CustomCountDownTimer}
     *
     * @param millisInFuture    总时长 单位：毫秒
     * @param countDownInterval 间隔时长 单位：毫秒
     */
    public static CustomCountDownTimer create(long millisInFuture, long countDownInterval) {
        return new CustomCountDownTimer(millisInFuture, countDownInterval);
    }

    /**
     * 开始倒计时
     */
    public <T extends CustomCountDownTimer> T startTimer() {
        start();

        if (onCountDownListener != null)
            onCountDownListener.onStart();

        return (T) this;
    }

    /**
     * 取消倒计时
     */
    public void cancelTimer() {
        cancel();

        if (onCountDownListener != null)
            onCountDownListener.onCancel();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (onCountDownListener != null)
            onCountDownListener.onTick(millisUntilFinished);
    }

    @Override
    public void onFinish() {
        if (onCountDownListener != null)
            onCountDownListener.onFinish();
    }

    private OnCountDownListener onCountDownListener;

    /**
     * 设置监听
     *
     * @param onCountDownListener {@link OnCountDownListener}
     */
    public <T extends CustomCountDownTimer> T setOnCountDownListener(OnCountDownListener onCountDownListener) {
        this.onCountDownListener = onCountDownListener;
        return (T) this;
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
         * @param millisUntilFinished 剩余时间 毫秒
         */
        void onTick(long millisUntilFinished);

        /**
         * 取消倒计时
         */
        void onCancel();

        /**
         * 完成倒计时
         */
        void onFinish();
    }

    /**
     * 监听实现，减少方法重写
     */
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
        public void onTick(long millisUntilFinished) {

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
