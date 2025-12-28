import UI.PropertyFormUI;
import UI.LeaseFormUI;
import DataAccess.DatabaseConnection;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Main application entry point for the Property Management System.
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Initialize database connection
        DatabaseConnection.getInstance();

        // Create main layout with tabs
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Add Property Tab
        Tab propertyTab = new Tab("Add Property");
        propertyTab.setContent(new PropertyFormUI());
        
        // Create Lease Tab
        Tab leaseTab = new Tab("Create Lease");
        leaseTab.setContent(new LeaseFormUI());

        tabPane.getTabs().addAll(propertyTab, leaseTab);

        // Create header
        Label headerLabel = new Label("Property Management System");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        headerLabel.setTextFill(Color.WHITE);
        headerLabel.setPadding(new Insets(15));

        HBox header = new HBox(headerLabel);
        header.setStyle("-fx-background-color: #1976D2;");
        header.setPadding(new Insets(10));

        // Main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(header);
        mainLayout.setCenter(tabPane);

        // Create scene
        Scene scene = new Scene(mainLayout, 600, 500);
        
        primaryStage.setTitle("PPMS - Property Management System");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(400);
        primaryStage.show();
    }

    @Override
    public void stop() {
        // Close database connection on exit
        DatabaseConnection.getInstance().close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
