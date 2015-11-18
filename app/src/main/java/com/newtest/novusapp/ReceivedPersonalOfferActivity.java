package com.newtest.novusapp;

import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.newtest.novusapp.fragments.receivedOffer.DiscountFragment;

import com.newtest.novusapp.util.SQLiteHandler;

import org.json.JSONArray;

import java.util.HashMap;

/**
 * Created by ROOT-PC on 15.10.2015.
 */
public class ReceivedPersonalOfferActivity extends FragmentActivity {

    public static final String MY_OFFER = "myoffer";
    public static final String DISCOUNT = "ratio";
    public static final String NAME="name";

    private ImageView offerProductImage;
    private TextView offerProductName;

    public String offerDiscount;
    public String offerName;

    private Fragment discountFragment;

    private FragmentTransaction ft;

    private SQLiteHandler dbHandler;
    public static String url = "http://192.168.1.250/generate_my_offer.php?email=";
    JSONArray myOfferJSONArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_offer);
        offerProductImage = (ImageView) findViewById(R.id.offerProductImageView);
//        showCodeButton = (Button) findViewById(R.id.saveOfferCodeBtn);
//
//        showCodeButton.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(ReceivedPersonalOfferActivity.this,ScanActivity.class);
//                startActivity(i);
//
//
//            }
//        });

        HashMap<String,String> userEmail;
        dbHandler = new SQLiteHandler(getApplicationContext());
        userEmail = dbHandler.getUserEmail();

        String email = userEmail.get("email");
        String urlEmail = url.concat(email);
        Log.d("email request", urlEmail);
        url = urlEmail;


        discountFragment = new DiscountFragment();

        ft = getSupportFragmentManager().beginTransaction();

//        ft.setCustomAnimations(R.animator.slide_in_left,0);
//        ft.replace(R.id.discountFrame, discountFragment);
//        ft.addToBackStack(null);
//        ft.commit();


//        new GeneratePersonalOfferTask().execute();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ReceivedPersonalOfferActivity.this, MainActivity.class);
        startActivity(intent);
    }



}
