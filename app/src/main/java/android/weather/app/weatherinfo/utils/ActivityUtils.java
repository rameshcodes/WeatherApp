package android.weather.app.weatherinfo.utils;


import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.weather.app.weatherinfo.fragment.MVVMFragment;

public class ActivityUtils {
    public static void replaceFragment(FragmentActivity baseActivity, int containerId, MVVMFragment fragment, boolean addToBackStack, String title) {
        FragmentTransaction transaction = baseActivity.getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment);
        if (addToBackStack)
            transaction.addToBackStack(title);
        transaction.commit();
    }

    public static void addFragment(FragmentActivity baseActivity, int containerId, MVVMFragment fragment, boolean addToBackStack, String title) {
        FragmentTransaction transaction = baseActivity.getSupportFragmentManager().beginTransaction();
        transaction.add(containerId, fragment);
        if (addToBackStack)
            transaction.addToBackStack(title);
        transaction.commit();
    }
}
