package gameEntities;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface GameEntity {
    void draw(Graphics2D graphics);
    void update();

    void initialiseAnimations(BufferedImage spriteSheet, int spriteDimentions);
}
