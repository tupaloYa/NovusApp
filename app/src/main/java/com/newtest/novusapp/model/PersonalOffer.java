package com.newtest.novusapp.model;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.newtest.novusapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ROOT-PC on 25.09.2015.
 */
public class PersonalOffer {

    private String offerProductName;
    private String offerDiscount;

    private String offerImageURL;

    private static int lastOfferId = 0;


    public static List<PersonalOffer> createOffersList(int numOffers) {
        Resources res ;

        List<PersonalOffer> offers = new ArrayList<PersonalOffer>();


//        for (int i = 1; i <= numOffers; i++) {
//            offers.add(new PersonalOffer( "Product" + ++lastOfferId, "Discount"));
//        }

        return offers;
    }

    public PersonalOffer(String offerProductName, String offerDiscount, String offerImageURL ) {
        this.offerDiscount = offerDiscount;
        this.offerImageURL = offerImageURL;
        this.offerProductName = offerProductName;
    }

    public PersonalOffer(String offerProductName, String offerImageURL) {
        this.offerProductName = offerProductName;
        this.offerImageURL = offerImageURL;
    }


}
