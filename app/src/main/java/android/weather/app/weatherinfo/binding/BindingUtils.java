package android.weather.app.weatherinfo.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;
import android.weather.app.weatherinfo.BR;
import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.viewmodel.ViewModel;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

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


    @BindingAdapter({"imageUrl"})
    public static void loadImage(final ImageView view, String url) {
        if (url == null) {
            return;
        }
        Context context = view.getContext();
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        view.setImageBitmap(resource);
                    }
                });
    }

    @BindingAdapter({"isFavorite"})
    public static void setFavorite(final ImageView view, boolean isFavorite) {
        view.setImageResource(isFavorite ? R.drawable.ic_favorite_selected : R.drawable.ic_favorite_non_selected);
    }
}
