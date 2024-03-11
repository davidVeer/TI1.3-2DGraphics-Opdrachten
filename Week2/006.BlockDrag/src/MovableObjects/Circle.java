package MovableObjects;

import javafx.scene.input.MouseEvent;
import org.dyn4j.geometry.Ellipse;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Circle implements MovableObject {
    private Point2D location;
    private Ellipse2D ellipse;
    private double rotation;
    private double scaleX;
    private double scaleY;
    private Color color;

    public Circle(Point2D location, Ellipse2D shape, Color color, double rotation, double scaleX, double scaleY) {
        this.scaleY = scaleY;
        this.scaleX = scaleX;
        this.rotation = rotation;
        this.color = color;
        this.ellipse = shape;
        setLocation(location);
    }

    public void draw(Graphics2D graphics2D) {
        transform();
        graphics2D.setColor(color);
        graphics2D.fill(transform().createTransformedShape(ellipse));
    }

    public AffineTransform transform() {
        AffineTransform tx = new AffineTransform();
        tx.translate(this.location.getX(), this.location.getY());
        tx.rotate(this.rotation);
        tx.scale(scaleX, scaleY);
        return tx;
    }

    public void setLocation(Point2D newLocation) {
        this.location = newLocation;
        this.location.setLocation(
                this.location.getX() - ellipse.getWidth() / 2,
                this.location.getY() - ellipse.getHeight() / 2
        );
    }

    public void updateLocation(MouseEvent e) {
        if (
                e.getY() >= this.location.getY() &&
                        e.getX() >= this.location.getX() &&
                        e.getY() <= this.location.getY() + this.ellipse.getHeight() &&
                        e.getX() <= this.location.getX() + (this.ellipse.getWidth())
        ) {
            Point2D newLocation = new Point2D.Double(e.getX(), e.getY());
            setLocation(newLocation);
        }
    }
}
