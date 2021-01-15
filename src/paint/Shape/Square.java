package paint.Shape;


import paint.Configuration;
import paint.Point;

/**
 * Draw a square that is a rectangle with the same
 * width and height.
 */
public class Square extends Rectangle{

    public Square(Point centre, int width, Configuration configuration){
        super(centre, width, width, configuration);
    }

    @Override
    public void setWidth(int width){
        super.setWidth(width);
        super.setHeight(width); // ensure same width and height
    }

    @Override
    public void setHeight(int height){
        super.setHeight(height);
        super.setWidth(height); // ensure same width and height
    }

    @Override
    public String toString() {
        return "Square{" +
                "centre=" + super.getCentre().toStringSimplified() +
                ", width=" + super.getWidth() +
                ", height=" + super.getHeight() +
                ", configuration=" + super.getConfiguration() +
                '}';
    }
}
