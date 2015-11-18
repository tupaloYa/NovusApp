package com.newtest.novusapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newtest.novusapp.MainActivity;
import com.newtest.novusapp.R;

/**
 * Created by ROOT-PC on 08.10.2015.
 */
public class OfferItemFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String SAVE_PAGE_NUMBER = "save_page_number";
    int pageNumber;

    public static OfferItemFragment newInstance(int page) {
        OfferItemFragment pageFragment = new OfferItemFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    private ImageView offerProductImageView;
    private TextView offerProductTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

        int savedPageNumber = -1;
        if (savedInstanceState != null) {
            savedPageNumber = savedInstanceState.getInt(SAVE_PAGE_NUMBER);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.offer_item,null);

//        offerProductImageView = (ImageView) getActivity().findViewById(R.id.offerImage);
        offerProductTextView = (TextView) getActivity().findViewById(R.id.offerText);


        return view;
    }
}
