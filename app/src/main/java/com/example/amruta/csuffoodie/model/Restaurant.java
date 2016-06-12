package com.example.amruta.csuffoodie.model;

public class Restaurant {
        /*
    CREATE TABLE "Restaurant" (
        "id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,
        "Name" VARCHAR NOT NULL ,
        "Description" VARCHAR NOT NULL ,
        "Address" VARCHAR NOT NULL ,
        "Loc_Lat" DOUBLE,
        "Loc_Long" DOUBLE,
        "Timing" VARCHAR,
        "image" VARCHAR NOT NULL  DEFAULT default_image
        )
     */

    int Id;
    String Name;
    String Description;
    String Address;
    double Loc_Lat;
    double Loc_Long;
    String Timing;
    String Image;
    String Phone;

    public Restaurant() {
        this.Id = -1;
        this.Name = null;
        this.Description = null;
        this.Address = null;
        this.Loc_Lat = -1;
        this.Loc_Long = -1;
        this.Timing = null;
        this.Image = "default_rest_image";
        this.Phone = null;
    }

    public Restaurant(int Id,
                      String Name,
                      String Description,
                      String Address,
                      double Loc_Lat,
                      double Loc_Long,
                      String Timing,
                      String Image,
                      String Phone)
    {
        this.Id = Id;
        this.Name = Name;
        this.Description = Description;
        this.Address = Address;
        this.Loc_Lat = Loc_Lat;
        this.Loc_Long = Loc_Long;
        this.Timing = Timing;
        this.Image = Image;
        this.Phone = Phone;
    }


    @Override
    public String toString() {
        return "Restaurant Info: { " +
                " id: " + this.Id
                + " Name: " + this.Name
                + " Description " + this.Description
                + " Address " + this.Address
                + " Loc_Lat " + this.Loc_Lat
                + " Loc_Long " + this.Loc_Long
                + " Timing " + this.Timing
                + " image " + this.Image
                + " Phone " + this.Phone
                + " }";
        //return super.toString();
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getLoc_Lat() {
        return Loc_Lat;
    }

    public void setLoc_Lat(double loc_Lat) {
        Loc_Lat = loc_Lat;
    }

    public double getLoc_Long() {
        return Loc_Long;
    }

    public void setLoc_Long(double loc_Long) {
        Loc_Long = loc_Long;
    }

    public String getTiming() {
        return Timing;
    }

    public void setTiming(String timing) {
        Timing = timing;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
