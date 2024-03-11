package MovableObjects;

import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Block implements MovableObject {
    private Point2D location;
    private Rectangle rectangle;
    private double rotation;
    private double scaleX;
    private double scaleY;
    private Color color;

    public Block(Point2D location, Rectangle shape, Color color, double rotation, double scaleX, double scaleY) {
        this.scaleY = scaleY;
        this.scaleX = scaleX;
        this.rotation = rotation;
        this.color = color;
        this.rectangle = shape;
        setLocation(location);
    }

    public void draw(Graphics2D graphics2D) {
        transform();
        graphics2D.setColor(color);
        graphics2D.fill(transform().createTransformedShape(rectangle));
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
                this.location.getX() - rectangle.getWidth() / 2,
                this.location.getY() - rectangle.getHeight() / 2
        );
    }

    public void updateLocation(MouseEvent e) {
        if (
                e.getY() >= this.location.getY() &&
                        e.getX() >= this.location.getX() &&
                        e.getY() <= this.location.getY() + this.rectangle.getHeight() &&
                        e.getX() <= this.location.getX() + (this.rectangle.getWidth())
        ) {
            Point2D newLocation = new Point2D.Double(e.getX(), e.getY());
            setLocation(newLocation);
        }
    }

}
