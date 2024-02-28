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

public class Moon extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Moon");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {


        GeneralPath gp = new GeneralPath();
        gp.moveTo(100.0,100.0);
        gp.curveTo(350,150,350,450,100,500);
        gp.curveTo(200,400,200,200,100,100);
        gp.closePath();

        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.fill(gp);

    }


    public static void main(String[] args)
    {
        launch(Moon.class);
    }

}
