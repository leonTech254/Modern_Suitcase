package com.leonteqsecurity.suitcasenew.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.leonteqsecurity.suitcasenew.Models.VacationItem;
import com.leonteqsecurity.suitcasenew.R;
import com.leonteqsecurity.suitcasenew.VacationOneItemScreen;

import java.util.List;

public class VacationItemAdapter extends RecyclerView.Adapter<VacationItemAdapter.ViewHolder> {

    private List<VacationItem> itemList;

    private Context context;

    public VacationItemAdapter(Context con,List<VacationItem> itemList) {
        this.itemList = itemList;
        this.context=con;
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
        holder.textViewItemPrice.setText("$ "+item.getItemPrice());
        holder.ContryItem.setText(item.getContryname());
        holder.ItemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, VacationOneItemScreen.class);
                intent.putExtra("ProductId",item.getItemName());
                Toast.makeText(context, "selected "+item.getItemName(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemName, textViewItemDescription, textViewItemPrice,ContryItem;
        LinearLayout ItemCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewItemPrice = itemView.findViewById(R.id.textViewItemPrice);
            ContryItem = itemView.findViewById(R.id.ContryItem);
            ItemCard=itemView.findViewById(R.id.ItemCard);
        }
    }
}
