package android.weather.app.weatherinfo.binding;

import android.databinding.ViewDataBinding;
import android.weather.app.weatherinfo.viewmodel.AppViewModel;

public interface ViewModelBinder {
    void bind(ViewDataBinding viewDataBinding, AppViewModel viewModel);
}
