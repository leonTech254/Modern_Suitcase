package com.leonteqsecurity.suitcasenew.Adaptor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.leonteqsecurity.suitcasenew.Database.DbHelper;
import com.leonteqsecurity.suitcasenew.Models.VacationItem;
import com.leonteqsecurity.suitcasenew.PhoneContactsScreens;
import com.leonteqsecurity.suitcasenew.R;
import com.leonteqsecurity.suitcasenew.VacationOneItemScreen;

import java.io.FileNotFoundException;
import java.net.URI;
import java.util.List;

public class VacationItemAdapter extends RecyclerView.Adapter<VacationItemAdapter.ViewHolder> {

    private List<VacationItem> itemList;
    private Context context;
    private GestureDetector gestureDetector;
    private Handler clickHandler;
    private int clickCount = 0;
    DbHelper dbHelper;
    private static final int REQUEST_PICK_IMAGE = 100;
    private VacationItem activeItem;

    public VacationItemAdapter(Context con, List<VacationItem> itemList) {
        this.itemList = itemList;
        this.context = con;
        this.clickHandler = new Handler(Looper.getMainLooper());
        this.dbHelper=new DbHelper(con);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vacation, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VacationItem item = itemList.get(position);
        holder.textViewItemName.setText(item.getItemName());
        holder.textViewItemPrice.setText("$ " + item.getItemPrice());
        holder.ContryItem.setText(item.getContryname());
        System.out.println(item);
        if(item.getProductImage()!=null && !item.getProductImage().isEmpty())
        {
            Uri imageUri = Uri.parse(item.getProductImage());
            try {
                holder.relativeBackground.setBackground(Drawable.createFromStream(
                        context.getContentResolver().openInputStream(imageUri), null));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else
        {
            holder.relativeBackground.setBackgroundResource(R.drawable.bg4);
        }

        holder.ItemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount++;
                clickHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (clickCount == 1) {
                            // Single click action
                            Intent intent = new Intent(context, VacationOneItemScreen.class);
                            intent.putExtra("ProductId", item.getItemName());
                            Toast.makeText(context, "Selected " + item.getItemName(), Toast.LENGTH_SHORT).show();
                            context.startActivity(intent);
                        } else if (clickCount == 2) {
                            // Double click action
                            handleDoubleTap(item);
                        }
                        clickCount = 0; // Reset click count
                    }
                }, 300); // Adjust the time threshold as needed
            }
        });

        holder.ItemCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context, PhoneContactsScreens.class);
                VacationItem vacationItem = new VacationItem();
                vacationItem.setItemDescription(item.getItemDescription());
                vacationItem.setItemName(item.getItemName());
                vacationItem.setItemPrice(item.getItemPrice());
                Gson gson = new Gson();
                intent.putExtra("vacationItem", gson.toJson(vacationItem));
                context.startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private void handleDoubleTap(VacationItem item) {
        Toast.makeText(context, "Double Clicked " + item.getProductImage(), Toast.LENGTH_SHORT).show();
        activeItem=item;
        // Launch gallery intent for image selection
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((Activity) context).startActivityForResult(galleryIntent, REQUEST_PICK_IMAGE);

    }

    // Handle the result of the gallery intent
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                Drawable drawable = Drawable.createFromStream(
                        context.getContentResolver().openInputStream(selectedImageUri), null);
                // Set the background of the selected item
                if (drawable != null) {
                    Toast.makeText(context, selectedImageUri.toString()+"fgc h", Toast.LENGTH_SHORT).show();
                    activeItem.setProductImage(selectedImageUri.toString());
                    System.out.println(activeItem);
                   int check= dbHelper.updateVacationItem(activeItem,activeItem.getItemDescription());
                    System.out.println(check);
                    System.out.println(activeItem.getItemDescription());


//                    ViewHolder viewHolder = // Get the ViewHolder for the selected item
//                            viewHolder.relativeBackground.setBackground(drawable);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemName, textViewItemDescription, textViewItemPrice, ContryItem;
        RelativeLayout relativeBackground;
        LinearLayout ItemCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewItemPrice = itemView.findViewById(R.id.textViewItemPrice);
            ContryItem = itemView.findViewById(R.id.ContryItem);
            ItemCard = itemView.findViewById(R.id.ItemCard);
            relativeBackground=itemView.findViewById(R.id.relativeBackground);


            gestureDetector = new GestureDetector(itemView.getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    clickCount = 2;
                    return true;
                }
            });

            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return gestureDetector.onTouchEvent(event);
                }
            });
        }
    }
}
