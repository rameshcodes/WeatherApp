package android.weather.app.weatherinfo.utils;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.WeatherApplication;
import android.weather.app.weatherinfo.fragment.MVVMFragment;
import android.widget.Toast;

import com.bumptech.glide.load.HttpException;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class ActivityUtils {
    private static final String TAG = "ActivityUtils";

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

    public static void handleError(Throwable throwable) {
        String error = null;
        Context context = WeatherApplication.getContext();
        if (throwable instanceof HttpException) {
            Log.i(TAG, "HttpException: " + throwable.getMessage());
            error = throwable.getMessage();
        } else if (throwable instanceof SocketTimeoutException) {
            Log.i(TAG, "SocketTimeoutException: " + throwable.getMessage());
            error = context.getString(R.string.timeout_exception);
        } else if (throwable instanceof IOException) {
            Log.i(TAG, "Network exception: " + throwable.getMessage());
            error = context.getString(R.string.no_internet_connection);
        } else {
            error = throwable.getMessage();
        }
        if (error != null)
            Toast.makeText(WeatherApplication.getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
