package com.newtest.novusapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ROOT-PC on 16.10.2015.
 */
public class AvailableCardsActivity extends AppCompatActivity {



    private Button listPurchaseBtn;
    private Button showBonusBtn;
    private Button removeBtn;

    TextView bonusPts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_cards);
        setActionBarTitle(R.string.avCards);

        listPurchaseBtn = (Button) findViewById(R.id.listPurchaseBtn);
        showBonusBtn = (Button) findViewById(R.id.showBonusBtn);
        removeBtn = (Button) findViewById(R.id.removeBtn);


       listPurchaseBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });

        showBonusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bonusPts = (TextView) findViewById(R.id.bonusPtsText);
                bonusPts.setText("?? ????? ????? 25 ???????? ??????");
            }
        });
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void setActionBarTitle(int actionBarTitle) {
        getSupportActionBar().setTitle(actionBarTitle);
    }

}
