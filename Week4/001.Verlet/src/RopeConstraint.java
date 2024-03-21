import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class RopeConstraint implements Constraint{
    private final Particle a;
    private final Particle b;
    private final double maxDistance;

    Point2D BA;
    double length;

    public RopeConstraint(Particle a, Particle b) {
        this.a = a;
        this.b = b;
        BA = new Point2D.Double(b.getPosition().getX() - a.getPosition().getX(), b.getPosition().getY() - a.getPosition().getY());
        length = BA.distance(0, 0);
        this.maxDistance = length;
    }

    @Override
    public void satisfy() {
        double currentDistance = a.getPosition().distance(b.getPosition());
        double adjustmentDistance = 0;
        if (currentDistance > maxDistance)
            adjustmentDistance = (currentDistance - maxDistance) / 2;


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
        if (length > maxDistance)
            g2d.setColor(new Color((int) (4*(length - maxDistance)),(int) (255 - 4*(length - maxDistance)),0));
        g2d.draw(new Line2D.Double(a.getPosition(), b.getPosition()));
    }
}
