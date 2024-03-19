package spotlights;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class CircleLight implements SpotlightShape{

    private int locationX;
    private int locationY;
    private Ellipse2D lightShape;

    public CircleLight() {
        this.locationX = 0;
        this.locationY = 0;
        this.lightShape = new Ellipse2D.Double(locationX,locationY,50,50);
    }

    @Override
    public void update() {
        lightShape = new Ellipse2D.Double(locationX, locationY ,(int) lightShape.getWidth(),(int) lightShape.getHeight());
    }

    @Override
    public Shape getShape() {
        return lightShape;
    }

    @Override
    public void setLocation(int newLocationX, int newLocationY) {
        locationX = newLocationX - (int) lightShape.getWidth()/2;
        locationY = newLocationY - (int) lightShape.getHeight()/2;
    }
}
