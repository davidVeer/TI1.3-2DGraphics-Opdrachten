import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Block {
   private Point2D location;
   private Shape shape;
   private double rotation;

   private double scaleX;
   private double scaleY;

   public Block(Point2D location, Shape shape, double rotation, double scaleX, double scaleY) {
      this.location = location;
      this.shape = shape;
      this.rotation = rotation;
      this.scaleX = scaleX;
      this.scaleY = scaleY;
   }

   public void draw(Graphics2D graphics2D){
      AffineTransform tx = new AffineTransform();
      tx.translate(this.location.getX(),this.location.getY());
      tx.rotate(this.rotation);
      tx.scale(scaleX,scaleY);

      graphics2D.draw(tx.createTransformedShape(shape));
   }
}
