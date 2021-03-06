package edu.westga.cs6242.budgetingapplication.model;

import java.util.ArrayList;

import edu.westga.cs6242.budgetingapplication.model.base_classes.BaseRecord;

/**
 * Represents a MonthlyBudget
 *
 * @author Patrick Dean
 * @version 1
 */
public class MonthlyBudget extends BaseRecord {
    private String description;
    private String dateCreated;
    private String dateUpdated;
    private int userId;

    public MonthlyBudget() {
        super(0, "");
        this.dateCreated = "";
        this.dateUpdated = "";
        this.userId = 0;
    }

    @Override
    public String getInformation() {
        return "Not Implemented";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return Integer.toString(this.getId()) + " - " + this.getTitle();
    }

    // todo USE THIS IN THE MANAGE BUGET VIEW
    public static double getSumOfBills(ArrayList<Bill> bills) {
        double d = 0;
        for (Bill bill : bills) {
            d += bill.getAmount();
        }
        return d;
    }

    public static double getSumOfEarnings(ArrayList<Earning> earnings) {
        double d = 0.0;
        for (Earning earning : earnings) {
            d += earning.getAmount();
        }
        return d;
    }

    public static int getCountOfBills(ArrayList<Bill> bills) {
        return bills.size();
    }


    public static int getCountOfEarnings(ArrayList<Earning> earnings) {
        return earnings.size();
    }
}
