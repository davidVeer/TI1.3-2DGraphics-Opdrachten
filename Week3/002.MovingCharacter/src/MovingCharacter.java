import java.awt.*;
import java.awt.geom.*;

import Animated_Objects.Character;
import Animated_Objects.animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;


import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class MovingCharacter extends Application {
//    I went way overboard because I wanted to experiment.
//    not necessarily the most efficient way to do it but this way I can expand with different animated entities, like coins or enemies IDK.
//    I also didn't follow the exercise quite right because I forgot to properly read it

    private ResizableCanvas canvas;
    private Character person;
    private double timerValue;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        canvas.setOnMouseDragged(e -> mouseDragged(e));
        canvas.setOnMouseReleased(e -> mouseReleased(e));
        canvas.setOnMousePressed(e -> mousePressed(e));

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);

                last = now;
                draw(g2d);
            }
        }.start();


        stage.setScene(new Scene(mainPane));
        stage.setTitle("Moving Animated_Objects.Character");
        stage.show();
        draw(g2d);
    }

    private void mousePressed(MouseEvent e) {
        if (e.isSecondaryButtonDown()){
            person.setNewAnimation(animation.DYING);
            person.setAnimated(true);
        }
    }

    private void mouseReleased(MouseEvent e) {
        person.setNewAnimation(animation.JUMPING);
        person.setAnimated(false);
    }

    private void mouseDragged(MouseEvent e) {
        person.setCharacterLocation(new Point2D.Double(e.getX(), e.getY()));
        person.setNewAnimation(animation.WALKING);
        person.setAnimated(true);
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.GRAY);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        person.draw(graphics);
    }


    public void update(double deltaTime) {
        timerValue += deltaTime;
        if (timerValue > 0.064) {
            person.update();
            timerValue = 0;
        }
    }

    public static void main(String[] args) {
        launch(MovingCharacter.class);
    }

    public void init() {
        person = new Character("/images/sprite.png", new Point2D.Double(0, 0));
        timerValue = 0;
    }

}
