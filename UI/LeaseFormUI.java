package UI;

import Controller.LeaseController;
import Domain.Lease;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * JavaFX form for creating a new lease.
 */
public class LeaseFormUI extends VBox {

    private TextField tenantIdField;
    private TextField unitIdField;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private TextField rentField;
    private Label statusLabel;
    private LeaseController controller;

    /**
     * Default constructor
     */
    public LeaseFormUI() {
        this.controller = new LeaseController();
        initializeUI();
    }

    /**
     * Initialize the UI components.
     */
    private void initializeUI() {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.TOP_CENTER);
        this.setStyle("-fx-background-color: #f5f5f5;");

        // Title
        Label titleLabel = new Label("Create New Lease");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Form grid
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(15);
        formGrid.setAlignment(Pos.CENTER);

        // Tenant ID field
        Label tenantIdLabel = new Label("Tenant ID:");
        tenantIdLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        tenantIdField = new TextField();
        tenantIdField.setPromptText("Enter tenant ID");
        tenantIdField.setPrefWidth(300);
        formGrid.add(tenantIdLabel, 0, 0);
        formGrid.add(tenantIdField, 1, 0);

        // Unit ID field
        Label unitIdLabel = new Label("Unit ID:");
        unitIdLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        unitIdField = new TextField();
        unitIdField.setPromptText("Enter unit ID");
        unitIdField.setPrefWidth(300);
        formGrid.add(unitIdLabel, 0, 1);
        formGrid.add(unitIdField, 1, 1);

        // Start date picker
        Label startDateLabel = new Label("Start Date:");
        startDateLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Select start date");
        startDatePicker.setPrefWidth(300);
        startDatePicker.setValue(LocalDate.now());
        formGrid.add(startDateLabel, 0, 2);
        formGrid.add(startDatePicker, 1, 2);

        // End date picker
        Label endDateLabel = new Label("End Date:");
        endDateLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        endDatePicker = new DatePicker();
        endDatePicker.setPromptText("Select end date");
        endDatePicker.setPrefWidth(300);
        endDatePicker.setValue(LocalDate.now().plusYears(1));
        formGrid.add(endDateLabel, 0, 3);
        formGrid.add(endDatePicker, 1, 3);

        // Rent field
        Label rentLabel = new Label("Monthly Rent:");
        rentLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        rentField = new TextField();
        rentField.setPromptText("Enter rent amount");
        rentField.setPrefWidth(300);
        formGrid.add(rentLabel, 0, 4);
        formGrid.add(rentField, 1, 4);

        // Submit button
        Button submitButton = new Button("Create Lease");
        submitButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");
        submitButton.setOnAction(e -> submitLease());

        // Clear button
        Button clearButton = new Button("Clear");
        clearButton.setStyle("-fx-background-color: #9E9E9E; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");
        clearButton.setOnAction(e -> clearForm());

        HBox buttonBox = new HBox(10, submitButton, clearButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Status label for feedback
        statusLabel = new Label();
        statusLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        this.getChildren().addAll(titleLabel, formGrid, buttonBox, statusLabel);
    }

    /**
     * Handle form submission.
     */
    public void submitLease() {
        // Parse tenant ID
        int tenantId;
        try {
            tenantId = Integer.parseInt(tenantIdField.getText());
        } catch (NumberFormatException e) {
            showError("Tenant ID must be a valid number");
            return;
        }

        // Parse unit ID
        int unitId;
        try {
            unitId = Integer.parseInt(unitIdField.getText());
        } catch (NumberFormatException e) {
            showError("Unit ID must be a valid number");
            return;
        }

        // Get dates
        LocalDate startLocal = startDatePicker.getValue();
        LocalDate endLocal = endDatePicker.getValue();

        if (startLocal == null || endLocal == null) {
            showError("Please select both start and end dates");
            return;
        }

        Date startDate = Date.from(startLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Parse rent
        double rent;
        try {
            rent = Double.parseDouble(rentField.getText());
        } catch (NumberFormatException e) {
            showError("Rent must be a valid number");
            return;
        }

        // Call controller
        Lease result = controller.createLease(tenantId, unitId, startDate, endDate, rent);

        if (result != null) {
            showSuccess("Lease created successfully! ID: " + result.getLeaseID());
            clearForm();
        } else {
            showError("Failed to create lease. Please check all fields.");
        }
    }

    /**
     * Legacy method for compatibility.
     */
    public void submitLease(int tenantID, int unitID, Date start, Date end, double rent) {
        Lease result = controller.createLease(tenantID, unitID, start, end, rent);
        if (result != null) {
            showSuccess("Lease created successfully!");
        }
    }

    /**
     * Clear the form fields.
     */
    private void clearForm() {
        tenantIdField.clear();
        unitIdField.clear();
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now().plusYears(1));
        rentField.clear();
        statusLabel.setText("");
    }

    /**
     * Show success message.
     */
    private void showSuccess(String message) {
        statusLabel.setText(message);
        statusLabel.setTextFill(Color.GREEN);
    }

    /**
     * Show error message.
     */
    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setTextFill(Color.RED);
    }
}