package Constraints;

import org.jfree.fx.FXGraphics2D;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class DistanceConstraint implements Constraint {

    private double distance;
    private Particle a;
    private Particle b;
    double length;

    public DistanceConstraint(Particle a, Particle b) {
        this(a, b, a.getPosition().distance(b.getPosition()));
    }

    public DistanceConstraint(Particle a, Particle b, double distance) {
        this.a = a;
        this.b = b;
        this.distance = distance;
    }


    @Override
    public void satisfy() {

        double currentDistance = a.getPosition().distance(b.getPosition());
        double adjustmentDistance = (currentDistance - distance) / 2;

        Point2D BA = new Point2D.Double(b.getPosition().getX() - a.getPosition().getX(), b.getPosition().getY() - a.getPosition().getY());
        length = BA.distance(0, 0);
        if (length > 0.0001) // We kunnen alleen corrigeren als we een richting hebben
        {
            BA = new Point2D.Double(BA.getX() / length, BA.getY() / length);
        } else {
            BA = new Point2D.Double(1, 0);
        }

        a.setPosition(new Point2D.Double(a.getPosition().getX() + BA.getX() * adjustmentDistance,
                a.getPosition().getY() + BA.getY() * adjustmentDistance));
        b.setPosition(new Point2D.Double(b.getPosition().getX() - BA.getX() * adjustmentDistance,
                b.getPosition().getY() - BA.getY() * adjustmentDistance));
    }

    @Override
    public void draw(FXGraphics2D g2d) {
        int colorCode = 0;
        //calculating a color value based on the delta between length and distance
        if (length > distance)
            colorCode = (int) (2*(length-distance));
        else if (distance > length) {
            colorCode = (int) (2*(distance-length));
        }

        //checking if colorvalue is above 255
        if (colorCode >= 255)
            colorCode = 254;

        //setting color and drawing line
        if (length != distance)
            g2d.setColor(new Color(colorCode,255 - colorCode,0));
        else
            g2d.setColor(Color.green);
        g2d.draw(new Line2D.Double(a.getPosition(), b.getPosition()));
    }
}
