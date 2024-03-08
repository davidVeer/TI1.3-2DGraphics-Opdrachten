import java.awt.*;


import java.awt.geom.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import java.awt.geom.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;


public class GradientPaintExercise extends Application {
    private ResizableCanvas canvas;
    Point2D centerPoint;



    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);

        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        centerPoint = new Point2D.Double(canvas.getWidth()/2, canvas.getHeight()/2);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                 draw(graphics);
            }
        }.start();

        canvas.setOnMouseMoved(e -> mouseDragged(e));

        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("GradientPaint");
        primaryStage.show();
    }

    private void mouseDragged(MouseEvent e) {
        System.out.println("you moved me");
        Point2D mouselocation = new Point2D.Double(e.getX(),e.getY());
        centerPoint.setLocation(mouselocation);
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);

        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        float[] fractions = {0.2f, 0.3f, 0.4f};
        Color[] colors = {Color.CYAN, Color.YELLOW, Color.magenta};

        RadialGradientPaint paint = new RadialGradientPaint(centerPoint, 20, fractions, colors, MultipleGradientPaint.CycleMethod.REFLECT);

        graphics.setPaint(paint);
        graphics.fillRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
    }

    public static void main(String[] args)
    {
        launch(GradientPaintExercise.class);
    }

}


