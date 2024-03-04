import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.FillRule;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class YingYang extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Ying Yang");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

    public GeneralPath yang(){
        GeneralPath path = new GeneralPath();
        path.moveTo(300,100);
        path.curveTo(400,100,400,300,300,300);
        path.curveTo(200,300,200,500,300,500);
        path.curveTo(575,500,575,100,300,100);
        path.moveTo(300,100);
        path.closePath();
        return path;
    }

    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.setColor(Color.BLACK);
        graphics.draw(new Ellipse2D.Double(100,100,400,400));
        graphics.fill(yang());

        graphics.setColor(Color.WHITE);
        graphics.fill(new Ellipse2D.Double(275,375,50,50));
        graphics.setColor(Color.BLACK);
        graphics.fill(new Ellipse2D.Double(275,175,50,50));

    }


    public static void main(String[] args)
    {
        launch(YingYang.class);
    }

}
