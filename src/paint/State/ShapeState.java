package paint.State;


import paint.Configuration;
import paint.Shape.Shape;

public interface ShapeState extends State {
    public Shape getShapeCreated();

    public void setConfiguration(Configuration configuration);
}
