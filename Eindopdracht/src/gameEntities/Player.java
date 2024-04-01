package gameEntities;

import java.awt.*;

import gameEntities.EntityProperties.CharacterDirections;
import gameEntities.EntityProperties.GameEntity;
import gameEntities.EntityProperties.HitBoxType;


import javafx.scene.input.KeyEvent;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Player
        implements GameEntity
{

    //movement and location
    private boolean isMoving;
    private double angle;
    private boolean isRotating;
    private CharacterDirections direction;

    //animations
    private ArrayList<ArrayList<BufferedImage>> animations;
    private ArrayList<BufferedImage> currentAnimation;
    private int animationFrame;

    // body and hitbox
    private Body playerBody;
    private double scale;
    private Vector2 offset;
    private double rotation;
    private final HitBoxType hitBoxType;
    private int health;

    public Player(Body body, String folderName, int spriteDimentions, double scale, Vector2 offset) {
        // general initialisation
        this.health = 100;
        this.playerBody = body;
        this.scale = scale;
        this.offset = offset;
        hitBoxType = HitBoxType.FRIENDLY;

        // movement and location initialisation
        this.isMoving = false;
        this.isRotating = false;
        this.rotation = 0;
        this.angle = 0;

        // animation initialisation
        this.animations = new ArrayList<>();
        this.animationFrame = 0;
        initialiseAnimations(folderName, spriteDimentions);
        this.currentAnimation = animations.get(0);
    }

    public void setDirection(CharacterDirections direction) {
        this.direction = direction;

        //setting animation and movement acording to direction
        switch (this.direction) {
            case FORWARD:
                this.currentAnimation = this.animations.get(1);
                break;
            case TURNING_LEFT:
                this.rotation = -10;
                break;
            case TURNING_RIGHT:
                this.rotation = 10;
                break;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (isMoving)
            return;

        String character = e.getCharacter().toLowerCase();
        switch (character) {
            case "w":
                System.out.println("w");
                this.isMoving = true;
                setDirection(CharacterDirections.FORWARD);
                break;
            case "a":
                System.out.println("a");
                this.isMoving = true;
                setDirection(CharacterDirections.TURNING_LEFT);
                break;
            case "d":
                System.out.println("d");
                this.isMoving = true;
                setDirection(CharacterDirections.TURNING_RIGHT);
                break;
            case " ":
                currentAnimation = animations.get(2);
        }

        this.animationFrame = 0;
    }

    public void keyReleased(KeyEvent e) {
        this.isMoving = false;
    }

    @Override
    public void update() {
        //updating image
        animationFrame++;

        if (!isMoving && animationFrame >= currentAnimation.size()) {
            this.currentAnimation = this.animations.get(0);
            this.animationFrame = 0;
        } else if (animationFrame >= currentAnimation.size()) {
            animationFrame = 0;
        }


        // updating location if player is moving
        if (isMoving) {
            switch (direction) {
                case FORWARD:
                    playerBody.translate(
                            0.25 * Math.cos(-angle / 57), 0.25 * Math.sin(-angle / 57)
                    );
                    break;
                case TURNING_LEFT:
                    isRotating = true;
                    rotation -= 0.5;
                    angle += rotation;
                    System.out.println(rotation);
                    break;
                case TURNING_RIGHT:
                    isRotating = true;
                    rotation += 0.5;
                    angle += rotation;
                    System.out.println(rotation);
                    break;
            }
        }
        //making rotation stop slowly
        else if (isRotating) {
            switch (direction) {
                case TURNING_LEFT:
                    rotation += 2;
                    if (rotation >= 0) {
                        rotation = 0;
                        isRotating = false;
                    }
                    angle += rotation;
                    break;
                case TURNING_RIGHT:
                    rotation -= 2;
                    if (rotation <= 0) {
                        rotation = 0;
                        isRotating = false;
                    }
                    angle += rotation;
                    break;
            }

        }
        if (angle >= 360)
            angle -= 360;

        System.out.println(health);
    }

    @Override
    public void draw(Graphics2D graphics) {
        AffineTransform tx = new AffineTransform();
        tx.translate(playerBody.getTransform().getTranslationX() * 100, playerBody.getTransform().getTranslationY() * 100);
        tx.scale(scale, -scale);
        tx.translate(offset.x, offset.y);
        tx.rotate(Math.toRadians(this.angle));

        tx.translate(-currentAnimation.get(animationFrame).getWidth() / 2.0,
                -currentAnimation.get(animationFrame).getHeight() / 2.0);

        graphics.drawImage(currentAnimation.get(animationFrame), tx, null);
    }

    @Override
    public void initialiseAnimations(String folderName, int spriteDimentions) {
        ArrayList<BufferedImage> spriteSheets = new ArrayList<>();
        int horizontalImageAmount;

        try {
            //adding animation spriteSheets
            spriteSheets.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(folderName + "/Idle.png"))));
            spriteSheets.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(folderName + "/Move.png"))));
            spriteSheets.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(folderName + "/Attack_1.png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (BufferedImage spriteSheet : spriteSheets) {
            horizontalImageAmount = spriteSheet.getWidth() / spriteDimentions;
            //adding arraylists of animationFrames to animations
            ArrayList<BufferedImage> tempAnimationList = new ArrayList<>();
            for (int i = 0; i < horizontalImageAmount; i++) {
                tempAnimationList.add(spriteSheet.getSubimage(
                        i * spriteDimentions, 0,
                        spriteDimentions, spriteDimentions));
            }
            animations.add(tempAnimationList);
        }
    }

    @Override
    public void damage(int damage) {

    }

    @Override
    public boolean checkContact(GameEntity entityToCheck) {
        return (this.playerBody.isInContact(entityToCheck.getBody()) &&
                entityToCheck.getHitBoxType().equals(HitBoxType.ENEMY) || entityToCheck.getHitBoxType().equals(HitBoxType.ENEMY_BULLET));
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public HitBoxType getHitBoxType() {
        return this.hitBoxType;
    }

    @Override
    public Body getBody() {
        return this.playerBody;
    }
    public double getPlayerX() {
        return playerBody.getTransform().getTranslationX();
    }
    public double getPlayerY() {
        return playerBody.getTransform().getTranslationY();
    }

    public double getAngle() {
        return angle;
    }

}
