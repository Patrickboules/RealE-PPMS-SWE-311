package DataAccess;

import Domain.Property;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Property entity.
 */
public class PropertyDB {

    /**
     * Default constructor
     */
    public PropertyDB() {
    }

    /**
     * Save a new property to the database.
     * @param p Property to save
     * @return The generated property ID, or -1 if failed
     */
    public int save(Property p) {
        String sql = "INSERT INTO properties (address, property_type, owner_id) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, p.getAddress());
            pstmt.setString(2, p.getPropertyType());
            pstmt.setInt(3, p.getOwnerID());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        p.setPropertyID(id);
                        return id;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving property: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Find a property by ID.
     * @param id Property ID
     * @return Property object or null if not found
     */
    public Property findByID(int id) {
        String sql = "SELECT * FROM properties WHERE property_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToProperty(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding property: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all properties.
     * @return List of all properties
     */
    public List<Property> findAll() {
        List<Property> properties = new ArrayList<>();
        String sql = "SELECT * FROM properties ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                properties.add(mapResultSetToProperty(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all properties: " + e.getMessage());
        }
        return properties;
    }

    /**
     * Helper method to map ResultSet to Property object.
     */
    private Property mapResultSetToProperty(ResultSet rs) throws SQLException {
        Property p = new Property();
        p.setPropertyID(rs.getInt("property_id"));
        p.setAddress(rs.getString("address"));
        p.setPropertyType(rs.getString("property_type"));
        p.setOwnerID(rs.getInt("owner_id"));
        return p;
    }
}