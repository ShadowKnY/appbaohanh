package com.example.myapplication.domain;

public class PopularDomain {
    private String title;
    private String picUrl;
    private int review;
    private double score;
    private int numberInChart;
    private double price;

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

    public PopularDomain(String title, String picUrl, int review, double score, int numberInChart, double price) {
        this.title = title;
        this.picUrl = picUrl;
        this.review = review;
        this.score = score;
        this.numberInChart = numberInChart;
        this.price = price;
    }
}
