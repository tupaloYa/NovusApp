package com.newtest.novusapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
public class OffersActivity extends ActionBarActivity {

//    private ViewPager offersPager;
//    private PagerAdapter offersAdapter;

    public static final String OFFERS = "offers";
    public static final String OFFER_ID = "offer_id";
    public static final String NAME="name";
    public static final String START_DATE="start_date";
    public static final String EXP_DATE="exp_date";

    JSONArray offersJSONArray = null;

    public String url = "http://192.168.1.250/get_all_offers.php";


    private ListView offersListView;
    List<HashMap<String, String>> offersList = new ArrayList<HashMap<String, String>>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(R.string.title_activity_offer);
        setContentView(R.layout.activity_offers);

        offersListView = (ListView) findViewById(R.id.offersList);

        GetOffersTask getOffersTask = new GetOffersTask();
        getOffersTask.execute();
//        adapter = new OffersSlidePagerAdapter();
//        offersListView.setAdapter(adapter);

//        offersPager = (ViewPager) findViewById(R.id.offersPager);
//        offersAdapter = new OffersSlidePagerAdapter(getSupportFragmentManager());
//        offersPager.setAdapter(offersAdapter);
    }

    public void setActionBarTitle(int actionBarTitle) {
        getSupportActionBar().setTitle(actionBarTitle);
    }


    class GetOffersTask extends AsyncTask<Void,Void,Void> {


        public String EMAIL;

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
                    offersJSONArray = jsonObj.getJSONArray(OFFERS);

                    // looping through
                    for (int i = 0; i < offersJSONArray.length(); i++) {

                        JSONObject c = offersJSONArray.getJSONObject(i);

                        String name = c.getString(NAME);
                        String quantity = c.getString(START_DATE);
                        String price = c.getString(EXP_DATE);

                        price = price ;
                        // tmp hashmap for single offer
                        HashMap<String, String> offer = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        offer.put(NAME, name);
                        offer.put(START_DATE, quantity);
                        offer.put(EXP_DATE, price);


                        // adding contact to contact list
                        offersList.add(offer);
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
                    OffersActivity.this, offersList,
                    R.layout.offer_item, new String[]{NAME, START_DATE,
                    EXP_DATE}, new int[]{R.id.offerText,
                    R.id.offerStartDate, R.id.offerExpDate});

            offersListView.setAdapter(adapter);
        }
    }



//    private class OffersSlidePagerAdapter extends android.support.v4.app.FragmentStatePagerAdapter {
//
//
//        public OffersSlidePagerAdapter(android.support.v4.app.FragmentManager fragmentManager) {
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


}
