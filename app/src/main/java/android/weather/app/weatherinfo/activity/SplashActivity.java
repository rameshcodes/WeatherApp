package android.weather.app.weatherinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.weather.app.weatherinfo.R;

public class SplashActivity extends BaseActivity {

    private int SPLASH_DISPLAY_TIME = 2000;

    private Handler splashHandler = new Handler();

    private Runnable startMainActivityRunnable = new Runnable() {
        @Override
        public void run() {
            Intent mainActivityIntent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(mainActivityIntent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashHandler.postDelayed(startMainActivityRunnable, SPLASH_DISPLAY_TIME);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        splashHandler.removeCallbacks(startMainActivityRunnable);
    }
}
