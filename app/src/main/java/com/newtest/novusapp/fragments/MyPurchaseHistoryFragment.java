package com.newtest.novusapp.fragments;

/**
 * Created by  on 06.08.2015.
 */
import android.annotation.TargetApi;
import android.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.newtest.novusapp.MainActivity;
import com.newtest.novusapp.PurchaseHistoryActivity;
import com.newtest.novusapp.R;
import com.newtest.novusapp.model.Purchase;
import com.newtest.novusapp.util.SQLiteHandler;
import com.newtest.novusapp.util.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyPurchaseHistoryFragment extends Fragment {

    //    public static final String EMAIL =
    public static final String PURCHASE = "purchase";
    public static final String PURCHASE_ID = "id_purchase";
    public static final String NAME="name";
    public static final String QUANTITY="quantity";
    public static final String PRICE="price";
    public static final String PURCHASE_DATE="purchase_date";


    private SQLiteHandler dbHandler;

    private ListView shoppingListView;

    private ImageButton gotoPurchaseHistoryBtn;
    private ImageButton addItemBtn;

    ArrayAdapter<String> adapter;

    public View view;

    List shoppingList = new ArrayList<String>();

    public MyPurchaseHistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.my_purchase_history_fragment, container,
                false);
        ((MainActivity)getActivity()).setActionBarTitle(R.string.history);

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, shoppingList);

        gotoPurchaseHistoryBtn = (ImageButton) rootView.findViewById(R.id.gotoPurchaseHistoryBtn);
        addItemBtn = (ImageButton) rootView.findViewById(R.id.addToListBtn);
        shoppingListView = (ListView) rootView.findViewById(R.id.shoppingList);

        shoppingListView.setAdapter(adapter);
        gotoPurchaseHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PurchaseHistoryActivity.class);
                startActivity(i);
            }
        });
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText) getActivity().findViewById(R.id.newitemEdit);
                shoppingList.add(edit.getText().toString());
                edit.setText("");
                adapter.notifyDataSetChanged();

            }
        });




//        view = rootView;
//        shoppingListView = (ListView) rootView.findViewById(R.id.shoppingList);
//
//        dbHandler = new SQLiteHandler(getActivity().getApplicationContext());
//        HashMap<String,String> userEmail;
//
//        userEmail = dbHandler.getUserEmail();
//
//
//        String email = userEmail.get("email");
//        String urlEmail = this.url.concat(email);
//        Log.d("email request",urlEmail);
//        this.url = urlEmail;


//        String url = "http://192.168.1.81/get_purchase_history.php?email=";
//        new GetPurchaseTask().execute();
        return rootView;
    }

//    class GetPurchaseTask extends AsyncTask<Void,Void,Void>{
//
//
//        public String EMAIL;
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            ServiceHandler sh = new ServiceHandler();
//            String jsonStr = null;
//
//            Log.d("email url",url);
//            try {
//
//                jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            Log.i("Response: ", "> " + jsonStr);
//
//            if (jsonStr != null) {
//                try {
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//
//                    // Getting JSON Array node
//                    purchaseJSONArray = jsonObj.getJSONArray(PURCHASE);
//
//                    // looping through
//                    for (int i = 0; i < purchaseJSONArray.length(); i++) {
//
//                        JSONObject c = purchaseJSONArray.getJSONObject(i);
//
//                        String name = c.getString(NAME);
//                        String quantity = c.getString(QUANTITY);
//                        String price = c.getString(PRICE);
//                        String purchaseDate = c.getString(PURCHASE_DATE);
//                        price = price ;
//                        // tmp hashmap for single purchase
//                        HashMap<String, String> purchase = new HashMap<String, String>();
//
//                        // adding each child node to HashMap key => value
//                        purchase.put(NAME, name);
//                        purchase.put(QUANTITY, quantity);
//                        purchase.put(PRICE, price);
//                        purchase.put(PURCHASE_DATE,purchaseDate);
//
//                        // adding contact to contact list
//                        shoppingList.add(purchase);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                Log.e("ServiceHandler", "Couldn't get any data from the url");
//            }
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            ListAdapter adapter = new SimpleAdapter(
//                    getActivity(), shoppingList,
//                    R.layout.purchase_list_item, new String[]{NAME, QUANTITY,
//                    PRICE, PURCHASE_DATE}, new int[]{R.id.productName,
//                    R.id.productQuantity, R.id.productPrice,R.id.purchaseDate});
//
//            shoppingListView.setAdapter(adapter);
//        }
//    }

}
