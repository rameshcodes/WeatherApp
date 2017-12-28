package android.weather.app.weatherinfo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Listen for changes in the back stack
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        //Handle when activity is recreated like on orientation Change
        shouldDisplayHomeUp();
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp() {
        //Enable Up button only  if there are entries in the back stack
        if (getSupportActionBar() != null) {
            boolean canBack = getSupportFragmentManager().getBackStackEntryCount() > 0;
            getSupportActionBar().setDisplayHomeAsUpEnabled(canBack);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        if (count >= 1) {
            String title = fragmentManager.getBackStackEntryAt(count-1).getName();
            super.onBackPressed();
            getSupportActionBar().setTitle(title);
        } else
            super.onBackPressed();
    }
}
