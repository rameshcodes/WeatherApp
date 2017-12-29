package android.weather.app.weatherinfo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.weather.app.weatherinfo.handler.DayListHandler;


public class DayRecyclerView extends RecyclerView {

    private ViewPager tidesPager;
    private DayListHandler dayListHandler;


    public DayRecyclerView(Context context) {
        super(context);
    }

    public DayRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setUpWithViewPager(ViewPager viewPager, DayListHandler handler) {
        this.tidesPager = viewPager;
        this.dayListHandler = handler;
        setUp();
    }

    private void setUp() {
        tidesPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    dayListHandler.onMonthPagerSwiped(tidesPager.getCurrentItem());
                }
            }
        });
    }


}
