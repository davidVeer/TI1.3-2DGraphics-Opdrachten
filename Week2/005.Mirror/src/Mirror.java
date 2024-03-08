import java.awt.*;
import java.awt.geom.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Mirror extends Application {
    ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw(graphics);
            }
        };
        timer.start();

        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Mirror");
        primaryStage.show();
//        draw(graphics);
    }


    public void draw(FXGraphics2D graphics)
    {
        //setting canvas to draw graph
        graphics.translate(canvas.getWidth()/2,canvas.getHeight()/2);
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        //drawing graph
        graphics.setColor(Color.red);
        graphics.drawLine(-1000,0,1000,0);
        graphics.setColor(Color.green);
        graphics.drawLine(0,-1000,0,1000);
        graphics.setColor(Color.black);


        //creating an affineTransform to draw a line with midpoint (0,150) at 45 degrees
        AffineTransform tx = new AffineTransform();
        tx.translate(0,-150);
        tx.rotate(Math.toRadians(-45));

        //drawing line, with a square and a mirrored square on the other side
        graphics.draw(tx.createTransformedShape(new Line2D.Double(0,-100,0,100)));
        Rectangle rect = new Rectangle(50,-25,50,50);
        graphics.draw(tx.createTransformedShape(rect));
        tx.scale(-1,1);
        graphics.draw(tx.createTransformedShape(rect));

        //resetting canvas to 0,0
        graphics.translate(-canvas.getWidth()/2,-canvas.getHeight()/2);
    }


    public static void main(String[] args)
    {
        launch(Mirror.class);
    }

}
