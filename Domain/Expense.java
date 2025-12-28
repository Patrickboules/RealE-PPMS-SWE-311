package Domain;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Expense {

    /**
     * Default constructor
     */
    public Expense() {
    }

    private int expenseID;
    private double amount;
    private String category;
    private Date date;
    private int propertyID; // Relationship: Property -- Expense

    /**
     * @return
     */
    public void calculateNetProfit() {
        // TODO implement here
    }

}