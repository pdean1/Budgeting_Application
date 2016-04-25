package edu.westga.cs6242.budgetingapplication.model;

import java.util.Date;

/**
 * Represents earnings
 * @author Patrick Dean
 * @version 1
 */
public class Earning {
    private int id;
    private String title;
    // NumberFormat formatter = NumberFormat.getCurrencyInstance();
    // System.out.println(formatter.format(amt));
    private double amount;
    private Date dateEarned;
    private boolean isRecurring;
    private int budgetId;

    public Earning() {
        this(0, "", 0.0, new Date(), false, 0);
    }

    public Earning(int id, String title, double amount,
                   Date dateEarned, boolean isRecurring,
                   int budgetId) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.dateEarned = dateEarned;
        this.isRecurring = isRecurring;
        this.budgetId = budgetId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDateEarned() {
        return dateEarned;
    }

    public void setDateEarned(Date dateEarned) {
        this.dateEarned = dateEarned;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setIsRecurring(boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    @Override
    public String toString() {
        String recurring = this.isRecurring() ? "Recurring" : "Not recurring";
        return this.getTitle() + "\n" +
                this.getAmount() + "\n" +
                this.getDateEarned() + "\n" +
                recurring;
    }
}