package Domain;

import java.util.Date;

/**
 * Represents a lease agreement in the system.
 */
public class Lease {

    private int leaseID;
    private Date startDate;
    private Date endDate;
    private double rentAmount;
    private String status;
    private int tenantID;
    private int unitID;

    /**
     * Default constructor
     */
    public Lease() {
    }

    /**
     * Constructor with parameters
     */
    public Lease(int tenantID, int unitID, Date startDate, Date endDate, double rentAmount) {
        this.tenantID = tenantID;
        this.unitID = unitID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentAmount = rentAmount;
        this.status = "Active";
    }

    /**
     * Check if lease is expiring within threshold days.
     * @param threshold Number of days
     * @return true if expiring within threshold
     */
    public boolean isExpiring(int threshold) {
        if (endDate == null) return false;
        long daysUntilEnd = (endDate.getTime() - System.currentTimeMillis()) / (1000 * 60 * 60 * 24);
        return daysUntilEnd >= 0 && daysUntilEnd <= threshold;
    }

    /**
     * @param threshold 
     */
    public void findAndMarkExpiring(int threshold) {
        if (isExpiring(threshold)) {
            // Mark as expiring - could trigger notification
            System.out.println("Lease " + leaseID + " is expiring within " + threshold + " days.");
        }
    }

    // Getters and Setters

    public int getLeaseID() {
        return leaseID;
    }

    public void setLeaseID(int leaseID) {
        this.leaseID = leaseID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(double rentAmount) {
        this.rentAmount = rentAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTenantID() {
        return tenantID;
    }

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    public int getUnitID() {
        return unitID;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

    @Override
    public String toString() {
        return "Lease{" +
                "leaseID=" + leaseID +
                ", tenantID=" + tenantID +
                ", unitID=" + unitID +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", rentAmount=" + rentAmount +
                ", status='" + status + '\'' +
                '}';
    }
}