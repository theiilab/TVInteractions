package com.yuanren.tvinteractions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.yuanren.tvinteractions.base.SocketUpdateCallback;
import com.yuanren.tvinteractions.utils.NetworkUtils;

public class SplashActivity extends Activity implements SocketUpdateCallback {
    public static int[] randoms; // random position
    private TextView teaser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        teaser = findViewById(R.id.teaser);

        NetworkUtils.setSocketUpdateCallback(this);
        NetworkUtils.start();
    }

    @Override
    public void update(Handler handler, String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                teaser.setText("Loading");

                String[] tmp = text.split(",");
                randoms = new int[tmp.length];
                for (int i = 0; i < tmp.length; ++i) {
                    randoms[i] = Integer.parseInt(tmp[i]);
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        NetworkUtils.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkUtils.stop();
    }
}