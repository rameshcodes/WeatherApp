package android.weather.app.weatherinfo.fragment;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.transition.Fade;
import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.databinding.FragmentDashboardBinding;
import android.weather.app.weatherinfo.handler.DashboardHandler;
import android.weather.app.weatherinfo.utils.ActivityUtils;
import android.weather.app.weatherinfo.utils.SearchTransition;
import android.weather.app.weatherinfo.viewmodel.DashboardViewModel;
import android.weather.app.weatherinfo.viewmodel.ViewModel;

public class DashboardFragment extends MVVMFragment {

    private DashboardHandler dashboardHandler = new DashboardHandler() {
        @Override
        public void loadSearchFragment() {
            SearchFragment searchFragment = new SearchFragment();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                searchFragment.setEnterTransition(new Fade());
                setExitTransition(new Fade());
                searchFragment.setSharedElementEnterTransition(new SearchTransition());
            }
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.addSharedElement(((FragmentDashboardBinding) mBinding).searchView, getString(R.string.search_transition));
            fragmentTransaction.replace(R.id.container_main, searchFragment).addToBackStack(getString(R.string.dashboard));
            fragmentTransaction.commit();
        }

        @Override
        public void showFavouritesFragment() {
            ActivityUtils.replaceFragment(getActivity(), R.id.container_main, new FavoritesFragment(), true, getString(R.string.dashboard));
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
