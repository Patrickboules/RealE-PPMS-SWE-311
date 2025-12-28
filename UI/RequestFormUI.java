package UI;

import java.util.*;

/**
 * 
 */
public class RequestFormUI {

    /**
     * Default constructor
     */
    public RequestFormUI() {
    }

    private Controller.PropertyController controller = new Controller.PropertyController();
    private Scanner scanner = new Scanner(System.in);

    public void reportIssue() {
        System.out.println("--- Report Maintenance Issue ---");
        System.out.print("Enter Unit ID: ");
        int unitID = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Issue Description: ");
        String description = scanner.nextLine();

        controller.newRequest(unitID, description);
        System.out.println("Maintenance issue reported successfully!");
    }
}