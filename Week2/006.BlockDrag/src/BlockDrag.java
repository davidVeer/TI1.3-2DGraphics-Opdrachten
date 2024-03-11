import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

import MovableObjects.Block;
import MovableObjects.Circle;
import MovableObjects.MovableObject;
import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class BlockDrag extends Application {
    private ResizableCanvas canvas;
    private ArrayList<MovableObject> movableObjects;

    private MyCamera myCamera;


    @Override
    public void start(Stage primaryStage)
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);

        this.myCamera = new MyCamera((int) canvas.getWidth(),(int) canvas.getHeight());

        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("MovableObjects.Block Dragging");
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
            }
        };
        timer.start();


        canvas.setOnMousePressed(e -> mousePressed(e));
        canvas.setOnMouseReleased(e -> mouseReleased(e));
        canvas.setOnMouseDragged(e -> mouseDragged(e));

    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(myCamera.transform());
        graphics.setColor(Color.white);
        graphics.fill(new Rectangle(-1000, -1000, (int) canvas.getWidth() * 6, (int) canvas.getHeight()*6));

        for (MovableObject movableObject : movableObjects) {
            movableObject.draw(graphics);
        }

    }


    public static void main(String[] args)
    {
        launch(BlockDrag.class);
    }

    private void mousePressed(MouseEvent e)
    {

    }

    private void mouseReleased(MouseEvent e)
    {

    }

    private void mouseDragged(MouseEvent e)
    {
        if(e.isPrimaryButtonDown()) {
            for (MovableObject movableObject : movableObjects) {
                movableObject.updateLocation(e);
            }
        }
        else if (e.isSecondaryButtonDown()) {
            myCamera.updateLocation(e);
        }
    }

    public void init(){
        movableObjects = new ArrayList<>();
        movableObjects.add(new Block(new Point2D.Double(50,50),new Rectangle(0,0,50,50),Color.red,0,1,1));
        movableObjects.add(new Block(new Point2D.Double(400,200),new Rectangle(0,0,50,50),Color.orange,0,1,1));
        movableObjects.add(new Block(new Point2D.Double(70,100),new Rectangle(0,0,50,50),Color.green,0,1,1));
        movableObjects.add(new Circle(new Point2D.Double(50,100), new Ellipse2D.Double(0,0,50,50),Color.green,0,1,1));
    }

}
