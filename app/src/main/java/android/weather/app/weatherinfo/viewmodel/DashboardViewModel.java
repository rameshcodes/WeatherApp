package android.weather.app.weatherinfo.viewmodel;

import android.weather.app.weatherinfo.handler.DashboardHandler;

public class DashboardViewModel implements AppViewModel {
    private static final String TAG = "DashboardViewModel";
    private DashboardHandler mDashboardHandler;

    public DashboardViewModel(DashboardHandler dashboardHandler) {
        mDashboardHandler = dashboardHandler;
    }

    public void favoriteClick() {
        mDashboardHandler.showFavouritesFragment();
    }

    public void searchClick() {
        mDashboardHandler.loadSearchFragment();
    }

}
