package com.newtest.novusapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.newtest.novusapp.fragments.SharePersonalCardFragment;


public class PersonalCardActivity extends ActionBarActivity {

    private Button listPurchaseBtn;
    private Button showBonusBtn;
    private Button shareBtn;
    private TextView bonusPts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_card);
        setActionBarTitle(R.string.personalCard);
        listPurchaseBtn = (Button) findViewById(R.id.listPurchaseBtn);
        showBonusBtn = (Button) findViewById(R.id.showBonusBtn);
        shareBtn = (Button) findViewById(R.id.shareBtn);

        listPurchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        showBonusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bonusPts = (TextView) findViewById(R.id.bonusPtsTextView);
                bonusPts.setText(R.string.bonus);
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                Fragment frag = manager.findFragmentByTag("SharePersonalCardFragment");
                if (frag != null) {
                    manager.beginTransaction().remove(frag).commit();
                }
                switch (v.getId()) {
                    case R.id.shareBtn:
                        DialogFragment shareCardFragment= new SharePersonalCardFragment();
                        shareCardFragment.show(getSupportFragmentManager(), "SharePersonalCardFragment");
                        break;

                }
            }
        });



    }

    public void setActionBarTitle(int actionBarTitle) {
        getSupportActionBar().setTitle(actionBarTitle);
    }





}
