package android.weather.app.weatherinfo.fragment;

import android.support.annotation.NonNull;
import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.activity.BaseActivity;
import android.weather.app.weatherinfo.handler.DashboardHandler;
import android.weather.app.weatherinfo.viewmodel.DashboardViewModel;
import android.weather.app.weatherinfo.viewmodel.ViewModel;

public class DashboardFragment extends MVVMFragment {

    private DashboardHandler dashboardHandler = new DashboardHandler() {
        @Override
        public void loadSearchFragment() {
            ((BaseActivity) getActivity()).replaceFragment(R.id.container_main, new SearchFragment(), true);
        }

        @Override
        public void showFavouritesFragment() {
        }
    };

    @NonNull
    @Override
    protected ViewModel getViewModel() {
        return new DashboardViewModel(dashboardHandler);
    }

    @NonNull
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dashboard;
    }
}
