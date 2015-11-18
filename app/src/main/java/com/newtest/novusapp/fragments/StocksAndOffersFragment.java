package com.newtest.novusapp.fragments;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
//import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.newtest.novusapp.MainActivity;
import com.newtest.novusapp.OffersActivity;
import com.newtest.novusapp.R;

import com.newtest.novusapp.StocksActivity;
import com.newtest.novusapp.util.ProductOffersJSONParser;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by  on 05.08.2015.
 */
public class StocksAndOffersFragment extends Fragment {

    protected String[] mDataset;

    public static String[] imgURLs;
    public CopyOnWriteArrayList<HashMap<String,Object>> offerItems;

  

    LinearLayout stocksLayout;
    LinearLayout offersLayout;

    ImageButton stocksBtn;
    Button offersBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//
        View rootView = inflater.inflate(R.layout.stocks_and_offers_fragment, container,
                false);
        ((MainActivity) getActivity()).setActionBarTitle(R.string.myoffers);
        stocksBtn = (ImageButton) rootView.findViewById(R.id.stocksBtn);
        offersBtn = (Button) rootView.findViewById(R.id.offersBtn);

//        stocksLayout = (LinearLayout) rootView.findViewById(R.id.stocksLayout);
//        offersLayout = (LinearLayout) rootView.findViewById(R.id.offersLayout);
//
        stocksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), StocksActivity.class);
                startActivity(i);
            }
        });
        offersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), OffersActivity.class);
                startActivity(i);
            }
        });


        return rootView;
    }




}
