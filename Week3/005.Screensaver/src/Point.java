import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Point {
    private Point2D location;
    private Point2D[] previousLocations;
    private ArrayList<Point> allPoint;
    private int directionSpeedX;
    private int directionSpeedY;
    private ResizableCanvas canvas;

    public Point(Point2D location, int directionSpeedX, int directionSpeedY, ResizableCanvas canvas, ArrayList<Point> allPoints) {
        this.canvas = canvas;
        this.location = location;
        this.allPoint = allPoints;
        previousLocations = new Point2D[50];
        this.directionSpeedX = directionSpeedX;
        this.directionSpeedY = directionSpeedY;

        for (int i = 0; i < 50; i++) {
            previousLocations[i] = location;
        }
    }

    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.WHITE);
        for (Point point : allPoint) {
            if (point != this){
                graphics.drawLine((int) location.getX(), (int) location.getY(), (int) point.getLocation().getX(), (int) point.getLocation().getY());
                for (int i = 0; i < previousLocations.length -1; i++) {
                    graphics.drawLine(
                            (int) previousLocations[i].getX(),
                            (int) previousLocations[i].getY(),
                            (int) point.getPreviousLocations()[i].getX(),
                            (int) point.getPreviousLocations()[i].getY()
                    );
                }
            }
        }
    }

    public void update() {

        previousLocations[0] = location;
        previousLocations[previousLocations.length-1] = previousLocations[previousLocations.length-2];
        Point2D current;
        Point2D last = previousLocations[0];
        for (int i = 0; i < previousLocations.length-1; i++) {
            if (i + 1 < previousLocations.length) {
                current = previousLocations[i];
                previousLocations[i] = last;

                last = current;
            }
        }


        if (location.getX() + directionSpeedX >= canvas.getWidth() || location.getX() + directionSpeedX <= 0) {
            directionSpeedX *= -1;
        } else if (location.getY() + directionSpeedY >= canvas.getHeight() || location.getY() + directionSpeedY <= 0) {
            directionSpeedY *= -1;
        }

        location = new Point2D.Double(
                location.getX() + directionSpeedX,
                location.getY() + directionSpeedY
        );
    }

    public Point2D getLocation() {
        return location;
    }

    public Point2D[] getPreviousLocations() {
        return previousLocations;
    }
}
