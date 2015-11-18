package com.newtest.novusapp;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.newtest.novusapp.util.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Monster on 13.11.2015.
 */
public class PurchaseHistoryActivity extends Activity {


    public static final String PURCHASE = "purchase";
    public static final String PURCHASE_ID = "id_purchase";
    public static final String NAME="name";
    public static final String QUANTITY="quantity";
    public static final String PRICE="price";
    public static final String PURCHASE_DATE="purchase_date";

    JSONArray purchaseJSONArray = null;

    public String url = "http://192.168.1.250/get_purchase_history.php?email=";


    List<HashMap<String,String>> purchaseList = new ArrayList<>();
    ListView purchaseListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);
        purchaseListView = (ListView) findViewById(R.id.purchaseList);

        GetPurchaseTask getPurchaseTask = new GetPurchaseTask();
        getPurchaseTask.execute();

    }

    class GetPurchaseTask extends AsyncTask<Void,Void,Void> {


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
                    purchaseJSONArray = jsonObj.getJSONArray(PURCHASE);

                    // looping through
                    for (int i = 0; i < purchaseJSONArray.length(); i++) {

                        JSONObject c = purchaseJSONArray.getJSONObject(i);

                        String name = c.getString(NAME);
                        String quantity = c.getString(QUANTITY);
                        String price = c.getString(PRICE);
                        String purchaseDate = c.getString(PURCHASE_DATE);
                        price = price ;
                        // tmp hashmap for single purchase
                        HashMap<String, String> purchase = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        purchase.put(NAME, name);
                        purchase.put(QUANTITY, quantity);
                        purchase.put(PRICE, price);
                        purchase.put(PURCHASE_DATE,purchaseDate);

                        // adding contact to contact list
                        purchaseList.add(purchase);
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
                    getApplicationContext(), purchaseList,
                    R.layout.purchase_list_item, new String[]{NAME, QUANTITY,
                    PRICE, PURCHASE_DATE}, new int[]{R.id.productName,
                    R.id.productQuantity, R.id.productPrice,R.id.purchaseDate});

            purchaseListView.setAdapter(adapter);
        }
    }



}
