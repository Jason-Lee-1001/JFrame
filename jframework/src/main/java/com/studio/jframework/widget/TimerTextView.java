package com.studio.jframework.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/*TimerTextView v = (TimerTextView) findViewById(R.id.button1);
    v.setTextAfter("秒后重新获取").setTextBefore("点击获取验证码").setLenght(15 * 1000);
    v.startTimer();*/

/*在finish界面时调用 v.clearTimer();*/

public class TimerTextView extends TextView {

    private long length = 60 * 1000;// 倒计时长度,默认60秒
    private String textAfter = "秒后重新获取";
    private String textBefore = "获取验证码";
    private Timer timer;
    private TimerTask timerTask;
    private long time;

    public TimerTextView(Context context) {
        super(context);
    }

    public TimerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            TimerTextView.this.setText(time / 1000 + textAfter);
            time -= 1000;
            if (time < 0) {
                TimerTextView.this.setEnabled(true);
                TimerTextView.this.setText(textBefore);
                clearTimer();
            }
        }
    };

    private void initTimer() {
        time = length;
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0x01);
            }
        };
    }

    public void clearTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null)
            timer.cancel();
        timer = null;
    }

    public void startTimer() {
        initTimer();
        this.setText(time / 1000 + textAfter);
        this.setEnabled(false);
        timer.schedule(timerTask, 0, 1000);
    }

    /**
     * 设置计时时候显示的文本
     *
     * @param text 变化后的文字
     * @return TimerTextView
     */
    public TimerTextView setTextAfter(String text) {
        this.textAfter = text;
        return this;
    }

    /**
     * 设置点击之前的文本
     *
     * @param text 变化前的文字
     * @return TimerTextView
     */
    public TimerTextView setTextBefore(String text) {
        this.textBefore = text;
        this.setText(textBefore);
        return this;
    }

    /**
     * 设置到计时长度
     *
     * @param length 时间 默认毫秒
     * @return TimerTextView
     */
    public TimerTextView setLenght(long length) {
        this.length = length;
        return this;
    }

}  