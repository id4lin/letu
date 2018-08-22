package com.letu.app.game.strategy.ui;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.ui.login.widget.LoginActivity;
import com.letu.app.game.strategy.ui.main.widget.MainActivity;
import com.letu.app.game.strategy.utils.RUtils;

public class WelcomeActivity extends AppCompatActivity {

    private TextView scapeTv;
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome_activity);
        scapeTv = (TextView) findViewById(R.id.tv_scape);

        MyCountDownTimer myCountDownTimer = new MyCountDownTimer(5000, 1000); // 5000代表5秒后进入主页面
        myCountDownTimer.start();

        handler.postDelayed(mRunnable = new Runnable() {
            @Override
            public void run() {
                gotoMainActivity();
            }
        }, 5000);

        scapeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMainActivity();

                if (null != mRunnable) {
                    handler.removeCallbacks(mRunnable);
                }
            }
        });
    }


    /**
     * 打开主页并关闭欢迎页面
     */
    private void gotoMainActivity() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        WelcomeActivity.this.finish();
    }

    private Handler handler = new Handler();

    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

        }

        @Override
        public void onTick(long millisUntilFinished) {
            scapeTv.setText(getString(R.string.welcome_scape_title_1)+"(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            scapeTv.setText(getString(R.string.welcome_scape_title_2));
        }
    }
}
