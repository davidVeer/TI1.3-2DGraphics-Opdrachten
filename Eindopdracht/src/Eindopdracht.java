import gameEntities.Bullet;
import gameEntities.GameEntity;
import gameEntities.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;


public class Eindopdracht extends Application {

    private ResizableCanvas canvas;
    private Player player;
    private World world;
    double timePassed;
    private boolean debugSelected;
    private ArrayList<GameEntity> entities;
    private Camera camera;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(this::draw, mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        this.camera = new Camera(canvas, this::draw , graphics);
        init();

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1) {
                    last = now;
                }
                update((now - last) / 1000000000.0);
                last = now;
                draw(graphics);
            }
        }.start();

        canvas.setFocusTraversable(true);
        canvas.setOnKeyTyped(this :: keyPressed);
        canvas.setOnKeyReleased(this :: keyReleased);

        stage.setScene(new Scene(mainPane));
        stage.setTitle("untitled astroid & defenders clone");
        stage.show();
        draw(graphics);
    }

    private void keyReleased(KeyEvent keyEvent) {
        player.keyReleased(keyEvent);
    }

    private void keyPressed(KeyEvent keyEvent) {
        player.keyPressed(keyEvent);

        switch (keyEvent.getCharacter().toLowerCase()){
            case " ":
                Body bulletBody = new Body();
                bulletBody.addFixture(Geometry.createRectangle(0.25,0.25));
                bulletBody.translate(new Vector2(player.getPlayerX(), player.getPlayerY()));
                bulletBody.setMass(MassType.INFINITE);

                world.addBody(bulletBody);

                entities.add(new Bullet(
                        "/Fighter", 28, player.getRotation(),
                        1 , bulletBody, new Vector2(0,0)));
            case "p":
                Body enemyBody;
                break;
            case "0":
                debugSelected = !debugSelected;
                break;
        }
    }

    private void update(double deltaTime) {
        timePassed += deltaTime;

        if (timePassed >= 0.16){
            for (GameEntity entity : entities) {
                entity.update();
            }
            timePassed -= 0.16;
        }
    }

    private void draw(FXGraphics2D graphics) {
        graphics.transform(new AffineTransform());
        graphics.setBackground(Color.BLACK);
        graphics.clearRect(0,0, (int) canvas.getWidth(), (int) canvas.getHeight());
        AffineTransform originalTransform = graphics.getTransform();

        graphics.setTransform(camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()));
        graphics.scale(0.5, -0.5);

        for (GameEntity entity : entities) {
            entity.draw(graphics);
        }

        if (debugSelected) {
            graphics.setColor(Color.blue);
            DebugDraw.draw(graphics, world, 100);
        }


        graphics.setTransform(originalTransform);
    }

    public void init(){
        this.timePassed = 0.0;

        Body playerBody = new Body();
        playerBody.addFixture(Geometry.createRectangle(0.4,0.4));
        playerBody.translate(new Vector2(-0.5,0.5));
        playerBody.setMass(MassType.INFINITE);

        this.world = new World();
        world.setGravity(new Vector2(0,1.62));

        world.addBody(playerBody);

        player = new Player(playerBody ,"/Fighter",192, 1, new Vector2(0,0));

        entities = new ArrayList<>();
        entities.add(player);
    }

}
