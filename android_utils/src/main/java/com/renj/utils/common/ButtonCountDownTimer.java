package com.renj.utils.common;

import android.support.annotation.NonNull;
import android.widget.TextView;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-12-27   15:43
 * <p>
 * 描述：按钮倒计时器</br></br>
 * <pre>
 * 创建计时器、设置监听并且启动计时器：
 * {@code
 *      ButtonCountDownTimer buttonCountDownTimer =
 *            ButtonCountDownTimer.create(60 * 1000, 1000,
 *                    textView, "重新获取")
 *                    .setOnCountDownListener(new SimpleCountDown() {
 *                        @Override
 *                        public void onTick(long millisUntilFinished) {
 *                             // 正在倒计时  参数 millisUntilFinished 表示剩余时间 毫秒
 *                             textView.setText((millisUntilFinished / 1000) + "s 后重新获取");
 *                        }
 *                     })
 *                     .startTimer();
 *  }
 *
 * 取消计时器：
 *  buttonCountDownTimer.cancelTimer();
 *  buttonCountDownTimer.cancelTimer(true, "重新获取");
 * </pre>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ButtonCountDownTimer extends CustomCountDownTimer {
    private TextView textView;
    private String finishMessage;

    /**
     * @param millisInFuture    总时长 单位：毫秒
     * @param countDownInterval 间隔时长 单位：毫秒
     * @param textView          按钮控件
     * @param finishMessage     倒计时完成后显示文案
     */
    protected ButtonCountDownTimer(long millisInFuture, long countDownInterval,
                                   @NonNull TextView textView, @NonNull String finishMessage) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.finishMessage = finishMessage;
    }

    /**
     * 创建按钮倒计时器
     *
     * @param millisInFuture    总时长 单位：毫秒
     * @param countDownInterval 间隔时长 单位：毫秒
     * @param textView          按钮控件
     * @param finishMessage     倒计时完成后显示文案
     */
    public static ButtonCountDownTimer create(long millisInFuture, long countDownInterval,
                                              @NonNull TextView textView, @NonNull String finishMessage) {
        return new ButtonCountDownTimer(millisInFuture, countDownInterval, textView, finishMessage);
    }

    /**
     * 开启倒计时，并将控件设置为不可用状态
     */
    @Override
    public ButtonCountDownTimer startTimer() {
        super.startTimer();
        if (textView != null) {
            textView.setEnabled(false);
        }

        return this;
    }

    /**
     * 取消计时器
     *
     * @param enable        取消之后控件是否可用
     * @param cancelMessage 取消之后显示的文案
     */
    public void cancelTimer(boolean enable, @NonNull String cancelMessage) {
        super.cancelTimer();
        if (textView != null) {
            textView.setEnabled(enable);
            textView.setText(cancelMessage);
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        if (textView != null) {
            textView.setEnabled(true);
            textView.setText(finishMessage);
        }
    }
}
