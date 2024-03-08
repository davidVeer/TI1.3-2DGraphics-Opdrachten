import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class BlockDrag extends Application {
    ResizableCanvas canvas;
    private ArrayList<Block> blocks;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        init();
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);

        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Block Dragging");
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
            }
        };
        timer.start();


        canvas.setOnMousePressed(e -> mousePressed(e));
        canvas.setOnMouseReleased(e -> mouseReleased(e));
        canvas.setOnMouseDragged(e -> mouseDragged(e));

    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.translate(canvas.getWidth()/2, canvas.getHeight()/2);
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        for (Block block : blocks) {
            block.draw(graphics);
        }
    }


    public static void main(String[] args)
    {
        launch(BlockDrag.class);
    }

    private void mousePressed(MouseEvent e)
    {
    }

    private void mouseReleased(MouseEvent e)
    {

    }

    private void mouseDragged(MouseEvent e)
    {
    }

    public void init(){
        blocks = new ArrayList<>();
        blocks.add(new Block(new Point2D.Double(50,50),new Rectangle(0,0,50,50),0,0,0));
    }

}
