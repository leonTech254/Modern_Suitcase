package com.leonteqsecurity.suitcasenew.Utils;

public class MessageParse {

    public String message( String myusername,String itemname, String itemDescription, String itemPrice, String itemShopLocation) {
        // Create a message for item delegation based on the parameters
        String message = "Hi " + ",\r\n";
        message += myusername + " has delegated an item to you for our holiday planning:\r\n";
        message += "Item Name: "+itemname+"\r\n";
        message += "Description: " + itemDescription + "\r\n";
        message += "Price: " + itemPrice +" USD"+ "\r\n";
//        message += "Shop Location: " + itemShopLocation + "\n";
        return message;
    }
}
