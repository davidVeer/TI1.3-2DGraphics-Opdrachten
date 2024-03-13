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

public class FadingImage extends Application {
    private ResizableCanvas canvas;
    private SlideShow slideShow;
    private double timerValue;

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        timerValue = 0;
        ArrayList<String> filePaths = new ArrayList<>();
        filePaths.add("3rdImage.jpg");
        filePaths.add("download.jpg");
        filePaths.add("fancyImage.jpg");
        filePaths.add("photo.jpg");
        slideShow = new SlideShow(filePaths, (int)canvas.getWidth(), (int) canvas.getHeight());
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
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
        stage.setTitle("Fading image");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        slideShow.draw(graphics);
    }


    public void update(double deltaTime) {
        timerValue += deltaTime;
        if (timerValue > 0.064) {
            slideShow.update();
            timerValue = 0;
        }
    }

    public static void main(String[] args) {
        launch(FadingImage.class);
    }

}
