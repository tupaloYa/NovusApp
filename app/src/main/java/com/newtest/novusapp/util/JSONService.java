package com.newtest.novusapp.util;

import android.util.Log;

import com.newtest.novusapp.model.Customer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ROOT-PC on 25.09.2015.
 */
public class JSONService  {
    public static final String CUSTOMERS = "categories";
    public static final String CUSTOMER_ID="category_id";
    public static final String CUSTOMER_FIRST_NAME="first_name";
    public static final String CUSTOMER_PATR_NAME="patr_name";
    public static final String CUSTOMER_LAST_NAME="last_name";
    public static final String CUSTOMER_GENDER="gender";
    public static final String CUSTOMER_BDAY="birthday";
    public static final String CUSTOMER_BUDGET="customer_budget";



    public static Customer getCustomerInfo() throws JSONException {
        JSONObject jsonObject = new JSONObject(URLService.getJsonURL("http://192.168.1.250/get_customer_info.php"));
        JSONArray dataJsonArray = jsonObject.getJSONArray("customer");
        Customer customer = new Customer();
        for(int i=0; i<dataJsonArray.length(); i++) {
            JSONObject dataObj = dataJsonArray.getJSONObject(i);
            String id = dataObj.getString("id_customer");
            String first_name = dataObj.getString("first_name");
            String patr_name = dataObj.getString("patr_name");
            String last_name = dataObj.getString("last_name");
            String gender = dataObj.getString("gender");
            String birthday = dataObj.getString("birthday");
            String customer_budget = dataObj.getString("customer_budget");

            customer.setCustomerId(Integer.parseInt(id));
            customer.setFirstName(first_name);
            customer.setPatrName(patr_name);
            customer.setLastName(last_name);
            customer.setGender(gender);
            customer.setBirthday(birthday);
            customer.setBudget(customer_budget);

            Log.d("customer name",customer.getFirstName());


            //Similarly you can extract for other fields.
        }

        return customer;

    }

}
