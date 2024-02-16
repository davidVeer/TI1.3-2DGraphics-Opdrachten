import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Rainbow extends Application {

    private Canvas canvas;
    @Override
    public void start(Stage primaryStage) throws Exception {
        canvas = new Canvas(1920, 1080);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Rainbow");
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics) {
        graphics.translate(this.canvas.getWidth()/2, this.canvas.getHeight()/2);
        graphics.scale( 1, -1);

        graphics.setColor(Color.red);
        graphics.drawLine(-1000,0,1000,0);
        graphics.setColor(Color.green);
        graphics.drawLine(0,-1000,0,1000);
        graphics.setColor(Color.black);

        float radiusBinnen = 100;
        float radiusBuiten = 500;
        float hoek;

        for(int i = 0; i < 1885; i++) {
            graphics.setColor(Color.getHSBColor(i/1885.0f, 1, 1));
            hoek = i/600.0f;
            float x1 = (float) (radiusBinnen * Math.cos(hoek));
            float y1 = (float) (radiusBinnen * Math.sin(hoek));
            float x2 = (float) (radiusBuiten * Math.cos(hoek));
            float y2 = (float) (radiusBuiten * Math.sin(hoek));

            graphics.drawLine((int) x1,(int) y1,(int) x2,(int) y2);

        }
    }
    
    
    
    public static void main(String[] args) {
        launch(Rainbow.class);
    }

}
