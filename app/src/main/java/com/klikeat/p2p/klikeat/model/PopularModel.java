package com.klikeat.p2p.klikeat.model;

public class PopularModel {
    String foodName;
    String foodPrice;
    int picture;

    public PopularModel(){

    }
    public PopularModel(String foodName, String foodPrice, int picture) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.picture = picture;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }
}
