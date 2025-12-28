package Controller;

import Domain.Property;
import DataAccess.PropertyDB;
import DataAccess.MaintenanceDB;
import Domain.MaintenanceRequest;

import java.util.List;

/**
 * Controller for Property-related operations.
 */
public class PropertyController {

    private PropertyDB propertyDB;
    private MaintenanceDB maintenanceDB;

    /**
     * Default constructor
     */
    public PropertyController() {
        this.propertyDB = new PropertyDB();
        this.maintenanceDB = new MaintenanceDB();
    }

    /**
     * Add a new property to the system.
     * @param address Property address
     * @param type Property type (Residential/Commercial)
     * @param ownerID Owner ID
     * @return The created Property, or null if validation failed
     */
    public Property addProperty(String address, String type, int ownerID) {
        // Validate input
        if (address == null || address.trim().isEmpty()) {
            System.err.println("Validation Error: Address cannot be empty");
            return null;
        }
        
        if (type == null || (!type.equals("Residential") && !type.equals("Commercial"))) {
            System.err.println("Validation Error: Type must be 'Residential' or 'Commercial'");
            return null;
        }
        
        if (ownerID <= 0) {
            System.err.println("Validation Error: Owner ID must be positive");
            return null;
        }

        // Create domain object
        Property property = new Property(address.trim(), type, ownerID);
        
        // Persist to database
        int generatedId = propertyDB.save(property);
        
        if (generatedId > 0) {
            System.out.println("Property added successfully with ID: " + generatedId);
            return property;
        } else {
            System.err.println("Failed to save property to database");
            return null;
        }
    }

    /**
     * Get all properties.
     * @return List of all properties
     */
    public List<Property> getAllProperties() {
        return propertyDB.findAll();
    }

    /**
     * Get a property by ID.
     * @param propertyID Property ID
     * @return Property or null
     */
    public Property getProperty(int propertyID) {
        return propertyDB.findByID(propertyID);
    }

    /**
     * Create a new maintenance request.
     * @param unitID Unit ID
     * @param description Issue description
     */
    public void newRequest(int unitID, String description) {
        MaintenanceRequest request = new MaintenanceRequest();
        // Set properties would go here
        maintenanceDB.save(request);
    }

    /**
     * Update maintenance request status.
     * @param reqID Request ID
     * @param status New status
     */
    public void setStatus(int reqID, String status) {
        // TODO: Implement status update
    }
}