package android.weather.app.weatherinfo.scheduler;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class UpdateWeatherJobService extends JobService {
    private static final String TAG = "UpdateWeatherService";

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.i(TAG, "onStartJob called ");
        jobFinished(job,true);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.i(TAG, "onStopJob called");
        return false;
    }

}
