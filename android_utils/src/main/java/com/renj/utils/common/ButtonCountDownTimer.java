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
    private String tickMessage;
    private String finishMessage;

    /**
     * @param millisInFuture    默认时长
     * @param countDownInterval 间隔时长
     * @param textView            按钮控件
     * @param tickMessage       倒计时时显示文案  "秒数 tickMessage"
     * @param finishMessage     倒计时完成后显示文案
     */
    public ButtonCountDownTimer(long millisInFuture, long countDownInterval, @NonNull TextView textView,
                                @NonNull String tickMessage, @NonNull String finishMessage) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.tickMessage = tickMessage;
        this.finishMessage = finishMessage;
    }

    public void startTimer() {
        textView.setEnabled(false);
        start();
    }

    public void cancelTimer() {
        cancel();
    }

    public void cancelTimer(boolean enable, @NonNull String cancelMessage) {
        cancel();
        textView.setEnabled(enable);
        textView.setText(cancelMessage);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        textView.setText((millisUntilFinished / 1000) + tickMessage);
    }

    @Override
    public void onFinish() {
        textView.setEnabled(true);
        textView.setText(finishMessage);
    }
}
