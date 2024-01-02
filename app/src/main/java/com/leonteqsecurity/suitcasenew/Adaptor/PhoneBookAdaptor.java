package com.leonteqsecurity.suitcasenew.Adaptor;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.leonteqsecurity.suitcasenew.Database.DbHelper;
import com.leonteqsecurity.suitcasenew.Models.PhoneBookModel;
import com.leonteqsecurity.suitcasenew.Models.User;
import com.leonteqsecurity.suitcasenew.Models.VacationItem;
import com.leonteqsecurity.suitcasenew.R;
import com.leonteqsecurity.suitcasenew.Utils.MessageParse;
import com.leonteqsecurity.suitcasenew.Utils.SendSms;

import java.util.List;

public class PhoneBookAdaptor extends RecyclerView.Adapter<PhoneBookAdaptor.PhoneBookHolder> {

    private List<PhoneBookModel> phoneBookModelList;
    private Context context;
    private TextView delegate_item_name;
    private  TextView delegate_item_price;
    private  TextView delegate_item_description;
    private  TextView delegate_item_location;
    private  TextView delegate_item_share_with;

    private Button delegate_send_sms;
    private  int dbIdItem;
    private DbHelper databaseHelper;
    private VacationItem vacationItem;

    public PhoneBookAdaptor(List<PhoneBookModel> phoneBookModelList, Context context, VacationItem vacationItem) {
        this.phoneBookModelList = phoneBookModelList;
        this.context = context;
       this.vacationItem=vacationItem;
    }

    @NonNull
    @Override
    public PhoneBookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_contact_design, parent, false);
        return new PhoneBookHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneBookHolder holder, int position) {
        PhoneBookModel phoneBookModel=phoneBookModelList.get(position);
        holder.phone_number.setText(phoneBookModel.getPhon_number().replace(" ","").replace("-",""));
        holder.phone_name.setText(phoneBookModel.getPhone_name());
        holder.assign_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFormDialog(phoneBookModel.getPhon_number(),phoneBookModel.getPhone_name());
            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void showFormDialog(String phone_number, String phone_name) {
        System.out.println(vacationItem);
        databaseHelper=new DbHelper(context);
        List<User> userAccountList=databaseHelper.getAllUsers();
        User userAccount= userAccountList.get(0);
        String myUsername=userAccount.getUsername();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context,R.style.CustomDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.pop_up_delegate_item_layout, null);
        delegate_item_share_with =dialogView.findViewById(R.id.contact_to_share);
        delegate_item_name=dialogView.findViewById(R.id.delegate_item_name);
        delegate_item_name.setText(vacationItem.getItemName());
        delegate_item_location=dialogView.findViewById(R.id.delegate_item_location);
        delegate_item_location.setText(vacationItem.getContryname());
        delegate_item_price=dialogView.findViewById(R.id.delegate_item_price);
        delegate_item_price.setText(vacationItem.getItemPrice());
        delegate_item_description=dialogView.findViewById(R.id.delegate_item_description);
        delegate_item_description.setText(vacationItem.getItemDescription());
        delegate_item_share_with.setText("Share with: "+ phone_name);
        delegate_send_sms= dialogView.findViewById(R.id.delegate_send_sms);
        dialogBuilder.setView(dialogView);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        delegate_send_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SendSms sendSms= new SendSms();
                MessageParse messageParse= new MessageParse();
               String message= messageParse.message(myUsername,vacationItem.getItemName(),vacationItem.getItemDescription(),vacationItem.getItemPrice(),vacationItem.getContryname());
                sendSms.sendMessage(context, phone_number, message, new SendSms.OnMessageSend() {

                    @Override
                    public void messageSent(String message) {
                        Toast.makeText(context, "item shared successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void messageFailed(String message, String errorMessage) {
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();

                    }
                });
//send sms
                dialog.dismiss();
            }
        });





    }

    @Override
    public int getItemCount() {
        return phoneBookModelList.size();
    }


    public  static  class  PhoneBookHolder extends  RecyclerView.ViewHolder{
        private TextView phone_name;
        private  TextView phone_number;
        private TextView assign_item_button;


        public PhoneBookHolder(@NonNull View itemView) {
            super(itemView);
            phone_name=itemView.findViewById(R.id.phone_book_name);
            phone_number=itemView.findViewById(R.id.phone_book_number);
            assign_item_button=itemView.findViewById(R.id.assign_item_button);



        }
    }
}
