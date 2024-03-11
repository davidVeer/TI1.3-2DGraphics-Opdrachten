import javafx.scene.input.MouseEvent;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class MyCamera {
    private int zoom = 1;
    private Point2D centerPoint;
    private Point2D lastMousePosition;
    private int canvasCenterX;
    private int canvasCenterY;

    public MyCamera(int canvasWidth, int canvasHeight) {
        this.canvasCenterX = canvasWidth / 2;
        this.canvasCenterY = canvasHeight / 2;
        centerPoint = new Point2D.Double(0, 0);
        lastMousePosition = new Point2D.Double(canvasCenterX, canvasCenterY);
    }

    public AffineTransform transform() {
        AffineTransform transform = new AffineTransform();
        transform.translate(canvasCenterX, canvasCenterY);
        transform.scale(zoom, zoom);
        transform.translate(centerPoint.getX(), centerPoint.getY());
        return transform;
    }

    public void updateLocation(MouseEvent e) {
        centerPoint = new Point2D.Double(
                centerPoint.getX() - (lastMousePosition.getX() - e.getX()) / zoom,
                centerPoint.getY() - (lastMousePosition.getY() - e.getY()) / zoom
        );

        lastMousePosition = new Point2D.Double(e.getX(),e.getY());
    }
}
