package com.example.myapplication;

import java.io.Serializable;

public class ServiceRequestModel implements Serializable {

    private String id;
    private String title;
    private String category;
    private String clientName;
    private String providerName;
    private String detail;
    private String location;
    private String originalBudgetBs;
    private String counterOfferBs;
    private String targetDate;
    private String preferredSchedule;
    private String executionTime;
    private String counterDateSchedule;
    private String status; // PENDIENTE, ACEPTADA, CONTRAOFERTA, RECHAZADA
    private String dateText;

    public ServiceRequestModel(String id, String title, String category, String clientName, String providerName, String detail, String location, String originalBudgetBs, String counterOfferBs, String targetDate, String preferredSchedule, String executionTime, String counterDateSchedule, String status, String dateText) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.clientName = clientName;
        this.providerName = providerName;
        this.detail = detail;
        this.location = location;
        this.originalBudgetBs = originalBudgetBs;
        this.counterOfferBs = counterOfferBs;
        this.targetDate = targetDate;
        this.preferredSchedule = preferredSchedule;
        this.executionTime = executionTime;
        this.counterDateSchedule = counterDateSchedule;
        this.status = status;
        this.dateText = dateText;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getClientName() { return clientName; }
    public String getProviderName() { return providerName; }
    public String getDetail() { return detail; }
    public String getLocation() { return location; }
    public String getOriginalBudgetBs() { return originalBudgetBs; }
    public String getCounterOfferBs() { return counterOfferBs; }
    public void setCounterOfferBs(String counterOfferBs) { this.counterOfferBs = counterOfferBs; }
    public String getTargetDate() { return targetDate; }
    public String getPreferredSchedule() { return preferredSchedule; }
    public String getExecutionTime() { return executionTime; }
    public String getCounterDateSchedule() { return counterDateSchedule; }
    public void setCounterDateSchedule(String counterDateSchedule) { this.counterDateSchedule = counterDateSchedule; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDateText() { return dateText; }
}