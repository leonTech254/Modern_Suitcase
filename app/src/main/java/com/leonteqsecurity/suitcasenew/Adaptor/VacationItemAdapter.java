package com.leonteqsecurity.suitcasenew.Adaptor;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.leonteqsecurity.suitcasenew.Models.VacationItem;
import com.leonteqsecurity.suitcasenew.R;

import java.util.List;

public class VacationItemAdapter extends RecyclerView.Adapter<VacationItemAdapter.ViewHolder> {

    private List<VacationItem> itemList;

    public VacationItemAdapter(List<VacationItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vacation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VacationItem item = itemList.get(position);

        holder.textViewItemName.setText(item.getItemName());
        holder.textViewItemDescription.setText(item.getItemDescription());
        holder.textViewItemPrice.setText(item.getItemPrice());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemName, textViewItemDescription, textViewItemPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewItemDescription = itemView.findViewById(R.id.textViewItemDescription);
            textViewItemPrice = itemView.findViewById(R.id.textViewItemPrice);
        }
    }
}
