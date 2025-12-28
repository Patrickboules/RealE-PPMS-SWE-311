package UI;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class PropertyFormUI {

    /**
     * Default constructor
     */
    public PropertyFormUI() {
    }

    private Controller.PropertyController controller = new Controller.PropertyController();
    private Scanner scanner = new Scanner(System.in);

    public void submitAddProperty() {
        System.out.println("--- Add New Property ---");
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Type (Residential/Commercial): ");
        String type = scanner.nextLine();
        System.out.print("Enter Owner ID: ");
        int ownerID = Integer.parseInt(scanner.nextLine());

        controller.addProperty(address, type, ownerID);
        System.out.println("Property added successfully!");
    }
}