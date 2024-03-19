package spotlights;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

public class MoonLight implements SpotlightShape{

    private GeneralPath gp;
    private int locationX;
    private int locationY;
    private Shape lightShape;

    public MoonLight() {
        gp = new GeneralPath();
        gp.moveTo(0,0);
        gp.curveTo(50,10,50,70,0,80);
        gp.curveTo(20,60,20,20,0,0);
        gp.closePath();

        lightShape = gp;
    }

    @Override
    public void update() {
        AffineTransform tx = new AffineTransform();
        tx.translate(locationX, locationY);
        lightShape = tx.createTransformedShape(gp);
    }

    @Override
    public Shape getShape() {
        return lightShape;
    }

    @Override
    public void setLocation(int newLocationX, int newLocationY) {
        locationX = newLocationX;
        locationY = newLocationY;
    }
}
