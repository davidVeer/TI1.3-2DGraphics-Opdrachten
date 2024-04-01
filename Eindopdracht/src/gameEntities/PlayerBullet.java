package gameEntities;

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

public class PlayerBullet
        implements GameEntity
{
    //animation attributes
    private ArrayList<BufferedImage> animation;
    private int animationFrame;

    //body and hitbox attributes
    private double rotation;
    private double scale;
    private Body bulletBody;
    private Vector2 offset;
    private final HitBoxType hitBoxType;
    private int health;

    public PlayerBullet(String folderName, int spriteDimentions, double rotation, double scale, Body bulletBody, Vector2 offset) {
        this.scale = scale;
        this.bulletBody = bulletBody;
        this.offset = offset;
        this.rotation = rotation;
        this.hitBoxType = HitBoxType.FRIENDLY_BULLET;
        this.health = 1;
        bulletBody.applyForce(new Vector2(100 * Math.cos(-rotation / 57),100* Math.sin(-rotation / 57)));

        initialiseAnimations(folderName, spriteDimentions);
        this.animationFrame = 0;
    }

    @Override
    public void draw(Graphics2D graphics) {
        AffineTransform tx = new AffineTransform();
        tx.translate(bulletBody.getTransform().getTranslationX() * 100, bulletBody.getTransform().getTranslationY() * 100);
        tx.scale(scale, -scale);
        tx.translate(offset.x, offset.y);
        tx.rotate(Math.toRadians(this.rotation));

        tx.translate(-animation.get(animationFrame).getWidth() / 2.0,
                -animation.get(animationFrame).getHeight() / 2.0);

        graphics.drawImage(animation.get(animationFrame), tx, null);
    }

    @Override
    public void update() {
        animationFrame++;
        if (animationFrame >= animation.size())
            animationFrame = 0;
    }

    @Override
    public void initialiseAnimations(String folderName, int spriteDimentions) {
        animation = new ArrayList<>();
        try {
            animation.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(folderName + "/Charge_1.png"))));
            animation.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(folderName + "/Charge_2.png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void damage(int damage) {

    }

    @Override
    public boolean checkContact(GameEntity entityToCheck) {
        return  (this.bulletBody.isInContact(entityToCheck.getBody()) &&
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
        return this.bulletBody;
    }
}