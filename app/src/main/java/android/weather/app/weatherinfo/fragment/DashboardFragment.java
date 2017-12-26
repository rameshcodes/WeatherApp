package android.weather.app.weatherinfo.fragment;

import android.support.annotation.NonNull;
import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.viewmodel.DashboardViewModel;
import android.weather.app.weatherinfo.viewmodel.ViewModel;

public class DashboardFragment extends MVVMFragment {
    @NonNull
    @Override
    protected ViewModel getViewModel() {
        return new DashboardViewModel();
    }

    @NonNull
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dashboard;
    }
}
