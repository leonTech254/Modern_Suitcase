package com.leonteqsecurity.suitcasenew.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leonteqsecurity.suitcasenew.Models.CardItemVacations;
import com.leonteqsecurity.suitcasenew.R;


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

        titleTextView.setText(cardItems[position].getVacationLocation());

        // Set an OnClickListener for the card item
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle card click event here
                String clickedCardTitle = cardItems[position].getVacationLocation();
                Toast.makeText(context, "Clicked on " + clickedCardTitle, Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;
    }


}