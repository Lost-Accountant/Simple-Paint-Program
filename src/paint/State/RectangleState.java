package paint.State;


import paint.Configuration;
import paint.Point;
import paint.Shape.Rectangle;


import java.awt.event.MouseEvent;

public class RectangleState implements ShapeState{
    private Rectangle rectangleCreated;
    private boolean completed;
    private Configuration configuration;

    public RectangleState(Configuration configuration){
        this.rectangleCreated = null;
        this.completed = false;
        this.configuration = configuration;
    }

    /**
     * When mouse pressed, create a rectangle centered at where
     * the mouse position is. Before mouse dragged, the width
     * and height are both zero.
     * @param event
     */
    public void mousePressed(MouseEvent event){
        if(rectangleCreated == null) {
            Point centre = new Point(event.getX(), event.getY(), configuration);
            rectangleCreated = new Rectangle(centre, 0, 0, configuration);
        }
    }

    /**
     * When mouse dragged, update the width and height of the
     * rectangle based on the distance between current position
     * and centre.
     *
     * The height is twice the distance in Y.
     * The width is twice the distance in X.
     *
     * @param event
     */
    public void mouseDragged(MouseEvent event) {
        if (this.rectangleCreated != null) {
            // update width
            rectangleCreated.setWidth(2 * Math.abs(event.getX() - rectangleCreated.getCentre().getX()));
            // update height
            rectangleCreated.setHeight(2 * Math.abs(event.getY() - rectangleCreated.getCentre().getY()));
        }
    }
    /**
     * When mouse is released, stopped changing the width and height
     * by setting the width and height one last time.
     * @param event
     */
    public void mouseReleased(MouseEvent event){
        if (this.rectangleCreated != null){
            // update width
            rectangleCreated.setWidth(2 * Math.abs(event.getX() - rectangleCreated.getCentre().getX()));
            // update height
            rectangleCreated.setHeight(2 * Math.abs(event.getY() - rectangleCreated.getCentre().getY()));

            this.completed = true;
        }
    }

    public void mouseMoved(MouseEvent event) {}

    public void mouseEntered(MouseEvent event){}

    public void mouseExited(MouseEvent event){}

    public void mouseClicked(MouseEvent event){}

    public Rectangle getShapeCreated(){
        return this.rectangleCreated;
    }

    public void reset(){
        this.rectangleCreated = null;
        this.completed = false;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Rectangle getCreation(){
        return getShapeCreated();
    }

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        if (this.getCreation() != null) {
            this.getCreation().setConfiguration(configuration);
        }
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
