import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Screensaver extends Application {
    private ResizableCanvas canvas;
    private ArrayList<Point> points;

    @Override
    public void start(Stage stage) throws Exception{
        //the way I built this was incredibly scuffed, but it works.

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        points.add(new Point(new Point2D.Double(0,0),5,5,canvas, points));
        points.add(new Point(new Point2D.Double(50,0),-5,3,canvas, points));
        points.add(new Point(new Point2D.Double(0,50),9,-5,canvas, points));
        points.add(new Point(new Point2D.Double(50,50),-5,6,canvas, points));
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now)
            {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Screensaver");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.BLACK);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        for (Point point : points) {
            point.draw(graphics);
        }
    }

    public void init()
    {
        points = new ArrayList<>();
    }

    public void update(double deltaTime)
    {
        for (Point point : points) {
            point.update();
        }
    }

    public static void main(String[] args)
    {
        launch(Screensaver.class);
    }

}
