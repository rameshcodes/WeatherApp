package android.weather.app.weatherinfo.utils;

public class Util {

    public static <T> void checkNotNull(T value, String name) {
        if (value == null) {
            throw new NullPointerException(name + " should not be null");
        }
    }
}
