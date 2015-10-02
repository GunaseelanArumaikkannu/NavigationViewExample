package com.guna.navigationviewexample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerView.NavigationViewCallbacks {


    /**
     * Used to store the last screen title.
     */
    private CharSequence mTitle;

    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NavigationDrawerView navigationDrawerView = (NavigationDrawerView) findViewById(R.id.navigation_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (TextView) toolbar.findViewById(R.id.txtTitle);
        TextView txtName = (TextView) navigationDrawerView.findViewById(R.id.txtName);
        txtName.setText(getResources().getString(R.string.title_name));
        txtTitle.setText(getResources().getString(R.string.title_home));
        mTitle = getTitle();

        navigationDrawerView.initDrawer(this);
        // Set up the drawer.
        navigationDrawerView.setUp(
                R.id.navigation_view,
                (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
    }


    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_home);
                break;
            case 2:
                mTitle = getString(R.string.title_bookmarks);
                break;
            case 3:
                mTitle = getString(R.string.title_favorite);
                break;
            case 4:
                mTitle = getString(R.string.title_payment);
                break;
            case 5:
                mTitle = getString(R.string.title_settings);
                break;
        }
        if (txtTitle != null)
            txtTitle.setText(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationViewItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
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
            section_label = (TextView) view.findViewById(R.id.section_label);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            int section_number = getArguments().getInt(ARG_SECTION_NUMBER);
            section_label.setText(String.valueOf(section_number));
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            ((MainActivity) context).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
