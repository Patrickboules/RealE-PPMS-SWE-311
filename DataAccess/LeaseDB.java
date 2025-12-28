package DataAccess;

import Domain.Lease;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Lease entity.
 */
public class LeaseDB {

    /**
     * Default constructor
     */
    public LeaseDB() {
    }

    /**
     * Save a new lease to the database.
     * @param l Lease to save
     * @return The generated lease ID, or -1 if failed
     */
    public int save(Lease l) {
        String sql = "INSERT INTO leases (tenant_id, unit_id, start_date, end_date, rent_amount, status) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, l.getTenantID());
            pstmt.setInt(2, l.getUnitID());
            pstmt.setDate(3, new java.sql.Date(l.getStartDate().getTime()));
            pstmt.setDate(4, new java.sql.Date(l.getEndDate().getTime()));
            pstmt.setDouble(5, l.getRentAmount());
            pstmt.setString(6, l.getStatus() != null ? l.getStatus() : "Active");
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        l.setLeaseID(id);
                        return id;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving lease: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Find a lease by ID.
     * @param id Lease ID
     * @return Lease object or null if not found
     */
    public Lease findByID(int id) {
        String sql = "SELECT * FROM leases WHERE lease_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToLease(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding lease: " + e.getMessage());
        }
        return null;
    }

    /**
     * Fetch leases expiring within the given threshold days.
     * @param threshold Number of days
     * @return List of expiring leases
     */
    public List<Lease> fetchLeasesNearEnd(int threshold) {
        List<Lease> leases = new ArrayList<>();
        String sql = "SELECT * FROM leases WHERE status = 'Active' AND end_date <= date('now', '+' || ? || ' days') ORDER BY end_date ASC";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, threshold);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                leases.add(mapResultSetToLease(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching expiring leases: " + e.getMessage());
        }
        return leases;
    }

    /**
     * Get all leases.
     * @return List of all leases
     */
    public List<Lease> findAll() {
        List<Lease> leases = new ArrayList<>();
        String sql = "SELECT * FROM leases ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                leases.add(mapResultSetToLease(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all leases: " + e.getMessage());
        }
        return leases;
    }

    /**
     * Helper method to map ResultSet to Lease object.
     */
    private Lease mapResultSetToLease(ResultSet rs) throws SQLException {
        Lease l = new Lease();
        l.setLeaseID(rs.getInt("lease_id"));
        l.setTenantID(rs.getInt("tenant_id"));
        l.setUnitID(rs.getInt("unit_id"));
        l.setStartDate(rs.getDate("start_date"));
        l.setEndDate(rs.getDate("end_date"));
        l.setRentAmount(rs.getDouble("rent_amount"));
        l.setStatus(rs.getString("status"));
        return l;
    }
}