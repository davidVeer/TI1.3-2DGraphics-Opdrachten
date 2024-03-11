package MovableObjects;

import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public interface MovableObject {

    void draw(Graphics2D graphics2D);
    AffineTransform transform();
    void setLocation(Point2D newLocation);

    void updateLocation(MouseEvent e);
}
