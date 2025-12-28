package Controller;

import Domain.Lease;
import DataAccess.LeaseDB;

import java.util.Date;
import java.util.List;

/**
 * Controller for Lease-related operations.
 */
public class LeaseController {

    private LeaseDB leaseDB;

    /**
     * Default constructor
     */
    public LeaseController() {
        this.leaseDB = new LeaseDB();
    }

    /**
     * Create a new lease agreement.
     * @param tenantID Tenant ID
     * @param unitID Unit ID
     * @param start Lease start date
     * @param end Lease end date
     * @param rent Monthly rent amount
     * @return The created Lease, or null if validation failed
     */
    public Lease createLease(int tenantID, int unitID, Date start, Date end, double rent) {
        // Validate input
        if (tenantID <= 0) {
            System.err.println("Validation Error: Tenant ID must be positive");
            return null;
        }
        
        if (unitID <= 0) {
            System.err.println("Validation Error: Unit ID must be positive");
            return null;
        }
        
        if (start == null) {
            System.err.println("Validation Error: Start date cannot be null");
            return null;
        }
        
        if (end == null) {
            System.err.println("Validation Error: End date cannot be null");
            return null;
        }
        
        if (end.before(start)) {
            System.err.println("Validation Error: End date must be after start date");
            return null;
        }
        
        if (rent <= 0) {
            System.err.println("Validation Error: Rent must be positive");
            return null;
        }

        // Create domain object
        Lease lease = new Lease(tenantID, unitID, start, end, rent);
        
        // Persist to database
        int generatedId = leaseDB.save(lease);
        
        if (generatedId > 0) {
            System.out.println("Lease created successfully with ID: " + generatedId);
            return lease;
        } else {
            System.err.println("Failed to save lease to database");
            return null;
        }
    }

    /**
     * Get all leases.
     * @return List of all leases
     */
    public List<Lease> getAllLeases() {
        return leaseDB.findAll();
    }

    /**
     * Get a lease by ID.
     * @param leaseID Lease ID
     * @return Lease or null
     */
    public Lease getLease(int leaseID) {
        return leaseDB.findByID(leaseID);
    }

    /**
     * Get leases expiring within a threshold.
     * @param days Number of days threshold
     * @return List of expiring leases
     */
    public List<Lease> getExpiringLeases(int days) {
        return leaseDB.fetchLeasesNearEnd(days);
    }
}