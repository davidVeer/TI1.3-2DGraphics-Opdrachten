package Animated_Objects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public interface Animatable {
    void draw(Graphics2D graphics);
    void update();
    void playAnimation(ArrayList<BufferedImage> animation);

    void setNewAnimation(animation newAnimation);
}
