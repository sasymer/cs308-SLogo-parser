package slogo.View;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import slogo.Main;
import slogo.model.Turtle;

/**
 * This class holds the grid where the commands are executed on; for example, if the turtle moves
 * forward with its pen down, we would see the line on the canvas. The class also has a drawer class
 * to draw when the pen is down and knows the turtle's information (like location) through binding,
 * and updates the turtle's properties through these bindings
 *
 * @author Michelle Tai
 */
public class TurtleGrid {

  private int myCanvasWidth, myCanvasHeight;
  private Turtle viewTurtle;
  private ImageView turtleImageView;
  private Drawing myDrawer;
  private Pane myPane; //to change background of grid, change the background of the pane
  private Canvas myCanvas;
  private static final int DEFAULT_CANVAS_WIDTH = 1140;
  private static final int DEFAULT_CANVAS_HEIGHT = 630;
  private static final String TURTLE_IMAGE = "TurtleImage";
  private StackPane retGrid;
  private double centerX, centerY, pastX, pastY;
  private double turtleCenterX, turtleCenterY;
  private Boolean ispenDown = true;
  private ArrayList<Line> linesDrawn;
//  private static final Paint DEFAULT_BACKGROUND  = Color.

  /**
   * Constructor for the TurtleGrid class, which initializes everything
   *
   * @param canvasWidth  is the width of the canvas where the turtle is located, and where all the
   *                     shapes are drawn
   * @param canvasHeight is the height of the canvas
   * @param draw         is the drawing class that would control what will be drawn on the canvas
   */
  public TurtleGrid(int canvasWidth, int canvasHeight, Drawing draw, Turtle viewTurtle) {
    myDrawer = draw;
    myCanvasWidth = canvasWidth;
    myCanvasHeight = canvasHeight;
    centerX = canvasWidth / 2.0;
    centerY = canvasHeight / 2.0;
    myPane = new Pane();
    myPane.setMaxWidth(myCanvasWidth);
    myPane.setMaxHeight(myCanvasHeight);
    setBackground(Color.LINEN);
    myCanvas = new Canvas(myCanvasWidth, myCanvasHeight);
    retGrid = new StackPane();
    retGrid.setPadding(new Insets(10, 10, 10, 0));
    retGrid.getChildren().addAll(myCanvas, myPane);

    linesDrawn = new ArrayList<>();
    this.viewTurtle = viewTurtle;
    setUpTurtle();
  }

  public TurtleGrid(Turtle turtle, Drawing draw) {
    this(DEFAULT_CANVAS_WIDTH, DEFAULT_CANVAS_HEIGHT, draw, turtle);
  }

  private void setUpTurtle() {
    turtleImageView = new ImageView(new Image(Main.myResources.getString(TURTLE_IMAGE)));
    turtleImageView.setX(centerX);
    turtleImageView.setY(centerY);
    turtleImageView.setFitHeight(40);
    turtleImageView.setFitWidth(40);
    turtleImageView.rotateProperty();
    addListeners();
    myPane.getChildren().add(turtleImageView);

    turtleCenterX = turtleImageView.getFitWidth() / 2;
    turtleCenterY = turtleImageView.getFitHeight() / 2;

    pastX = turtleImageView.getX() + turtleCenterX;
    pastY = turtleImageView.getY() + turtleCenterY;
  }

  private void addListeners() {
    viewTurtle.coordinatesProperty().addListener(new ChangeListener() {
      @Override
      public void changed(ObservableValue o, Object oldVal, Object newVal) {
        System.out.println("here");
        //System.out.println("X value changed to: " + (viewTurtle.getX() + centerX));
        turtleImageView.setX(viewTurtle.getX() + centerX);
        turtleImageView.setY(-(viewTurtle.getY()) + centerY);
        //System.out.println("Y val on x change:" + -(viewTurtle.getY()) + centerY);
        if (ispenDown) {
          makeLine(pastX, pastY, viewTurtle.getX() + turtleCenterX + centerX,
              -(viewTurtle.getY() - turtleCenterY) + centerY);

        }
        drawAllLines();
        pastX = viewTurtle.getX() + turtleCenterX + centerX;
        pastY = -(viewTurtle.getY() - turtleCenterY) + centerY;
      }
    });

//    viewTurtle.xProperty().addListener(new ChangeListener() {
//      @Override
//      public void changed(ObservableValue o, Object oldVal, Object newVal) {
//        //System.out.println("X value changed to: " + (viewTurtle.getX() + centerX));
//        turtleImageView.setX(viewTurtle.getX() + centerX);
//        //System.out.println("Y val on x change:" + -(viewTurtle.getY()) + centerY);
//        if (ispenDown) {
//          makeLine(pastX, pastY, viewTurtle.getX() + turtleCenterX + centerX,
//              -(viewTurtle.getY() - turtleCenterY) + centerY);
//
//        }
//        drawAllLines();
//        pastX = viewTurtle.getX() + turtleCenterX + centerX;
//        pastY = -(viewTurtle.getY() - turtleCenterY) + centerY;
//      }
//    });
//
//    viewTurtle.yProperty().addListener(new ChangeListener() {
//      @Override
//      public void changed(ObservableValue o, Object oldVal, Object newVal) {
//      //  System.out.println("Y value changed to: " + (-(viewTurtle.getY()) + centerY));
//        turtleImageView.setY(-(viewTurtle.getY()) + centerY);
//        if (ispenDown) {
//          makeLine(pastX, pastY, viewTurtle.getX() + turtleCenterX + centerX,
//              -(viewTurtle.getY() - turtleCenterY) + centerY);
//
//        }
//        drawAllLines();
//        pastX = viewTurtle.getX() + turtleCenterX + centerX;
//        pastY = -(viewTurtle.getY() - turtleCenterY) + centerY;
//      }
//    });

    viewTurtle.angleProperty().addListener(new ChangeListener() {
      @Override
      public void changed(ObservableValue o, Object oldVal, Object newVal) {
        System.out.println("Angle changed to: " + viewTurtle.getDegree());
        turtleImageView.setRotate(viewTurtle.getDegree());
      }
    });

    viewTurtle.isPenDownProperty().addListener(new ChangeListener() {
      @Override
      public void changed(ObservableValue o, Object oldVal, Object newVal) {
        System.out.println("Pen has been changed to: " + viewTurtle.isPenDown());
        ispenDown = viewTurtle.isPenDown();
      }
    });
  }

  public void makeLine(double x1, double y1, double x2, double y2) {
    System.out.println("From: (" + x1 + " , " + y1 + ")   to  (" + x2 + " , " + y2 + ")");
    Line line = new Line(x1, y1, x2, y2);
    line.setStroke(Color.RED);
    linesDrawn.add(line);
    System.out.println("Lines drawn size: " + linesDrawn.size());
   // myPane.getChildren().add(line);
  }

  public void drawAllLines() {
    for (Line line : linesDrawn) {
      if (!myPane.getChildren().contains(line))
        myPane.getChildren().add(line);
    }
  }

  protected Node getTurtleGrid() {
    return retGrid;
  }

  protected void setBackground(Color color) {
    myPane.setBackground(new Background(new BackgroundFill(color, null, null)));
  }

  private void updateTurtle() {

  }

}
