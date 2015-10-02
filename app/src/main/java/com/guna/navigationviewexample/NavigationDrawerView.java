package com.guna.navigationviewexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerView extends NavigationView  implements NavigationView.OnNavigationItemSelectedListener {

    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    private AppCompatActivity activity;
    private View mNavigationView;
    public DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private int mCurrentSelectedPosition = 0;

    private NavigationViewCallbacks mCallbacks;

    public void initDrawer(MainActivity activity) {
        this.activity = activity;
        mCallbacks = activity;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        mCurrentSelectedPosition = sp.getInt(STATE_SELECTED_POSITION, 0);
        mFromSavedInstanceState = true;
        mCallbacks.onNavigationViewItemSelected(mCurrentSelectedPosition);
    }

    public NavigationDrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setUp(int navigationViewId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mNavigationView = activity.findViewById(navigationViewId);
        NavigationView navigationView = (NavigationView) activity.findViewById(navigationViewId);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                activity,                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                toolbar,
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                /*if (!isAdded()) {
                    return;
                }*/

                activity.supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mDrawerLayout.openDrawer(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(activity);
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                //activity.supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mNavigationView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int position = 0;
        switch (menuItem.getItemId()) {
            case R.id.navigation_item_1:
                mCallbacks.onNavigationViewItemSelected(0);
                position = 0;
                break;
            case R.id.navigation_item_2:
                mCallbacks.onNavigationViewItemSelected(1);
                position = 1;
                break;
            case R.id.navigation_item_3:
                mCallbacks.onNavigationViewItemSelected(2);
                position = 2;
                break;
            case R.id.navigation_item_4:
                mCallbacks.onNavigationViewItemSelected(3);
                position = 3;
                break;
            case R.id.navigation_item_5:
                mCallbacks.onNavigationViewItemSelected(4);
                position = 0;
                break;
        }
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(activity);
        sp.edit().putInt(STATE_SELECTED_POSITION, position).apply();
        mDrawerLayout.closeDrawers();
        return true;
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public interface NavigationViewCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationViewItemSelected(int position);
    }
}
