package com.leonteqsecurity.suitcasenew.Utills;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class AmazonProductOpener {

    public static void searchProductByName(Context context, String productName) {
        // Construct the Amazon search URL based on the product name
        String amazonSearchUrl = "https://www.amazon.com/s?k=" + Uri.encode(productName);

        // Create an Intent to open the URL
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(amazonSearchUrl));

        // Check if the Amazon app is installed, and if not, open the URL in a web browser
        if (isAmazonAppInstalled(context)) {
            intent.setPackage("com.amazon.mobile.shopping"); // Package name for the Amazon app
        }

        // Start the activity
        context.startActivity(intent);
    }

    private static boolean isAmazonAppInstalled(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.amazon.mobile.shopping", 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
