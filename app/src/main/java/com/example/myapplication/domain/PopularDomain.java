package com.example.myapplication.domain;

public class PopularDomain {

    private String title;
    private String picUrl;
    private int review;
    private double score;
    private int numberInChart;
    private int quantity;
    private double price;
    private  String decription;
    private String itemId;


    public  PopularDomain(String title,String picUrl,double price){
        this.title= title;
        this.picUrl = picUrl;
        this.price = price;
    }

    public PopularDomain(String title, String picUrl, int review, double score, int numberInChart, double price, String decription) {
        this.title = title;
        this.picUrl = picUrl;
        this.review = review;
        this.score = score;
        this.numberInChart = numberInChart;
        this.price = price;
    }
    public PopularDomain(String title, String picUrl, int review, double score, int numberInChart, double price, String decription,String itemId) {
        this.title = title;
        this.picUrl = picUrl;
        this.review = review;
        this.score = score;
        this.numberInChart = numberInChart;
        this.price = price;
        this.itemId = itemId;
    }


    public PopularDomain(int quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public int getReview() {
        return review;
    }

    public double getScore() {
        return score;
    }

    public int getNumberInChart() {
        return numberInChart;
    }

    public double getPrice() {
        return price;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PopularDomain() {
    }
    public String getItemId() {
        return itemId;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
