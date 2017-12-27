package android.weather.app.weatherinfo.networking.request;

import android.weather.app.weatherinfo.networking.response.CitiesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CitiesRequest {
    @GET("ndfdXMLclient.php")
    Observable<CitiesResponse> getCities(@Query("listCitiesLevel") int level);
}
