package android.weather.app.weatherinfo.scheduler;

import android.util.Log;
import android.weather.app.weatherinfo.DataSource;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import io.reactivex.functions.Consumer;

public class UpdateWeatherJobService extends JobService {
    private static final String TAG = "UpdateWeatherService";
    private JobParameters mJobParameters;

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.i(TAG, "onStartJob called ");
        mJobParameters = job;
        getFavoriteCities();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.i(TAG, "onStopJob called");
        return false;
    }

    private void getFavoriteCities() {
        DataSource.getFavouriteCitiesLocationData().subscribe(new Consumer() {
            @Override
            public void accept(Object latLongValues) throws Exception {
                Log.i(TAG, "accept: latlong values " + latLongValues);
                updateFavoritesForecast((String) latLongValues);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "on Error " + throwable.getMessage());
            }
        });
    }

    private void updateFavoritesForecast(String latLongValues) {
        DataSource.updateForecastDataForFavorites(latLongValues).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean status) throws Exception {
                Log.i(TAG, "accept: update status : " + status);
                jobFinished(mJobParameters, true);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "Error: " + throwable.getMessage());
                jobFinished(mJobParameters, true);
            }
        });
    }
}
