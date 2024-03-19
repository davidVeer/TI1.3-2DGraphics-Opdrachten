import java.awt.*;
import java.awt.geom.*;
import java.sql.SQLOutput;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;
import spotlights.CircleLight;
import spotlights.MoonLight;
import spotlights.SpotlightShape;
import spotlights.SquareLight;

public class Spotlight extends Application {
    private ResizableCanvas canvas;
    private SpotlightShape light;
    private ArrayList<Line2D> lines;
    private ArrayList<Color> colors;
    private ArrayList<SpotlightShape> spotlightShapes;
    private int currentShape;

    @Override
    public void start(Stage stage) throws Exception
    {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(this::draw, mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        Rectangle rectangle = new Rectangle(0,0, (int) canvas.getWidth(), (int) canvas.getHeight());
        g2d.setClip(rectangle);

        for (int i = 0; i < 250; i++) {
            colors.add(new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)));
            lines.add(new Line2D.Double(0,Math.random()*canvas.getHeight(),
               canvas.getWidth(),Math.random()*canvas.getHeight()));
        }

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
        canvas.setOnMouseMoved(this::mouseMoved);
        canvas.setOnMouseClicked(this::mouseClicked);


        stage.setScene(new Scene(mainPane));
        stage.setTitle("Spotlight");
        stage.show();
        draw(g2d);
    }

    private void mouseClicked(MouseEvent e) {
        if (currentShape < spotlightShapes.size()-1)
            currentShape++;
        else
            currentShape = 0;

        System.out.println("mouseWasClicked");
        System.out.println(currentShape);
    }

    private void mouseMoved(MouseEvent e) {
        light.setLocation((int) e.getX(), (int) e.getY());
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        graphics.setClip(light.getShape());

        for (Line2D line : lines) {
            graphics.setColor(colors.get(lines.indexOf(line)));
            graphics.draw(line);
        }

        graphics.setClip(null);
        graphics.setColor(Color.BLACK);
        graphics.draw(light.getShape());

    }



    public void init()
    {
        lines = new ArrayList<>();
        colors = new ArrayList<>();

        currentShape = 0;
        spotlightShapes = new ArrayList<>();
        spotlightShapes.add(new CircleLight());
        spotlightShapes.add(new SquareLight());
        spotlightShapes.add(new MoonLight());
        light = spotlightShapes.get(currentShape);
    }

    public void update(double deltaTime)
    {
        light = spotlightShapes.get(currentShape);
        light.update();
    }

    public static void main(String[] args)
    {
        launch(Spotlight.class);
    }

}
