
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import objects.Block;
import objects.Catapult;
import objects.GameObject;
import objects.Mii;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.imageio.ImageIO;

public class AngryBirds extends Application {

    private ResizableCanvas canvas;
    private World world;
    private MousePicker mousePicker;
    private Camera camera;
    private boolean debugSelected = false;
    private BufferedImage background;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private double scale;

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();

        // Add debug button
        javafx.scene.control.CheckBox showDebug = new CheckBox("Show debug");
        showDebug.setOnAction(e -> {
            debugSelected = showDebug.isSelected();
        });
        mainPane.setTop(showDebug);

        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        camera = new Camera(canvas, this::draw, g2d);
        mousePicker = new MousePicker(canvas);

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1) {
                    last = now;
                }
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        canvas.setOnMousePressed(this::mouseClicked);
        stage.setScene(new Scene(mainPane, 1920, 1080));
        stage.setTitle("Angry Birds");
        stage.show();
        draw(g2d);
    }

    private void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseButton.SECONDARY){
            Body body = new Body();
            body.addFixture(Geometry.createCircle(0.5));
            body.setMass(MassType.NORMAL);
            body.translate(e.getX()/(scale), -e.getY()/(scale));
            world.addBody(body);
            gameObjects.add(new Mii("/casualMii.png", body, new Vector2(0,0) ,0.3, world.getBodies()));

        }
    }

    public void init() {
        scale = 50;

        //making an angry bird (or in this case a casualMii)
        Body miiBody = new Body();
        BodyFixture entityfixture = new BodyFixture(Geometry.createCircle(0.5));
        entityfixture.setRestitution(.25);
        miiBody.translate(-10,5);
        miiBody.addFixture(entityfixture);
        miiBody.setMass(MassType.NORMAL);


        // making a platform
        Body platform = new Body();
        BodyFixture platformfixture = new BodyFixture(Geometry.createRectangle(80,25.8));
        platform.addFixture(platformfixture);
        platform.setMass(MassType.INFINITE);
        platform.translate(0,-20);

        //making a catapult
        Body catapultBody = new Body();
        catapultBody.addFixture(Geometry.createRectangle(2,2));
        catapultBody.setMass(MassType.INFINITE);
        catapultBody.translate(-10,-6.10);

        //adding a couple wooden planks
        Body box = new Body();
        box.addFixture(Geometry.createRectangle(3,.99));
        box.setMass(MassType.NORMAL);
        box.rotate(Math.toRadians(90));
        box.translate(-1.2,-5);

        Body box1 = new Body();
        box1.addFixture(Geometry.createRectangle(3,.99));
        box1.setMass(MassType.NORMAL);
        box1.rotate(Math.toRadians(90));
        box1.translate(0,-5);

        Body box2 = new Body();
        box2.addFixture(Geometry.createRectangle(3,.99));
        box2.setMass(MassType.NORMAL);
        box2.rotate(Math.toRadians(90));
        box2.translate(1.2,-5);

        Body box3 = new Body();
        box3.addFixture(Geometry.createRectangle(4,1.33));
        box3.setMass(MassType.NORMAL);
        box3.translate(1.2,-5);



        world = new World();
        world.setGravity(new Vector2(0, -9.8));

        world.addBody(platform);
        world.addBody(miiBody);
        world.addBody(catapultBody);
        world.addBody(box);
        world.addBody(box1);
        world.addBody(box2);
        world.addBody(box3);

        gameObjects.add(new Block("/groundTile.jpg", platform, new Vector2(0,0),10));
        gameObjects.add(new Mii("/casualMii.png", miiBody, new Vector2(0,-10),0.40, world.getBodies()));
        gameObjects.add(new Catapult("/slingshot.png", catapultBody ,new Vector2(0,0),0.40, world.getBodies()));
        gameObjects.add(new Block("/wewd.jpg", box, new Vector2(0,0) ,0.3));
        gameObjects.add(new Block("/wewd.jpg", box1, new Vector2(0,0) ,0.3));
        gameObjects.add(new Block("/wewd.jpg", box2, new Vector2(0,0) ,0.3));
        gameObjects.add(new Block("/wewd.jpg", box3, new Vector2(0,0) ,0.4));

        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("casualMiiBackground.jpg")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        graphics.drawImage(background,0,0, (int) canvas.getWidth(), (int) canvas.getHeight(),null,null);

        AffineTransform originalTransform = graphics.getTransform();

        graphics.setTransform(camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()));
        graphics.scale(scale/100, -scale/100);

        for (GameObject go : gameObjects) {
            go.draw(graphics);
        }

        if (debugSelected) {
            graphics.setColor(Color.blue);
            DebugDraw.draw(graphics, world, 100);
        }

        graphics.setTransform(originalTransform);
    }

    public void update(double deltaTime) {
        mousePicker.update(world, camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()), scale);
        world.update(deltaTime);
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        }
    }

    public static void main(String[] args) {
        launch(AngryBirds.class);
    }

}
