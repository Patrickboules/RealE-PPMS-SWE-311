package Domain;

/**
 * Represents a property in the system.
 */
public class Property {

    private int propertyID;
    private String address;
    private String propertyType;
    private int ownerID;

    /**
     * Default constructor
     */
    public Property() {
    }

    /**
     * Constructor with parameters
     */
    public Property(String address, String propertyType, int ownerID) {
        this.address = address;
        this.propertyType = propertyType;
        this.ownerID = ownerID;
    }

    // Getters and Setters

    public int getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    @Override
    public String toString() {
        return "Property{" +
                "propertyID=" + propertyID +
                ", address='" + address + '\'' +
                ", propertyType='" + propertyType + '\'' +
                ", ownerID=" + ownerID +
                '}';
    }
}