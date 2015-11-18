package com.newtest.novusapp.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.*;
import android.os.Bundle;


import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newtest.novusapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ROOT-PC on 08.10.2015.
 */
public class OfferByCategoryFragment extends Fragment {

    public static final int PAGE_COUNT = 3;
    ViewPager pager;
    PagerAdapter pagerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.offers_category_rec_item, container,
                false);
        List<Fragment> fragments = getFragments();
//        pager = (ViewPager) getActivity().findViewById(R.id.pager);
//        pagerAdapter = new OffersCategoryFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
//        pager.setAdapter(pagerAdapter);

        return rootView;
    }
    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<Fragment>();

        fList.add(OfferItemFragment.newInstance(1));
        fList.add(OfferItemFragment.newInstance(2));
        fList.add(OfferItemFragment.newInstance(3));

        return fList;
    }

    private class OffersCategoryFragmentPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments;

        public OffersCategoryFragmentPagerAdapter(android.support.v4.app.FragmentManager fm,List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;

        }

        @Override
        public Fragment getItem(int position) {


            return OfferItemFragment.newInstance(position);
        }

        @Override
        public int getCount() {


            return PAGE_COUNT;
        }
    }
}
