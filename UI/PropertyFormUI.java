package UI;

import Controller.PropertyController;
import Domain.Property;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * JavaFX form for adding a new property.
 */
public class PropertyFormUI extends VBox {

    private TextField addressField;
    private ComboBox<String> typeComboBox;
    private TextField ownerIdField;
    private Label statusLabel;
    private PropertyController controller;

    /**
     * Default constructor
     */
    public PropertyFormUI() {
        this.controller = new PropertyController();
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
        Label titleLabel = new Label("Add New Property");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Form grid
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(15);
        formGrid.setAlignment(Pos.CENTER);

        // Address field
        Label addressLabel = new Label("Address:");
        addressLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        addressField = new TextField();
        addressField.setPromptText("Enter property address");
        addressField.setPrefWidth(300);
        formGrid.add(addressLabel, 0, 0);
        formGrid.add(addressField, 1, 0);

        // Property type combo box
        Label typeLabel = new Label("Property Type:");
        typeLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Residential", "Commercial");
        typeComboBox.setPromptText("Select type");
        typeComboBox.setPrefWidth(300);
        formGrid.add(typeLabel, 0, 1);
        formGrid.add(typeComboBox, 1, 1);

        // Owner ID field
        Label ownerIdLabel = new Label("Owner ID:");
        ownerIdLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        ownerIdField = new TextField();
        ownerIdField.setPromptText("Enter owner ID");
        ownerIdField.setPrefWidth(300);
        formGrid.add(ownerIdLabel, 0, 2);
        formGrid.add(ownerIdField, 1, 2);

        // Submit button
        Button submitButton = new Button("Add Property");
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");
        submitButton.setOnAction(e -> submitAddProperty());

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
    public void submitAddProperty() {
        String address = addressField.getText();
        String type = typeComboBox.getValue();
        String ownerIdText = ownerIdField.getText();

        // Validate owner ID is a number
        int ownerId;
        try {
            ownerId = Integer.parseInt(ownerIdText);
        } catch (NumberFormatException e) {
            showError("Owner ID must be a valid number");
            return;
        }

        // Call controller
        Property result = controller.addProperty(address, type, ownerId);

        if (result != null) {
            showSuccess("Property added successfully! ID: " + result.getPropertyID());
            clearForm();
        } else {
            showError("Failed to add property. Please check all fields.");
        }
    }

    /**
     * Clear the form fields.
     */
    private void clearForm() {
        addressField.clear();
        typeComboBox.setValue(null);
        ownerIdField.clear();
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