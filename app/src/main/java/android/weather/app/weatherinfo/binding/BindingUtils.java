package android.weather.app.weatherinfo.binding;

import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;
import android.weather.app.weatherinfo.BR;
import android.weather.app.weatherinfo.viewmodel.ViewModel;

//Binding method for setting View setOnClickListener in layout as attribute
@BindingMethods({@BindingMethod(type = View.class,
        attribute = "android:onClick",
        method = "setOnClickListener")})
public final class BindingUtils {
    private static final String TAG = "BindingUtils";
    @Nullable
    private static ViewModelBinder defaultBinder;

    public static void setDefaultBinder() {
        defaultBinder = new ViewModelBinder() {
            @Override
            public void bind(ViewDataBinding viewDataBinding, ViewModel viewModel) {
                viewDataBinding.setVariable(BR.vm, viewModel);
            }
        };
    }

    @Nullable
    public static ViewModelBinder getDefaultBinder() {
        return defaultBinder;
    }
}
