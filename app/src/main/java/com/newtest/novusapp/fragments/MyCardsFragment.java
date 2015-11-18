package com.newtest.novusapp.fragments;

/**
 * Created by  on 05.08.2015.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


import com.newtest.novusapp.AvailableCardsActivity;
import com.newtest.novusapp.MainActivity;
import com.newtest.novusapp.PersonalCardActivity;
import com.newtest.novusapp.R;

public class MyCardsFragment extends Fragment {

    public MyCardsFragment() {
    }

    private LinearLayout personalCardLayout;
    private LinearLayout availableCardsLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.my_cards_fragment, container,
                false);
        ((MainActivity) getActivity()).setActionBarTitle(R.string.mycard);

        personalCardLayout = (LinearLayout) rootView.findViewById(R.id.personalCardLayout);
        availableCardsLayout = (LinearLayout) rootView.findViewById(R.id.availableCardsLayout);

        personalCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), PersonalCardActivity.class);
                startActivity(in);
            }
        });

        availableCardsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), AvailableCardsActivity.class);
                startActivity(in);
            }
        });


        return rootView;
    }

}
