package DataAccess;

import Domain.Property;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class PropertyDB {

    /**
     * Default constructor
     */
    public PropertyDB() {
    }

    private static List<Property> properties = new ArrayList<>();

    public void save(Property p) {
        properties.add(p);
        System.out.println("Property saved to database: " + p);
    }

    public List<Property> getAll() {
        return properties;
    }
}