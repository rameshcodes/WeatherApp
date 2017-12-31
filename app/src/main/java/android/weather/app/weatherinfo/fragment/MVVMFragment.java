package android.weather.app.weatherinfo.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.weather.app.weatherinfo.binding.BindingUtils;
import android.weather.app.weatherinfo.binding.ViewModelBinder;
import android.weather.app.weatherinfo.utils.Util;
import android.weather.app.weatherinfo.viewmodel.AppViewModel;

public abstract class MVVMFragment extends Fragment {
    protected ViewDataBinding mBinding;
    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        getDefaultBinder().bind(mBinding, getViewModel());
        mRootView = mBinding.getRoot();
        return mRootView;
    }

    @NonNull
    private ViewModelBinder getDefaultBinder() {
        ViewModelBinder defaultBinder = BindingUtils.getDefaultBinder();
        Util.checkNotNull(defaultBinder, "Default Binder");
        return defaultBinder;
    }

    @NonNull
    protected abstract AppViewModel getViewModel();

    @NonNull
    protected abstract int getLayoutId();

}
