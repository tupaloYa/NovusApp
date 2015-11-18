package com.newtest.novusapp.util;

import com.newtest.novusapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ROOT-PC on 08.10.2015.
 */
public class ProductOffersJSONParser {

    // public static final String CATEGORY_ID = "id_offer";

    public static final String OFFERS = "offers";
    public static final String OFFER_ID = "offer_id";
    public static final String OFFER_NAME="name";
    public static final String START_DATE="start_date";
    public static final String EXP_DATE ="exp_date";
    public static final String DESCRITPION = "description";
    public static final String IMAGE_URL = "img_url";

    // Receives a JSONObject and returns a list
    public List<HashMap<String,Object>> parse(JSONObject jObject){

        JSONArray jOffers = null;
        try {
            // Retrieves all the elements in the 'countries' array
            jOffers = jObject.getJSONArray(OFFERS);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Invoking getCountries with the array of json object

        // where each json object represent a offer
        return getOffers(jOffers);
    }


    private List<HashMap<String, Object>> getOffers(JSONArray jOffers){
        int offerCount = jOffers.length();
        List<HashMap<String, Object>> categoriesList = new ArrayList<HashMap<String,Object>>();
        HashMap<String, Object> offer = null;

        // Taking each country, parses and adds to list object
        for(int i=0; i<offerCount;i++){
            try {
                // Call getCountry with country JSON object to parse the country
                offer = getOffer((JSONObject) jOffers.get(i));
                categoriesList.add(offer);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return categoriesList;
    }

    // Parsing the Country JSON object
    private HashMap<String, Object> getOffer(JSONObject jCategory){

        HashMap<String, Object> offer = new HashMap<String, Object>();
        String offerName = "";
        String offerImage="";
        String startDate = "";
        String expDate = "";

        try {
            offerName = jCategory.getString(OFFER_NAME);
            offerImage = jCategory.getString(IMAGE_URL);

            offer.put(OFFER_NAME,offerName);
            offer.put(IMAGE_URL,offerImage);

            offer.put(START_DATE,startDate);
            offer.put(EXP_DATE,expDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return offer;
    }
}
