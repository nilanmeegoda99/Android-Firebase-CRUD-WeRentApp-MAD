package com.example.werentapp;

public class model {
    String ProductName,Location,Image,Description;
    Double RatePerDay;
    public model() {
    }

    public Double getRatePerDay() {
        return RatePerDay;
    }

    public void setRatePerDay(Double ratePerDay) {
        RatePerDay = ratePerDay;
    }

    public model(String productName, String location, Double ratePerDay, String image, String description) {
        ProductName = productName;
        Location = location;
        RatePerDay = ratePerDay;
        Image = image;
        Description = description;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }




    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
