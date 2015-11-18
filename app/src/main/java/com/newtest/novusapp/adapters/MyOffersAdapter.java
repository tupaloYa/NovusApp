package com.newtest.novusapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newtest.novusapp.R;
import com.newtest.novusapp.model.PersonalOffer;

import java.util.List;

/**
 * Created by Monster on 14.11.2015.
 */
public class MyOffersAdapter extends RecyclerView.Adapter<MyOffersAdapter.ViewHolder> {

    private List<PersonalOffer> personalOffersList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout offerLayout;
        public TextView nameTextView;
        public TextView offerTextView;
        public ImageView offerImg;

        public ViewHolder(View v) {
            super(v);

            nameTextView = (TextView) v.findViewById(R.id.myOfferProductName);
            offerTextView = (TextView) v.findViewById(R.id.myOfferDiscountName);
            offerImg = (ImageView) v.findViewById(R.id.myOfferQrCode);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyOffersAdapter(List<PersonalOffer> offers) {
        personalOffersList = offers;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_offer_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PersonalOffer offer = personalOffersList.get(position);
        TextView nameView = holder.nameTextView;
        TextView offerView = holder.offerTextView;
        ImageView imageView = holder.offerImg;

        nameView.setText(R.string.showOffer);
        offerView.setText(R.string.saveCodeText);



    }

    @Override
    public int getItemCount() {
        return personalOffersList.size();
    }
}
