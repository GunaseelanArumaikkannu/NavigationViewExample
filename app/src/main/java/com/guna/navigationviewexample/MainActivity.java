package com.guna.navigationviewexample;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    /**
     * Used to store the last screen title.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        TextView txtName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtName);
        txtName.setText(getResources().getString(R.string.title_name));
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(1))
                .commit();

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getResources().getString(R.string.title_home));
        mTitle = getTitle();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        int position = 0;
        switch (id) {
            case R.id.navigation_item_1:
                mTitle = getString(R.string.title_home);
                position = 0;
                break;
            case R.id.navigation_item_2:
                mTitle = getString(R.string.title_bookmarks);
                position = 1;
                break;
            case R.id.navigation_item_3:
                mTitle = getString(R.string.title_favorite);
                position = 2;
                break;
            case R.id.navigation_item_4:
                mTitle = getString(R.string.title_payment);
                position = 3;
                break;
            case R.id.navigation_item_5:
                mTitle = getString(R.string.title_settings);
                position = 4;
                break;
        }
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(mTitle);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private TextView section_label;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            int section_number = getArguments().getInt(ARG_SECTION_NUMBER);
            int[] rainbow = getActivity().getResources().getIntArray(R.array.colors);
            view.setBackgroundColor(rainbow[section_number - 1]);
            section_label = (TextView) view.findViewById(R.id.section_label);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            int section_number = getArguments().getInt(ARG_SECTION_NUMBER);
            section_label.setText(String.valueOf(section_number));
        }
    }
}
