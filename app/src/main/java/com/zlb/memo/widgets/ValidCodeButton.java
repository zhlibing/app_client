package com.zlb.memo.widgets;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;

import com.zlb.memo.R;


/**
 * @author Daniel
 * @ClassName: ValidCodeButton.java
 * @Description: 发送验证码自动倒计时按钮
 * @date 2016/2/22 16:23
 */
public class ValidCodeButton extends android.support.v7.widget.AppCompatButton {
    ValidCodeCountDownTimer timer;

    // Context mContext;

    public ValidCodeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // mContext = context;
        timer = new ValidCodeCountDownTimer(60000, 1000);
    }


    public void resetTimer() {
        setText(getResources().getString(R.string.get_verification_code_hint));
        timer.cancel();
        setClickable(true);
        setSelected(false);
    }

    public void canCel() {
        timer.cancel();

    }

    public void startCountDownTimer() {
        timer.start();
    }

    /* 定义一个倒计时的内部类 */
    class ValidCodeCountDownTimer extends CountDownTimer {
        public ValidCodeCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            setText(getContext().getString(R.string.get_code_again, ""));
            setClickable(true);
            setSelected(false);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            setText(getContext().getString(R.string.get_code_again, "(" + millisUntilFinished / 1000 + ")"));
            setClickable(false);
            setSelected(true);
        }
    }
}
