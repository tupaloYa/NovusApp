package com.newtest.novusapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.newtest.novusapp.fragments.StockItemFragment;
import com.newtest.novusapp.util.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ROOT-PC on 05.11.2015.
 */
public class StocksActivity extends ActionBarActivity {

    public static final String STOCKS = "stocks";
    public static final String STOCK_ID = "stock_id";
    public static final String NAME="name";
    public static final String START_DATE="start_date";
    public static final String EXP_DATE="exp_date";

    private ListView stocksListView;


    List<HashMap<String, String>> stocksList = new ArrayList<HashMap<String, String>>();

    JSONArray stocksJSONArray = null;

    public String url = "http://192.168.1.250/get_all_stocks.php";
//    private static final int NUM_PAGES = 2;
//    private ViewPager stocksPager;
//    private PagerAdapter stocksAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(R.string.title_activity_stock);
        setContentView(R.layout.activity_stocks);
        stocksListView = (ListView) findViewById(R.id.stocksList);

        GetStocksTask getStocksTask = new GetStocksTask();
        getStocksTask.execute();
//        stocksPager = (ViewPager) findViewById(R.id.stocksPager);
//        stocksAdapter = new StocksSlidePagerAdapter(getSupportFragmentManager());
//        stocksPager.setAdapter(stocksAdapter);

    }

//    private class StocksSlidePagerAdapter extends android.support.v4.app.FragmentStatePagerAdapter {
//
//
//        public StocksSlidePagerAdapter(android.support.v4.app.FragmentManager fragmentManager) {
//            super(fragmentManager);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return StockItemFragment.create(position);
//        }
//
//        @Override
//        public int getCount() {
//            return NUM_PAGES;
//        }
//    }

    class GetStocksTask extends AsyncTask<Void,Void,Void> {




        @Override
        protected Void doInBackground(Void... params) {

            ServiceHandler sh = new ServiceHandler();
            String jsonStr = null;

            Log.d("email url", url);
            try {

                jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.i("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    stocksJSONArray = jsonObj.getJSONArray(STOCKS);

                    // looping through
                    for (int i = 0; i < stocksJSONArray.length(); i++) {

                        JSONObject c = stocksJSONArray.getJSONObject(i);

                        String name = c.getString(NAME);
                        String quantity = c.getString(START_DATE);
                        String price = c.getString(EXP_DATE);

                        price = price ;
                        // tmp hashmap for single stock
                        HashMap<String, String> stock = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        stock.put(NAME, name);
                        stock.put(START_DATE, quantity);
                        stock.put(EXP_DATE, price);


                        // adding contact to contact list
                        stocksList.add(stock);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ListAdapter adapter = new SimpleAdapter(
                    StocksActivity.this, stocksList,
                    R.layout.stock_item, new String[]{NAME, START_DATE,
                    EXP_DATE}, new int[]{R.id.stockText,
                    R.id.stockStartDate, R.id.stockExpDate});

            stocksListView.setAdapter(adapter);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setActionBarTitle(int actionBarTitle) {
        getSupportActionBar().setTitle(actionBarTitle);
    }


}
