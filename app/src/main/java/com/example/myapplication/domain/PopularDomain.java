package com.example.myapplication.domain;

public class PopularDomain {
    private String title;
    private String picUrl;
    private int review;
    private double score;
    private int numberInChart;
    private double price;
    private  String decription;

    public PopularDomain() {
    }

    public PopularDomain(String title, String picUrl, int review, double score, int numberInChart, double price, String decription) {
        this.title = title;
        this.picUrl = picUrl;
        this.review = review;
        this.score = score;
        this.numberInChart = numberInChart;
        this.price = price;
        this.decription = decription;
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


}
