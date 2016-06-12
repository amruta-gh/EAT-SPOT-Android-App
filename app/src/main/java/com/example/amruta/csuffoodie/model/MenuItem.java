package com.example.amruta.csuffoodie.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/*
CREATE TABLE "MenuItem"
    ("id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE ,
    "restaurantId" INTEGER NOT NULL ,
    "price" DOUBLE NOT NULL ,
    "itemName" VARCHAR NOT NULL ,
    "available" BOOL NOT NULL  DEFAULT True,
    "image" VARCHAR NOT NULL  DEFAULT default_image)
 */
public class MenuItem {
    int id;
    int restaurantId;
    double price;
    String itemName;
    boolean available;
    String image;

    public MenuItem() {
        this.id = -1;
        this.restaurantId = -1;
        this.price = (float) 0.0;
        this.itemName = null;
        this.available = true;
        this.image = "default_menu_image";
    }
    public MenuItem(int Id,
                    int RestaurantId,
                    float Price,
                    String ItemName,
                    boolean Available,
                    String Image)
    {
        this.id = Id;
        this.restaurantId = RestaurantId;
        this.price = Price;
        this.itemName = ItemName;
        this.available = Available;
        this.image = Image;
    }

    public MenuItem (MenuItem m) {
        this.id = m.getId();
        this.restaurantId = m.getRestaurantId();
        this.price = m.getPrice();
        this.itemName = m.getItemName();
        this.available = m.isAvailable();
        this.image = m.getImage();
    }

    @Override
    public String toString() {
        return "Restaurant Info: { " +
                " id: " + this.id
                + " restaurantId: " + this.restaurantId
                + " price " + this.price
                + " itemName " + this.itemName
                + " available " + this.available
                + " image " + this.image
                + " }";
        //return super.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public double getPrice() {
        //return with only 2 level of precision
        return twoDigitPrecisionValue(price);
    }

    public void setPrice(double price) {
        this.price = twoDigitPrecisionValue(price);
    }

    //return with only 2 level of precision
    public double twoDigitPrecisionValue(double value){
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
