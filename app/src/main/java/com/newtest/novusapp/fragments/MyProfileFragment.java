package com.newtest.novusapp.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newtest.novusapp.MainActivity;
import com.newtest.novusapp.R;
  import com.newtest.novusapp.View.SlidingTabLayout;
import com.newtest.novusapp.model.Customer;
import com.newtest.novusapp.util.JSONService;
import com.newtest.novusapp.util.SQLiteHandler;
import com.newtest.novusapp.util.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;


/**
 * Created by  on 05.08.2015.
 */
public class MyProfileFragment extends Fragment {

    private TextView firstNameString;
    private TextView patrNameString;
    private TextView lastNameString;
    private TextView birthdayString;

    private JSONArray customerJSONArray;



    public static final String CUSTOMER = "customer";
    public static final String CUSTOMER_ID="user_id";
    public static final String CUSTOMER_FIRST_NAME="first_name";
    public static final String CUSTOMER_PATR_NAME="patr_name";
    public static final String CUSTOMER_LAST_NAME="last_name";
    public static final String CUSTOMER_GENDER="gender";
    public static final String CUSTOMER_BDAY="birthday";

    public static String vkProfile;
    public static String fbProfile;


    public MyProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.my_profile_fragment, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(R.string.myprofile);

//        HashMap<String, String> userDetails = sqLiteHandler.getUserDetails();
//        getUserDetails

//        Customer customer = null;

        ClientProfileParseTask task = new ClientProfileParseTask();
        task.execute();
        return rootView;
    }

    class ClientProfileParseTask extends AsyncTask<Void,Void,Void>{
        private String url = "http://192.168.1.250/get_customer_info.php?email=";
        private SQLiteHandler db;
        String customerId;
        String customerFname;
        String customerPname;
        String customerLname;
        String customerGender;
        String customerBDay;

        private String getEMail(){
            db = new SQLiteHandler(getActivity().getApplicationContext());
            HashMap<String,String> emails;
            emails = db.getUserEmail();
            String url = this.url;

            String emailUrl = emails.get("email");
            if (emailUrl != null)
                url = url.concat(emailUrl);
            else {
                Log.d("oops","no email found");
            }
            return url;
        }


        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = null;
            try {
                String urlmail = getEMail();
                if (urlmail  != null) {
                    Log.d("request:","true");
                    jsonStr = sh.makeServiceCall(urlmail, ServiceHandler.GET);
                }
                else {
                    System.err.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (jsonStr != null) {
                Log.d("json:",jsonStr.toString());
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    customerJSONArray = jsonObj.getJSONArray(CUSTOMER);
                    JSONObject c = customerJSONArray.getJSONObject(0);
                    customerFname = c.getString(CUSTOMER_FIRST_NAME);
                    Log.d("customerFname",customerFname);
                    customerPname = c.getString(CUSTOMER_PATR_NAME);
                    Log.d("customerPname",customerPname);
                    customerLname = c.getString(CUSTOMER_LAST_NAME);
                    Log.d("customerLname",customerLname);

                    customerBDay = c.getString(CUSTOMER_BDAY);
                    Log.d("customerGender",customerBDay);
//                    customerBudget = c.getString(CUSTOMER_BUDGET);
                    // looping through All Contacts
//                    for (int i = 0; i < customerJSONArray.length(); i++) {
//
//                        // tmp hashmap for single purchase
//                        HashMap<String, String> purchase = new HashMap<String, String>();
//
//                        // adding each child node to HashMap key => value
//                        purchase.put(PURCHASE_ID, id);
//                        purchase.put(NAME, name);
//                        purchase.put(QUANTITY, quantity);
//                        purchase.put(PRICE, price);
//                        purchase.put(PURCHASE_DATE,purchaseDate);
//
//                        // adding contact to contact list
//                        purchaseList.add(purchase);
//                    }
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


            firstNameString = (TextView) getActivity().findViewById(R.id.nameString);
            patrNameString = (TextView) getActivity().findViewById(R.id.patrNameString);
            lastNameString = (TextView) getActivity().findViewById(R.id.lastNameString);
            birthdayString = (TextView) getActivity().findViewById(R.id.birthdayString);



            firstNameString.setText(customerFname);

            patrNameString.setText(customerPname);

            lastNameString.setText(customerLname);

            birthdayString.setText(customerBDay);

        }

    }


}