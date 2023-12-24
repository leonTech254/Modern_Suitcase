package com.leonteqsecurity.suitcasenew.Adaptor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leonteqsecurity.suitcasenew.Models.CardItemVacations;
import com.leonteqsecurity.suitcasenew.R;
import com.leonteqsecurity.suitcasenew.VacationItemScreen;

import java.io.Serializable;
import java.util.List;


public class VacationAdaptor extends ArrayAdapter<CardItemVacations> {

    private final Context context;
    private final CardItemVacations[] cardItems;

    public VacationAdaptor(Context context, CardItemVacations[] cardItems) {
        super(context, R.layout.card_design_vacation, cardItems);
        this.context = context;
        this.cardItems = cardItems;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") final View rowView = inflater.inflate(R.layout.card_design_vacation, parent, false);

        TextView titleTextView = rowView.findViewById(R.id.chip);
        ImageView imageView=rowView.findViewById(R.id.cardImageView);

        titleTextView.setText(cardItems[position].getVacationLocation());
        imageView.setImageResource(Integer.parseInt(cardItems[position].getImageBackground()));
        System.out.println(cardItems[position].getImageBackground());

        // Set an OnClickListener for the card item
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle card click event here
                String clickedCardTitle = cardItems[position].getVacationLocation();
                Toast.makeText(context, "Clicked on " + clickedCardTitle, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, VacationItemScreen.class);
                int vacationId=cardItems[position].getId();
                intent.putExtra("vacationId",String.valueOf(cardItems[position].getId()));
                context.startActivity(intent);
            }
        });

        return rowView;
    }


}