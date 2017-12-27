package android.weather.app.weatherinfo.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.weather.app.weatherinfo.fragment.MVVMFragment;


public class BaseActivity extends AppCompatActivity {
    public void replaceFragment(int containerId, MVVMFragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment);
        if (addToBackStack)
            transaction.addToBackStack(null);
        transaction.commit();
    }
}
