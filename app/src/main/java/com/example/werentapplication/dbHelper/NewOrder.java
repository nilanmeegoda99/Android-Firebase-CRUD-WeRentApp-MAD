package com.example.werentapplication.dbHelper;

public class NewOrder {
    private Integer orderNumber;
    private String product;
    private int cost;
    private int days;
    private int totCost;

    public NewOrder() {
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getTotCost() {
        return totCost;
    }

    public void setTotCost(int totCost) {
        this.totCost = totCost;
    }
}
