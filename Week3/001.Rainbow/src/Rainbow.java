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

public class Rainbow extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Rainbow");
        stage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        String word = "this is rainbow?";
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        Font font = new Font("times new roman", Font.ITALIC,60);

        for (int i = 0; i < word.length(); i++) {
            graphics.setColor(Color.getHSBColor(i/(word.length()/2.0f), 1, 1));
            Shape shape = font.createGlyphVector(graphics.getFontRenderContext(), word.substring(i,i+1)).getOutline();
            AffineTransform tx = new AffineTransform();
            tx.translate(300,200);
            tx.rotate(Math.toRadians(-100+(180/(word.length() -1.0f)) *i));
            tx.translate(0,-100);
            graphics.draw(tx.createTransformedShape(shape));
        }

    }


    public static void main(String[] args)
    {
        launch(Rainbow.class);
    }

}
