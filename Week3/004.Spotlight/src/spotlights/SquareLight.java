package spotlights;

import java.awt.*;


public class SquareLight implements SpotlightShape{
    private int locationX;
    private int locationY;
    private Rectangle lightShape;

    public SquareLight() {
        this.locationX = 0;
        this.locationY = 0;
        this.lightShape = new Rectangle(locationX,locationY,50,50);
    }

    public void update(){
        lightShape = new Rectangle(locationX, locationY ,(int) lightShape.getWidth(),(int) lightShape.getHeight());
    }

    public Shape getShape(){
        return lightShape;
    }


    public void setLocation(int newLocationX, int newLocationY) {
        locationX = newLocationX - (int) lightShape.getWidth()/2;
        locationY = newLocationY - (int) lightShape.getHeight()/2;
    }

}
