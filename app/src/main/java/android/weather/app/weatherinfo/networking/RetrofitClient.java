package android.weather.app.weatherinfo.networking;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RetrofitClient {
    private static final String APP_URL = "https://graphical.weather.gov/xml/sample_products/browser_interface/";
    private static Retrofit mRetrofit;

    public static synchronized Retrofit getClient() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder().baseUrl(APP_URL)
                    .client(getOKHttpClient())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    private static OkHttpClient getOKHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }
}
