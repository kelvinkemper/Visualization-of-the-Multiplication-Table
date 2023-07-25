import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Kelvin Kemper
 * CS 351 Project 1 Times Table
 * main class creates the stage, scenes and sets the panes that will be used
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Times Table Visualization");

        GridPane inputs = new GridPane();
        inputs.setHgap(10);
        inputs.setVgap(10);
        inputs.setPadding(new Insets(25,25,25,25));
        inputs.setAlignment(Pos.TOP_LEFT);
        StackPane displayCircle = new StackPane();

        Circle circle = new Circle(225);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.GREY);
        displayCircle.getChildren().add(circle);

        BorderPane root = new BorderPane();
        root.setLeft(inputs);
        root.setCenter(displayCircle);
        Display display = new Display(inputs,displayCircle);
        Scene scene = new Scene(root,800,800);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }



}
