package com.leonteqsecurity.suitcasenew.Utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class CopyData {

    public  static  void copyText(Context context,String text)
    {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        // Create a ClipData object with the text to copy
        ClipData clipData = ClipData.newPlainText("Copied Text", text);

        // Set the data to the clipboard
        clipboardManager.setPrimaryClip(clipData);

    }
}
