package paint.Shape;

import paint.Configuration;
import paint.Point;

/**
 * Draw a rectangle that is part of the shape interface.
 * With width and height that can be the same or different.
 */
public class Rectangle implements Shape{
    private int width;
    private int height;
    private Point centre;
    private Configuration configuration;

    public Rectangle(Point centre, int height, int width, Configuration configuration){
        this.centre = centre;
        this.height = height;
        this.width = width;
        this.configuration = configuration;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Point getCentre() {
        return centre;
    }

    public void setCentre(Point centre) {
        this.centre = centre;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "centre=" + centre.toStringSimplified() +
                ", width=" + width +
                ", height=" + height +
                ", configuration=" + configuration +
                '}';
    }
}
