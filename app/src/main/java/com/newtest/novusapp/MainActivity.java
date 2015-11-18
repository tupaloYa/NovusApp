package com.newtest.novusapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.newtest.novusapp.fragments.MyCardsFragment;
import com.newtest.novusapp.fragments.MyProfileFragment;
import com.newtest.novusapp.fragments.LogoutDialogFragment;
import com.newtest.novusapp.fragments.MyCardsFragment;
import com.newtest.novusapp.fragments.MyProfileFragment;
import com.newtest.novusapp.fragments.MyPurchaseHistoryFragment;
import com.newtest.novusapp.fragments.OfferItemFragment;
import com.newtest.novusapp.fragments.StocksAndOffersFragment;
import com.newtest.novusapp.fragments.MyOffersFragment;
import com.newtest.novusapp.fragments.MyPurchaseHistoryFragment;
import com.newtest.novusapp.fragments.StocksAndOffersFragment;
import com.newtest.novusapp.fragments.IssuesFragment;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    private String[] mScreenTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!isNetworkAvailable()) {
            Toast.makeText(MainActivity.this, R.string.no_conn, LENGTH_LONG).show();
        }
        //Intent intent = new Intent(this,StocksAndOffersFragment.class);
        //startActivity(intent);

        mTitle = mDrawerTitle = getTitle();
        mScreenTitles = getResources().getStringArray(R.array.screen_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
//        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
//                R.layout.drawer_list_item, mScreenTitles));
        // Set the list's click listener
        final TypedArray typedArray = getResources().obtainTypedArray(R.array.sections_icons);
        mDrawerList.setAdapter(new ArrayAdapter<String>(
                                       this,
                                       R.layout.drawer_list_item,
                                       android.R.id.text1,
                                       getResources().getStringArray(R.array.screen_array)
                               )
                               {
                                   @Override
                                   public View getView(int position, View convertView, ViewGroup parent){
                                       View v = super.getView(position,convertView,parent);
                                       int resourceId = typedArray.getResourceId(position, 0);
                                       Drawable drawable = getResources().getDrawable(resourceId);
                                       ((TextView)v).setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                                       return v;
                                   }
                               }
        );
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open, /* "open drawer" description */
                R.string.drawer_close /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
//                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
//                getSupportActionBar().setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

//         Initialize the first fragment when the application first loads.
        if (savedInstanceState == null) {
             selectItem(1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {
            case R.id.action_scanOffer:
               // Show toast about click.
                //Toast.makeText(this, R.string.action_search, Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(this, ScanActivity.class);
               // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
//
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {
        // Update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                //fragment = new StocksAndOffersFragment();
                fragment = new StocksAndOffersFragment();
                break;
            case 1:
               // fragment = new MyProfileFragment();
                fragment = new MyOffersFragment();
                break;
            case 2:
               // fragment = new MyCardsFragment();
                fragment = new MyCardsFragment();
                break;
            case 3:
               // fragment = new MyPurchaseHistoryFragment();
                fragment = new MyPurchaseHistoryFragment();
                break;
            case 4:
               // fragment = new MyOffersFragment();
                fragment = new IssuesFragment();
                break;
            case 5:
                fragment = new MyProfileFragment();

                break;

            case 6:
               // fragment = new LogoutDialogFragment();
                fragment = new LogoutDialogFragment();
                break;


            default:
                break;
        }

        // Insert the fragment by replacing any existing fragment
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();

            // Highlight the selected item, update the title, and close the drawer
            mDrawerList.setItemChecked(position, true);
            setTitle(mScreenTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // Error
            Log.e(this.getClass().getName(), "Error. Fragment is not created");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
//        getSupportActionBar().setTitle(mTitle);

    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles0
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
        finish();
        System.runFinalizersOnExit(true);
        System.exit(0);
    }

    public void onDestroy() {
        super.onDestroy();

        System.runFinalizersOnExit(true);
        System.exit(0);
    }


//    public void onBackPressed() {
//
//    finish();
//        Intent setIntent = new Intent(Intent.ACTION_MAIN);
//        setIntent.addCategory(Intent.CATEGORY_HOME);
//        setIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//        startActivity(setIntent);

//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, R.string.exit_click, Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//                startActivity(new Intent(MainActivity.this, MainActivity.class));
//                finish();
//            }
//        }, 2000);




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
          //  Toast.makeText(MainActivity.this,R.string.exit_click, LENGTH_LONG).show();
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public   boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public class getOffersTask extends AsyncTask<Void,Void,Void>{

        private String url;

        @Override
        protected Void doInBackground(Void... params) {


            return null;
        }
    }

    public void onSectionAttached(int number){
        String[] stringArray = getResources().getStringArray(R.array.screen_array);
        if(number >=1){
            mTitle = stringArray[number-1];
        }
    }
    public void setActionBarTitle(int actionBarTitle) {
        getSupportActionBar().setTitle(actionBarTitle);
    }


}