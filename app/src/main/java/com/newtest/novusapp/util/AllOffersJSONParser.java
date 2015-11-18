package com.newtest.novusapp.util;

import com.newtest.novusapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ROOT-PC on 13.10.2015.
 */
public class AllOffersJSONParser {

    public static final String CATEGORY_ID = "id_category";
    public static final String CATEGORIES = "categories";
    public static final String NAME="name";
    public static final String IMAGE_URL = "img_url";

    // Receives a JSONObject and returns a list
    public List<HashMap<String,Object>> parse(JSONObject jObject){

        JSONArray jCategories = null;
        try {
            // Retrieves all the elements in the 'countries' array
            jCategories = jObject.getJSONArray(CATEGORIES);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Invoking getCountries with the array of json object

        // where each json object represent a category
        return getCategories(jCategories);
    }


    private List<HashMap<String, Object>> getCategories(JSONArray jCategories){
        int categoryCount = jCategories.length();
        List<HashMap<String, Object>> categoriesList = new ArrayList<HashMap<String,Object>>();
        HashMap<String, Object> category = null;

        // Taking each country, parses and adds to list object
        for(int i=0; i<categoryCount;i++){
            try {
                // Call getCountry with country JSON object to parse the country
                category = getCategory((JSONObject) jCategories.get(i));
                categoriesList.add(category);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return categoriesList;
    }

    // Parsing the Country JSON object
    private HashMap<String, Object> getCategory(JSONObject jCategory){

        HashMap<String, Object> category = new HashMap<String, Object>();
        String categoryName = "";
        String categoryImage="";

        try {
            categoryName = jCategory.getString(NAME);
            categoryImage = jCategory.getString(IMAGE_URL);
//            language = jCountry.getString("language");
//            capital = jCountry.getString("capital");
//            currencyCode = jCountry.getJSONObject("currency").getString("code");
//            currencyName = jCountry.getJSONObject("currency").getString("currencyname");

            category.put(NAME,categoryName);
            category.put("img", R.drawable.blank);
            category.put(IMAGE_URL,categoryImage);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return category;
    }
}
