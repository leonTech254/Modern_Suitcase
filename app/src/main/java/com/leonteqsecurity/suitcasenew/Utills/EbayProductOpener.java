package com.leonteqsecurity.suitcasenew.Utills;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.content.pm.PackageManager;

public class EbayProductOpener {

    public static void openProductPage(Context context, String productId) {
        // Construct the eBay product page URL
        String ebayProductUrl = "https://www.ebay.com/itm/" + productId;

        // Create an Intent to open the URL
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ebayProductUrl));

        // Check if the eBay app is installed, and if not, open the URL in a web browser
        if (isEbayAppInstalled(context)) {
            intent.setPackage("com.ebay.mobile"); // Package name for the eBay app
        }

        // Start the activity
        context.startActivity(intent);
    }

    private static boolean isEbayAppInstalled(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.ebay.mobile", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void searchProductByName(Context context, String productName) {
        // Construct the eBay search URL based on the product name
        String ebaySearchUrl = "https://www.ebay.com/sch/i.html?_nkw=" + Uri.encode(productName);

        // Create an Intent to open the URL
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ebaySearchUrl));

        // Check if the eBay app is installed, and if not, open the URL in a web browser
        if (isEbayAppInstalled(context)) {
            intent.setPackage("com.ebay.mobile"); // Package name for the eBay app
        }

        // Start the activity
        context.startActivity(intent);
    }
}
