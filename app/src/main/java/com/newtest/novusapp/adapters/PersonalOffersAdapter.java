package com.newtest.novusapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.newtest.novusapp.R;
import com.newtest.novusapp.model.PersonalOffer;
import com.newtest.novusapp.model.PersonalOffersList;

import java.util.ArrayList;

/**
 * Created by ROOT-PC on 28.10.2015.
 */
public class PersonalOffersAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;

    TextView myOfferProductNameTextView;
    TextView myOfferDiscountNameTextView;
    ImageView myOfferQrCodeImageView;

    public PersonalOffersAdapter(Context context, ArrayList<PersonalOffer> offers) {
        ctx = context;
        PersonalOffersList.personalOffers = offers;
    }

    @Override
    public int getCount() {

        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.my_offer_item, parent, false);
        }
        myOfferProductNameTextView = (TextView) view.findViewById(R.id.myOfferProductName);
        myOfferDiscountNameTextView = (TextView) view.findViewById(R.id.myOfferDiscountName);

        myOfferQrCodeImageView = (ImageView) view.findViewById(R.id.myOfferQrCode);

        return view;
    }
}
