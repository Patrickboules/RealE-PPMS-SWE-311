package Domain;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class MaintenanceRequest {

    /**
     * Default constructor
     */
    public MaintenanceRequest() {
    }

    private int requestID;
    private String issueDescription;
    private Date requestDate;
    private String status;

    public MaintenanceRequest(int requestID, String issueDescription, Date requestDate, String status) {
        this.requestID = requestID;
        this.issueDescription = issueDescription;
        this.requestDate = requestDate;
        this.status = status;
    }

    public int getRequestID() {
        return requestID;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MaintenanceRequest{" + "id=" + requestID + ", issue='" + issueDescription + '\'' + ", status='" + status
                + '\'' + '}';
    }
}