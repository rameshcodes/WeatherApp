package android.weather.app.weatherinfo.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.weather.app.weatherinfo.binding.BindingUtils;
import android.weather.app.weatherinfo.binding.ViewModelBinder;
import android.weather.app.weatherinfo.utils.Util;
import android.weather.app.weatherinfo.viewmodel.AppViewModel;

public abstract class MVVMActivity extends BaseActivity {
    protected ViewDataBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        getDefaultBinder().bind(mBinding, getViewModel());
    }

    @NonNull
    private ViewModelBinder getDefaultBinder() {
        ViewModelBinder defaultBinder = BindingUtils.getDefaultBinder();
        Util.checkNotNull(defaultBinder, "Default Binder");
        return defaultBinder;
    }

    @Override
    protected void onDestroy() {
        getDefaultBinder().bind(mBinding, null);
        mBinding.executePendingBindings();
        super.onDestroy();
    }

    @NonNull
    protected abstract AppViewModel getViewModel();

    @NonNull
    protected abstract int getLayoutId();
}
