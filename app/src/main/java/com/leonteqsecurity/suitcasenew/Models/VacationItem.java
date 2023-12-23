package com.leonteqsecurity.suitcasenew.Models;

public class VacationItem {
    private  int Id;
    private  String itemName;
    private String itemDescription;
    private  String itemPrice;
    private boolean isItemPurchased;

    private  String productImage;

    private String contryname;


    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getContryname() {
        return contryname;
    }

    public void setContryname(String contryname) {
        this.contryname = contryname;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public boolean isItemPurchased() {
        return isItemPurchased;
    }

    public void setItemPurchased(boolean itemPurchased) {
        isItemPurchased = itemPurchased;
    }
}
