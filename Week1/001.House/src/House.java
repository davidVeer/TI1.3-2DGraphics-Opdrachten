import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class House extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1024, 768);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("House");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {
        graphics.drawLine(100,200,100,400);
        graphics.drawLine(400,200,400,400);
        graphics.drawLine(100,400,400,400);
        graphics.drawLine(100,200,250,50);
        graphics.drawLine(400,200,250,50);

        drawRect(150,400,200,300,graphics);
        drawRect(250,250,350,300,graphics);
    }



    public static void main(String[] args) {
        launch(House.class);
    }


    public void drawRect(int x1, int y1, int x2, int y2, FXGraphics2D graphics){
        graphics.drawLine(x1, y1, x1, y2);
        graphics.drawLine(x1, y1, x2, y1);
        graphics.drawLine(x2, y1, x2, y2);
        graphics.drawLine(x1, y2, x2, y2);
    }
}


