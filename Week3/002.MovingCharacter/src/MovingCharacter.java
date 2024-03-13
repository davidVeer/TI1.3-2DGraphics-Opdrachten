import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;


import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class MovingCharacter extends Application {
    private ResizableCanvas canvas;
    private BufferedImage spritesheet;
    private ArrayList<BufferedImage> walkingAnimation;
    private Point2D manLocation;

    private Point2D lastMousePoint;
    private int currentImage;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        canvas.setOnMouseDragged(e -> mouseDragged(e));

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
        stage.setTitle("Moving Character");
        stage.show();
        draw(g2d);
    }

    private void mouseDragged(MouseEvent e) {
        manLocation = new Point2D.Double(e.getX(), e.getY());
        if (currentImage < walkingAnimation.size() - 1 ) {
            currentImage++;
        } else {
            currentImage = 0;
        }

        lastMousePoint = new Point2D.Double(e.getX(), e.getY());
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.drawImage(walkingAnimation.get(currentImage), (int) manLocation.getX(), (int) manLocation.getY(), null);
    }

    public void createSubImages() {
        walkingAnimation = new ArrayList<>();
        try {
            spritesheet = ImageIO.read(getClass().getResource("/images/sprite.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < 64; i++) {
            walkingAnimation.add(spritesheet.getSubimage(64 * (i % 6), 64 * 4, 64, 64));
        }

    }

    public void update(double deltaTime) {
    }

    public static void main(String[] args) {
        launch(MovingCharacter.class);
    }

    public void init() {
        manLocation = new Point2D.Double(0, 0);
        lastMousePoint = new Point2D.Double(0, 0);
        createSubImages();
        currentImage = 0;
    }
}
