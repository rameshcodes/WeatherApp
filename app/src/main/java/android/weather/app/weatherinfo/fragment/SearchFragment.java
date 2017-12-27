package android.weather.app.weatherinfo.fragment;

import android.support.annotation.NonNull;
import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.viewmodel.SearchViewModel;
import android.weather.app.weatherinfo.viewmodel.ViewModel;

public class SearchFragment extends MVVMFragment {
    @NonNull
    @Override
    protected ViewModel getViewModel() {
        return new SearchViewModel();
    }

    @NonNull
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }
}
