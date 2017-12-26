package android.weather.app.weatherinfo.utils;

/**
 * Created by R Ramesh on 26/12/17.
 */

public class Util {

    public static <T> void checkNotNull(T value, String name) {
        if (value == null) {
            throw new NullPointerException(name + " should not be null");
        }
    }
}
