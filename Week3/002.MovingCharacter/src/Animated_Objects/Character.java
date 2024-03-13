package Animated_Objects;

import Animated_Objects.Animatable;
import Animated_Objects.animation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Character implements Animatable {

    //character data
    Point2D characterLocation;
    private boolean animated;


    // sprites and animations
    private String fileName;
    private BufferedImage spritesheet;
    private BufferedImage standing;
    private ArrayList<BufferedImage> walkingAnimation;
    private ArrayList<BufferedImage> jumpingAnimation;
    private ArrayList<BufferedImage> dyingAnimation;
    private BufferedImage currentImage;
    private int animationFrame;

    private ArrayList<BufferedImage> currentAnimation;


    public Character(String spritesheet, Point2D startingLocation) {
        this.characterLocation = startingLocation;
        animated = false;

        this.fileName = spritesheet;
        createAnimations();
        this.currentImage = this.standing;
    }

    public void createAnimations() {
        walkingAnimation = new ArrayList<>();
        jumpingAnimation = new ArrayList<>();
        dyingAnimation = new ArrayList<>();


        try {
            spritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResource(fileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //loading standingSprite
        standing = spritesheet.getSubimage(0, 64 * 8, 64, 64);

        //loading walingAnimation
        for (int i = 0; i < 8; i++) {
            walkingAnimation.add(spritesheet.getSubimage(64 * (i % 8), 64 * 4, 64, 64));
        }

        //loading jumpingAnimation
        for (int i = 0; i < 8; i++) {
            jumpingAnimation.add(spritesheet.getSubimage(64 * (i % 8), 64 * 5, 64, 64));
        }

        //loading dyingAnimation
        for (int i = 0; i < 8; i++) {
            dyingAnimation.add(spritesheet.getSubimage(64 * (i % 8), 64 * 2, 64, 64));
        }
    }

    public void draw(Graphics2D graphics) {
        graphics.drawImage(currentImage, (int) characterLocation.getX(), (int) characterLocation.getY(), null);
    }

    public void update() {
        if (animated)
            playAnimation(currentAnimation);
        else
            if (currentAnimation == jumpingAnimation && animationFrame != 0){
                playAnimation(currentAnimation);
            }
            else
                currentImage = standing;
    }

    public void playAnimation(ArrayList<BufferedImage> animation) {
        if (animationFrame < animation.size() - 1) {
            animationFrame++;
            currentImage = animation.get(animationFrame);
        } else if (animationFrame >= animation.size() - 1)
            animationFrame = 0;
    }

    @Override
    public void setNewAnimation(animation newAnimation) {
        if (newAnimation == animation.WALKING)
            currentAnimation = walkingAnimation;
        else if (newAnimation == animation.JUMPING) {
            currentAnimation = jumpingAnimation;
            animationFrame = 1;
        } else if (newAnimation == animation.DYING) {
            currentAnimation = dyingAnimation;
            animationFrame = 0;
        }
        else
            try {
                throw new Exception("this animation doesn't exist for this animatable");
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
    }

    public void setCharacterLocation(Point2D characterLocation) {
        this.characterLocation = characterLocation;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

}
