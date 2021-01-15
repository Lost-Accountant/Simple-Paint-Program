package paint.Shape;


import paint.Configuration;
import paint.Point;

/**
 * Draw a circle that is part of the shape interface.
 * width and height are the same, being double the radius
 */
public class Circle implements Shape{
    private Point centre;
    private int radius;
    private Configuration configuration;

    public Circle(Point centre, int radius, Configuration configuration){
        this.centre = centre;
        this.radius = radius;
        this.configuration = configuration;
    }

    public Point getCentre(){
        return centre;
    }

    public int getWidth() {
        return (2 * radius);
    }

    public int getHeight(){
        return (2 * radius);
    }

    public int getRadius(){
        return radius;
    }

    public void setWidth(int width){
        this.radius = width / 2; // integer division
    }

    public void setHeight(int height){
        this.radius = height / 2; // integer division
    }

    public void setRadius(int radius){
        this.radius = radius;
    }

    public void setCentre(Point centre){
        this.centre = centre;
    }

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "centre=" + centre.toStringSimplified() +
                ", width=" + getWidth() +
                ", height=" + getHeight() +
                ", configuration=" + configuration +
                '}';
    }
}
