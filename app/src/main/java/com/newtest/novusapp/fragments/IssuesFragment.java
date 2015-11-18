package com.newtest.novusapp.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.newtest.novusapp.MainActivity;
import com.newtest.novusapp.R;

/**
 * Created by Yaroslav on 12.11.2015.
 */
public class IssuesFragment extends Fragment {

    final private int NUM_STARS = 5;
    private float step = 0.5f;
    private float rat = 1.0f;
    RatingBar ratingBar;
    Button assessment;

    public IssuesFragment(){
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.issues_fragment,null);

        ((MainActivity)getActivity()).setActionBarTitle(R.string.issue);

        ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        ratingBar.setNumStars(NUM_STARS);
        ratingBar.setStepSize(step);
        ratingBar.setRating(rat);

        assessment = (Button) v.findViewById(R.id.btnAssessment);
        assessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });

        return v;

    }

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle(R.string.shareDialogTitle);
//        // Get the layout inflater
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//
//        // Inflate and set the layout for the dialog
//        // Pass null as the parent view because its going in the dialog layout
//        builder.setView(inflater.inflate(R.layout.share_personal_card_fragment, null))
//                // Add action buttons
//                .setPositiveButton(R.string.share, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        // sign in the user ...
//                    }
//                })
//                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        SharePersonalCardFragment.this.getDialog().cancel();
//                    }
//                });
//        return builder.create();
//    }




}
