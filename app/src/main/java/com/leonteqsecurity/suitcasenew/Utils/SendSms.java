package com.leonteqsecurity.suitcasenew.Utils;

import android.content.Context;
import android.telephony.SmsManager;

import java.util.ArrayList;

public class SendSms {

    public interface OnMessageSend{
        void messageSent(String message);
        void messageFailed(String message, String errorMessage);
    }

    public void sendMessage(Context context, String phoneNumber, String message, OnMessageSend callback) {
        try {
            SmsManager sms = SmsManager.getDefault();

            if (message.length() <= 160) {
                sms.sendTextMessage(phoneNumber, null, message, null, null);
                callback.messageSent(message);
            } else {
                ArrayList<String> messageParts = sms.divideMessage(message);
                for (String part : messageParts) {
                    sms.sendTextMessage(phoneNumber, null, part, null, null);
                }
                callback.messageSent(message);
            }
        } catch (Exception e) {
            callback.messageFailed(message, e.getMessage());
        }
    }
}
