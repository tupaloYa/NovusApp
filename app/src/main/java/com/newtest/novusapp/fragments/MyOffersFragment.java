package com.newtest.novusapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.newtest.novusapp.MainActivity;
import com.newtest.novusapp.R;
import com.newtest.novusapp.ScanActivity;
import com.newtest.novusapp.adapters.MyOffersAdapter;
import com.newtest.novusapp.adapters.PersonalOffersAdapter;
import com.newtest.novusapp.model.PersonalOffer;
import com.newtest.novusapp.model.PersonalOffersList;
import com.newtest.novusapp.util.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ROOT-PC on 08.10.2015.
 */
public class MyOffersFragment extends Fragment {

    private ListView myOffersListView;
    private ImageView gotoScanBtn;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyOffersAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.my_offers_fragment, container,
                false);
        ((MainActivity)getActivity()).setActionBarTitle(R.string.mypurchase);

        gotoScanBtn = (ImageView) rootView.findViewById(R.id.gotoScanBtn);
        gotoScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ScanActivity.class);
                startActivity(i);
            }
        });

//        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_offers_recycler_view);
//        adapter = new MyOffersAdapter(PersonalOffer.createOffersList(4));
//        mRecyclerView.setAdapter(adapter);
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(mLayoutManager);


        return rootView;
    }

//    private class MyOffersListItemAdapter extends BaseAdapter {
//        Context ctx;
//        private  LayoutInflater inflater = null;
//        ArrayList<PersonalOffer> offers;
//
//        public MyOffersListItemAdapter(Context context) {
//            ctx = context;
//        }
//
//        @Override
//        public int getCount() {
//            return offers.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return offers.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View vi = convertView;
//            if (vi == null)
//                vi = inflater.inflate(R.layout.my_offer_item, null);
//            TextView myOfferProductNameTextView = (TextView) vi.findViewById(R.id.myOfferProductName);
//            TextView myOfferDiscountNameTextView = (TextView) vi.findViewById(R.id.myOfferDiscountName);
//            ImageView myOfferQrCode =  (ImageView) vi.findViewById(R.id.myOfferQrCode);
//
//
//            return vi;
//        }
//    }





}
