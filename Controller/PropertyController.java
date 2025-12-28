package Controller;

import java.util.*;

/**
 * 
 */
public class PropertyController {

    /**
     * Default constructor
     */
    public PropertyController() {
    }

    private DataAccess.PropertyDB db = new DataAccess.PropertyDB();

    public void addProperty(String address, String type, int ownerID) {
        int newId = db.getAll().size() + 1;
        Domain.Property p = new Domain.Property(newId, address, type);
        db.save(p);
    }

    /**
     * @param unitID
     * @param description
     */
    public void newRequest(int unitID, String description) {
        DataAccess.MaintenanceDB mdb = new DataAccess.MaintenanceDB();
        int newId = mdb.getAll().size() + 1;
        Domain.MaintenanceRequest r = new Domain.MaintenanceRequest(newId, description, new Date(), "New");
        mdb.save(r);
    }

    public void setStatus(int reqID, String status) {
        DataAccess.MaintenanceDB mdb = new DataAccess.MaintenanceDB();
        for (Domain.MaintenanceRequest r : mdb.getAll()) {
            if (r.getRequestID() == reqID) {
                r.setStatus(status);
                mdb.update(r);
                break;
            }
        }
    }

}