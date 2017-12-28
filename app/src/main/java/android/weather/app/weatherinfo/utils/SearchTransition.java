package android.weather.app.weatherinfo.utils;

import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;

public class SearchTransition extends TransitionSet {

    public SearchTransition() {
        setOrdering(ORDERING_SEQUENTIAL);
        addTransition(new ChangeBounds()).
        addTransition(new ChangeTransform());
    }
}
