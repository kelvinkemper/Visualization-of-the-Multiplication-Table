import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Random;

/**
 * Creates the times table circle and
 * user inputs including buttons and spinners
 */


public class Display {


    public int numOfPoints = 360;
    public double multiplier = 2;
    private final int RADIUS = 225;
    private double[] xCoordinate;
    private double[] yCoordinate;

    private final GridPane leftPane;
    private StackPane displayPane;

    private Spinner<Double> incrementSpinner;
    private Spinner<Integer> fpsSpinner;
    private int fps=1;
    double amt;
    double incrementer = multiplier;
    private ArrayList<Color> colorList;



    public Display(GridPane leftPane, StackPane displayPane) {
        this.displayPane = displayPane;
        this.leftPane = leftPane;
        createUserInput();
    }

    /**
     * populate x and y coordinate arrays that are used for lines of circle
     */
    private void populateCoordinates() {
        xCoordinate = new double[numOfPoints];
        yCoordinate = new double[numOfPoints];
        for (int i = 0; i < numOfPoints; i++) {
            double delta = Math.PI * 2 * i / numOfPoints;
            xCoordinate[i] = RADIUS * Math.cos(delta);
            yCoordinate[i] = RADIUS * Math.sin(delta);
            //System.out.println(i + ". " + xCoordinate[i] + " : " + yCoordinate[i]);
        }
    }

    /**
     * method that will draw the circle with number of points
     * and lines
     */
    private void drawCircle() {
        populateCoordinates();
        Group displayTT = new Group();
        Color currColor = lineColors();

//       visualize circle for testing
        Circle c = new Circle(RADIUS);
        c.setStroke(Color.BLACK);
       displayTT.getChildren().add(c);


        for (int i = 0; i < numOfPoints; i++) {

            // sets which coordinate the end point of the line will be
            int j = (int) multiplier * i % numOfPoints;


            Circle circle = new Circle(xCoordinate[i], yCoordinate[i], 2);
            circle.setFill(Color.BLACK);
            displayTT.getChildren().add(circle);

            Line line = new Line(xCoordinate[i],yCoordinate[i],xCoordinate[j],yCoordinate[j]);

            line.setStroke(currColor);
            line.setStrokeWidth(1);

            displayTT.getChildren().add(line);

        }
        displayPane.getChildren().add(displayTT);

    }

    /**
     * 10 random colors I picked for lines of the times table
     * @return 1 random color from the list
     */
    private Color lineColors() {
        Random rand = new Random();

        colorList = new ArrayList<>();
        colorList.add(Color.WHITESMOKE);
        colorList.add(Color.RED);
        colorList.add(Color.DARKORANGE);
        colorList.add(Color.DARKSEAGREEN);
        colorList.add(Color.SADDLEBROWN);
        colorList.add(Color.CORNFLOWERBLUE);
        colorList.add(Color.MINTCREAM);
        colorList.add(Color.DARKVIOLET);
        colorList.add(Color.DARKSLATEGREY);
        colorList.add(Color.GOLD);
        return colorList.get(rand.nextInt(colorList.size()));




    }

    /**
     * updates circle when jump to button is pressed
     */
    private void updateCircle() {
        displayPane.getChildren().clear();
        drawCircle();

    }

    /**
     * used to show all user inputs in the program
     * root pane -> left position -> vbox -> all user inputs
     */

    private void createUserInput() {

        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                long dt = now - lastUpdate;
                switch (fps) {
                    case 1: //1 fps
                        amt = 1.5e9;
                        break;
                    case 2: //2fps
                        amt = 5e8;
                        break;
                    case 3: //5 fps
                        amt = 2e8;
                        break;
                    case 4: //10 fps
                        amt = 1e8;
                        break;
                    case 5:
                        amt = 3e7;
                        break;
                    case 6:
                        amt = 0;
                        break;
                }

                if (dt > amt) {
                  //  System.out.println("incrementer= " + incrementer);
                    lastUpdate = now;
                    multiplier += incrementer;
                 //   System.out.println(multiplier);
                    updateCircle();
                }

            }
        };

        Label l1 = new Label("Number of points:");
        TextField tf = new TextField("360");
        leftPane.add(l1, 0, 1);
        leftPane.add(tf, 1, 1);

        Label label2 = new Label("Multiplier:");
        TextField tf2 = new TextField("2");
        leftPane.add(label2, 0, 2);
        leftPane.add(tf2, 1, 2);

        Button jumpTo = new Button("Go");
        jumpTo.setOnAction(event -> {
            numOfPoints = Integer.parseInt(tf.getText());
            multiplier = Double.parseDouble(tf2.getText());
            updateCircle();
        });
        leftPane.add(jumpTo, 0, 3);

        Button startBttn = new Button("Start");
        startBttn.setOnAction(event -> animationTimer.start());

        Button stopBttn = new Button("Pause");
        stopBttn.setOnAction(event -> animationTimer.stop());

        leftPane.add(startBttn, 0, 10);
        leftPane.add(stopBttn, 1, 10);

        incrementSpinner = new Spinner<>();
        fpsSpinner = new Spinner<>();

        SpinnerValueFactory<Double> doubleSpinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 360);
        doubleSpinnerValueFactory.setValue(multiplier);

        SpinnerValueFactory<Integer> integerSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 6);
        integerSpinnerValueFactory.setValue(1);

        incrementSpinner.setValueFactory(doubleSpinnerValueFactory);
        fpsSpinner.setValueFactory(integerSpinnerValueFactory);

        incrementSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            incrementer = incrementSpinner.getValue();
        });

        fpsSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            fps = fpsSpinner.getValue();
            //System.out.println(fps);
        });


        Label multiplyLabel = new Label("Increment: ");
        leftPane.add(multiplyLabel, 0, 12);
        leftPane.add(incrementSpinner, 1, 12);

        Label fpsLabel = new Label("FPS: ");
        leftPane.add(fpsLabel, 0, 13);
        leftPane.add(fpsSpinner, 1, 13);



        Label lbFaves = new Label("Favorites");
        Button bt1 = new Button("1");
        Button bt2 = new Button("2");
        Button bt3 = new Button("3");
        Button bt4 = new Button("4");
        Button bt5 = new Button("5");
        Button bt6 = new Button("6");
        bt1.setOnAction(event -> {
            multiplier = 36;
            numOfPoints = 10;
            updateCircle();
        });

        bt2.setOnAction(event -> {
            multiplier = 121;
            numOfPoints = 360;
            updateCircle();
        });

        bt3.setOnAction(event -> {
            multiplier = 169;
            numOfPoints = 255;
            updateCircle();
        });

        bt4.setOnAction(event -> {
            multiplier = 239;
            numOfPoints = 360;
            updateCircle();
        });

        bt5.setOnAction(event -> {
            multiplier = 183;
            numOfPoints = 255;
            updateCircle();
        });

        bt6.setOnAction(event -> {
            multiplier = 359;
            numOfPoints = 360;
            updateCircle();
        });

        leftPane.add(lbFaves,0,20);
        leftPane.add(bt1,0,21);
        leftPane.add(bt2,1,21);
        leftPane.add(bt3,0,22);
        leftPane.add(bt4,1,22);
        leftPane.add(bt5,0,23);
        leftPane.add(bt6,1,23);



    }


    public Group favorites() {
        Group faves = new Group();
        // n = 10, multiplier 32
        // n = 360, m = 91

        return faves;

    }

}
