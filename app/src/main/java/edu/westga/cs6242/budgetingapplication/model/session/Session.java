package edu.westga.cs6242.budgetingapplication.model.session;

import edu.westga.cs6242.budgetingapplication.model.MonthlyBudget;
import edu.westga.cs6242.budgetingapplication.model.User;

/**
 * Session variables that can be accessed by the application.
 *
 * @author Patrick Dean
 * @version 1
 */
public class Session {
    /**
     *
     */
    private static Session ourInstance = new Session();

    /**
     *
     */
    private static User user = new User();

    /**
     *
     */
    private static MonthlyBudget budget_1 = new MonthlyBudget();
    private static MonthlyBudget budget_2 = new MonthlyBudget();

    /**
     * Returns the session's instance to the caller
     *
     * @return Session's instance
     */
    public static Session getInstance() {
        return ourInstance;
    }

    /**
     * Default Constructor, does nothing
     */
    private Session() {

    }

    /**
     * Sets the session user
     *
     * @param user Sets the session user equal to the passed user
     */
    public static void setUser(User user) {
        Session.user = user;
    }

    /**
     * Sets the monthly budget variable number one
     *
     * @param budget Budget to set too
     */
    public static void setMonthlyBudget1(MonthlyBudget budget) {
        Session.budget_1 = budget;
    }

    /**
     * Sets the monthly budget variable number two
     *
     * @param budget Budget to set too
     */
    public static void setMonthlyBudget2(MonthlyBudget budget) {
        Session.budget_2 = budget;
    }

    /**
     * Gets the session user
     *
     * @return this session user
     */
    public static User getUser() {
        return Session.user;
    }

    /**
     * Get the monthly budget variable number one
     *
     * @return the monthly budget associated to the variable number one
     */
    public static MonthlyBudget getMonthlyBudget1() {
        return Session.budget_1;
    }

    /**
     * Get the monthly budget variable number two
     *
     * @return the second monthly variable budget
     */
    public static MonthlyBudget getMonthlyBudget2() {
        return Session.budget_2;
    }
}