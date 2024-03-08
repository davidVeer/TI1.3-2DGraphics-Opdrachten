import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Colors extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Colors");
        primaryStage.show();
    }

    public void drawRectangle(int x, int y, int width, int height, Color color, FXGraphics2D graphics2D){
        graphics2D.setColor(color);
        graphics2D.fillRect(x,y,width,height);
    }

    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        drawRectangle(50 , 100, 50, 50, Color.magenta, graphics);
        drawRectangle(100 , 100, 50, 50, Color.red, graphics);
        drawRectangle(150 , 100, 50, 50,  Color.ORANGE, graphics);
        drawRectangle(200 , 100, 50, 50, Color.YELLOW, graphics);
        drawRectangle(250 , 100, 50, 50, Color.GREEN, graphics);
        drawRectangle(300 , 100, 50, 50, Color.CYAN, graphics);
        drawRectangle(350 , 100, 50, 50, Color.BLUE, graphics);
        drawRectangle(400 , 100, 50, 50,Color.pink, graphics);
        drawRectangle(450 , 100, 50, 50, Color.WHITE, graphics);
        drawRectangle(500 , 100, 50, 50, Color.lightGray, graphics);
        drawRectangle(550 , 100, 50, 50, Color.gray, graphics);
        drawRectangle(600 , 100, 50, 50, Color.DARK_GRAY, graphics);
        drawRectangle(650 , 100, 50, 50, Color.BLACK, graphics);






    }


    public static void main(String[] args)
    {
        launch(Colors.class);
    }

}
