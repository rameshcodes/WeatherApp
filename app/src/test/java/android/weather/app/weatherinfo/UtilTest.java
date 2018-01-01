package android.weather.app.weatherinfo;


import android.weather.app.weatherinfo.utils.Util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class UtilTest {

    @Test
    public void convertDateTimeToDate() {
        String dateTime = "2018-01-01T07:00:00-0800";
        String expected = "Jan 01";
        assertEquals(expected, Util.getDate(dateTime));
    }

    @Test
    public void convertDateTimeToTime() {
        String dateTime = "2018-01-01T04:00:00-0800";
        String expected = "05:30 PM";
        assertEquals(expected, Util.getTime(dateTime));
    }
}
