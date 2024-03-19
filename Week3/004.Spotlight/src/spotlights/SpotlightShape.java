package spotlights;

import java.awt.*;

public interface SpotlightShape {
    void update();

    Shape getShape();

    void setLocation(int newLocationX, int newLocationY);
}
