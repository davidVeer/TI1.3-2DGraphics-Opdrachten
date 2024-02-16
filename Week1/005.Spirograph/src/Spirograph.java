import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Spirograph extends Application {
    private TextField v1;
    private TextField v2;
    private TextField v3;
    private TextField v4;
    private TextField v5;
    private TextField v6;
    private TextField v7;

    private Canvas canvas;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        canvas = new Canvas(1920, 1080);
       
        VBox mainBox = new VBox();
        HBox topBar = new HBox();
        mainBox.getChildren().add(topBar);
        mainBox.getChildren().add(new Group(canvas));
        
        topBar.getChildren().add(v1 = new TextField("300"));
        topBar.getChildren().add(v2 = new TextField("1"));
        topBar.getChildren().add(v3 = new TextField("300"));
        topBar.getChildren().add(v4 = new TextField("10"));
        topBar.getChildren().add(v5 = new TextField("255"));
        topBar.getChildren().add(v6 = new TextField("0"));
        topBar.getChildren().add(v7 = new TextField("0"));
                
        v1.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
        v2.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
        v3.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
        v4.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));

        v5.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
        v6.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
        v7.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));

        
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(mainBox));
        primaryStage.setTitle("Spirograph");
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics) {
        graphics.translate(this.canvas.getWidth()/2, this.canvas.getHeight()/2);
        graphics.scale( 1, -1);

        graphics.setColor(Color.red);
        graphics.drawLine(-1000,0,1000,0);
        graphics.setColor(Color.green);
        graphics.drawLine(0,-1000,0,1000);
        graphics.setColor(new Color(Integer.parseInt(v5.getText()),Integer.parseInt(v6.getText()) ,Integer.parseInt(v7.getText())));

        double resolution = 0.01;
        double lastY = 0;
        double lastX = 0;

        for(double i = 0; i < 100; i += resolution)
        {
            float x1 = (float) (Integer.parseInt(v1.getText()) * Math.cos(Integer.parseInt(v2.getText()) * i)
                    + Integer.parseInt(v3.getText()) * Math.cos(Integer.parseInt(v4.getText()) * i));

            float y1 = (float) (Integer.parseInt(v1.getText()) * Math.sin(Integer.parseInt(v2.getText()) * i)
                    + Integer.parseInt(v3.getText()) * Math.sin(Integer.parseInt(v4.getText()) * i));

            graphics.draw(new Line2D.Double(x1, y1, lastX, lastY));
            lastX = x1;
            lastY = y1;
        }

        graphics.translate(-this.canvas.getWidth()/2, -this.canvas.getHeight()/2);
    }
    
    
    
    public static void main(String[] args) {
        launch(Spirograph.class);
    }

}
