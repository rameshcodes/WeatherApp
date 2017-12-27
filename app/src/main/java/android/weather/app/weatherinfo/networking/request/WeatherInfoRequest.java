package android.weather.app.weatherinfo.networking.request;


import android.weather.app.weatherinfo.networking.response.WeatherInfoResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherInfoRequest {
    @GET("ndfdXMLclient.php")
    Observable<WeatherInfoResponse> getWeatherInfo(@Query("lat") String lat, @Query("lon") String lon, @Query("product") String product,
                                                   @Query("begin") String beginTime, @Query("end") String endTime, @Query("maxt") String maxtValue,
                                                   @Query("mint") String mintValue, @Query("icons") String iconValue);

}
