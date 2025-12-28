package DataAccess;

import Domain.MaintenanceRequest;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class MaintenanceDB {

    /**
     * Default constructor
     */
    public MaintenanceDB() {
    }

    private static List<MaintenanceRequest> requests = new ArrayList<>();

    public void save(MaintenanceRequest r) {
        requests.add(r);
        System.out.println("Maintenance Request saved: " + r);
    }

    public void update(MaintenanceRequest r) {
        System.out.println("Maintenance Request updated: " + r);
    }

    public List<MaintenanceRequest> getAll() {
        return requests;
    }
}