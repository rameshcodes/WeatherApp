package android.weather.app.weatherinfo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.fragment.DashboardFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
            loadDashboardFragment();
    }

    private void loadDashboardFragment() {
        replaceFragment(R.id.container_main, new DashboardFragment(), false);
    }
}

