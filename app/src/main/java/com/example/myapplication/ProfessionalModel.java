package com.example.myapplication;

import java.io.Serializable;
import java.util.List;

public class ProfessionalModel implements Serializable {

    private String id;
    private String name;
    private String title;
    private String category;
    private double rating;
    private int reviewsCount;
    private double distanceKm;
    private String priceText;
    private String statusBadge;
    private String phone;
    private String bio;
    private List<String> skills;
    private boolean verified;

    public ProfessionalModel(String id, String name, String title, String category, double rating, int reviewsCount, double distanceKm, String priceText, String statusBadge, String phone, String bio, List<String> skills, boolean verified) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.category = category;
        this.rating = rating;
        this.reviewsCount = reviewsCount;
        this.distanceKm = distanceKm;
        this.priceText = priceText;
        this.statusBadge = statusBadge;
        this.phone = phone;
        this.bio = bio;
        this.skills = skills;
        this.verified = verified;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public double getRating() { return rating; }
    public int getReviewsCount() { return reviewsCount; }
    public double getDistanceKm() { return distanceKm; }
    public String getPriceText() { return priceText; }
    public String getStatusBadge() { return statusBadge; }
    public String getPhone() { return phone; }
    public String getBio() { return bio; }
    public List<String> getSkills() { return skills; }
    public boolean isVerified() { return verified; }
}