package android.weather.app.weatherinfo.networking.request;


import android.weather.app.weatherinfo.networking.response.ZipCodeLatLongResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ZipCodeLatLongRequest {
    @GET("ndfdXMLclient.php")
    Observable<ZipCodeLatLongResponse> getLatLongForZipcode(@Query("listZipCodeList") int level);
}
