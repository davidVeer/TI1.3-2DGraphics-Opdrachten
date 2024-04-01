package gameEntities;

import gameEntities.EntityProperties.CharacterDirections;
import gameEntities.EntityProperties.GameEntity;
import gameEntities.EntityProperties.HitBoxType;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class EnemyBomber
        implements GameEntity
{


    //movement and location
    private CharacterDirections direction;

    //animations
    private ArrayList<ArrayList<BufferedImage>> animations;
    private ArrayList<BufferedImage> currentAnimation;
    private int animationFrame;

    // body and hitbox
    private Body enemyBody;
    private double scale;
    private Vector2 offset;
    private final HitBoxType hitBoxType;
    private int health = 100;

    public EnemyBomber(String folderName, int spriteDimentions, Body enemyBody, double scale, Vector2 offset) {

        this.enemyBody = enemyBody;
        this.scale = scale;
        this.offset = offset;
        hitBoxType = HitBoxType.ENEMY;

        // animation initialisation
        this.animations = new ArrayList<>();
        this.animationFrame = 0;
        initialiseAnimations(folderName, spriteDimentions);
        this.currentAnimation = animations.get(0);
    }

    @Override
    public void draw(Graphics2D graphics) {
        AffineTransform tx = new AffineTransform();
        tx.translate(enemyBody.getTransform().getTranslationX() * 100, enemyBody.getTransform().getTranslationY() * 100);
        tx.scale(scale, -scale);
        tx.translate(offset.x, offset.y);

        tx.translate(-currentAnimation.get(animationFrame).getWidth() / 2.0,
                -currentAnimation.get(animationFrame).getHeight() / 2.0);

        graphics.drawImage(currentAnimation.get(animationFrame), tx, null);
    }

    @Override
    public void update() {
        //updating image
        animationFrame++;
        if (animationFrame >= currentAnimation.size()) {
            animationFrame = 0;
        }

        System.out.println(health);


    }

    @Override
    public void initialiseAnimations(String folderName, int spriteDimentions) {
        ArrayList<BufferedImage> spriteSheets = new ArrayList<>();
        int horizontalImageAmount;

        try {
            //adding animation spriteSheets
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
        return  (this.enemyBody.isInContact(entityToCheck.getBody()) &&
                entityToCheck.getHitBoxType().equals(HitBoxType.FRIENDLY) || entityToCheck.getHitBoxType().equals(HitBoxType.FRIENDLY_BULLET));
    }

    @Override
    public HitBoxType getHitBoxType() {
        return this.hitBoxType;
    }

    @Override
    public Body getBody() {
        return this.enemyBody;
    }

    @Override
    public int getHealth(){
        return health;
    }

}