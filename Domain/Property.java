package Domain;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Property {

    /**
     * Default constructor
     */
    public Property() {
    }

    private int propertyID;
    private String address;
    private String propertyType;

    public Property(int propertyID, String address, String propertyType) {
        this.propertyID = propertyID;
        this.address = address;
        this.propertyType = propertyType;
    }

    public int getPropertyID() {
        return propertyID;
    }

    public String getAddress() {
        return address;
    }

    public String getPropertyType() {
        return propertyType;
    }

    @Override
    public String toString() {
        return "Property{" + "id=" + propertyID + ", address='" + address + '\'' + ", type='" + propertyType + '\''
                + '}';
    }
}