package gameEntities;

import java.awt.*;
import javafx.scene.input.KeyEvent;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player implements GameEntity {

    //movement and location
    private Point2D location;
    private Point2D movementDirection;
    private boolean isMoving;

    //animations
    private ArrayList<ArrayList<BufferedImage>> animations;
    private ArrayList<BufferedImage> currentAnimation;
    private int animationFrame;

    // general
    private CharacterDirections direction;
    private Body playerBody;
    private double scale;
    private Vector2 offset;

    public Player(Body body, BufferedImage spriteSheet, int spriteDimentions, double scale, Vector2 offset) {
        // general initialisation
        this.playerBody = body;
        this.scale = scale;
        this.offset = offset;

        // movement and location initialisation
        isMoving = false;
        this.location = new Point2D.Double(300,200);
        movementDirection = new Point2D.Double(0,0);

        // animation initialisation
        animations = new ArrayList<>();
        this.animationFrame = 0;
        initialiseAnimations(spriteSheet, spriteDimentions);
        setDirection(CharacterDirections.RIGHT);
    }

    public void setDirection(CharacterDirections direction){
        this.direction = direction;

        //setting animation and movement acording to direction
        switch (this.direction){
            case UP:
                this.currentAnimation = this.animations.get(3);
                this.movementDirection = new Point2D.Double(0,0.2);
                break;
            case DOWN:
                this.currentAnimation = this.animations.get(0);
                this.movementDirection = new Point2D.Double(0,-0.2);
                break;
            case LEFT:
                this.currentAnimation = this.animations.get(1);
                this.movementDirection = new Point2D.Double(-0.2,0);
                break;
            case RIGHT:
                this.currentAnimation = this.animations.get(2);
                this.movementDirection = new Point2D.Double(0.2,0);
                break;
        }

        this.animationFrame = 0;
    }

    public void keyPressed(KeyEvent e){
        if (isMoving)
            return;

        isMoving = true;
        String character = e.getCharacter().toLowerCase();
        switch (character){
            case "w":
                System.out.println("w");
                setDirection(CharacterDirections.UP);
                break;
            case "a":
                System.out.println("a");
                setDirection(CharacterDirections.LEFT);
                break;
            case "s":
                System.out.println("s");
                setDirection(CharacterDirections.DOWN);
                break;
            case "d":
                System.out.println("d");
                setDirection(CharacterDirections.RIGHT);
                break;
        }
    }

    public void keyReleased(KeyEvent e){
        isMoving = false;
    }

    @Override
    public void update(){
        //updating image
        animationFrame ++;
        if (animationFrame > currentAnimation.size()-1){
            animationFrame = 0;
        }

        // updating location if player is moving
        if (isMoving)
            playerBody.translate(
                    playerBody.getLocalCenter().x + movementDirection.getX(),
                    playerBody.getLocalCenter().y + movementDirection.getY()
            );
    }

    @Override
    public void draw(Graphics2D graphics2D){
        AffineTransform tx = new AffineTransform();
        tx.translate(playerBody.getTransform().getTranslationX() * 100,
                playerBody.getTransform().getTranslationY() * 100);
        tx.scale(scale, -scale);
        tx.translate(offset.x, offset.y);

        tx.translate(-currentAnimation.get(animationFrame).getWidth() / 2.0,
                -currentAnimation.get(animationFrame).getHeight() / 2.0);

        graphics2D.drawImage(currentAnimation.get(animationFrame), tx ,null);
    }

    @Override
    public void initialiseAnimations(BufferedImage spriteSheet, int spriteDimentions){

        //determining amount of images (vertical and horizontal)
        int horizontalImageAmount, verticalImageAmount;
        horizontalImageAmount = spriteSheet.getWidth()/spriteDimentions;
        verticalImageAmount = spriteSheet.getHeight()/spriteDimentions;

        //adding arraylists of animationFrames to animations
        for (int i = 0; i < verticalImageAmount; i++) {
            ArrayList<BufferedImage> tempAnimationList = new ArrayList<>();
            for (int j = 0; j < horizontalImageAmount; j++) {
                tempAnimationList.add(spriteSheet.getSubimage(
                        j*spriteDimentions,i*spriteDimentions,
                        spriteDimentions,spriteDimentions));
            }
            animations.add(tempAnimationList);
        }
    }
}
