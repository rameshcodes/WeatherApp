package android.weather.app.weatherinfo.networking.request;

import android.weather.app.weatherinfo.networking.response.WeatherInfoResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MultiLocationWeatherInfoRequest {
    @GET("ndfdXMLclient.php")
    Observable<WeatherInfoResponse> getWeatherInfo(@Query("listLatLon") String latLongList, @Query("product") String product,
                                                   @Query("begin") String beginTime, @Query("end") String endTime, @Query("maxt") String maxtValue,
                                                   @Query("mint") String mintValue, @Query("temp") String tempValue, @Query("icons") String iconValue);

}
