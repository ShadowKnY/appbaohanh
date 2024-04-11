package com.example.myapplication.domain;

import com.google.gson.Gson;

import java.util.HashMap;

public class PopularDomain {

    private String title;
    private String picUrl;
    private int review;
    private double score;
    private int numberInChart;
    private double price;
    private String decription;
    private String itemId;
    //them
    private int quantity;
    private String category;

    public PopularDomain() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public PopularDomain(int quantity) {
        this.quantity = quantity;
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

    public PopularDomain(String title, String picUrl, int review, double score, int numberInChart, double price, String decription,String itemId, String category) {
        this.title = title;
        this.picUrl = picUrl;
        this.review = review;
        this.score = score;
        this.numberInChart = numberInChart;
        this.price = price;
        this.decription = decription;
        this.itemId = itemId;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getNumberInChart() {
        return numberInChart;
    }

    public void setNumberInChart(int numberInChart) {
        this.numberInChart = numberInChart;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("picUrl", picUrl);
        map.put("review", review);
        map.put("score", score);
        map.put("numberInChart", numberInChart);
        map.put("price", price);
        map.put("decription", decription);
        return map;
    }
}