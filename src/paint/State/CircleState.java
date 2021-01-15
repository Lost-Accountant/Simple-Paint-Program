package paint.State;



import paint.Configuration;
import paint.Point;
import paint.Shape.Circle;

import java.awt.event.MouseEvent;

public class CircleState implements ShapeState{
    private Circle circleCreated;
    private boolean completed;
    private Configuration configuration;

    public CircleState(Configuration configuration){
        this.circleCreated = null;
        this.completed = false;
        this.configuration = configuration;
    }

    public void mouseMoved(MouseEvent event){}

    /**
     * When mouse pressed, create a circle centered at where mouse pressed.
     * Before mouse dragged, the radius is zero.
     * @param event
     */
    public void mousePressed(MouseEvent event){
        if(circleCreated == null) {
            Point centre = new Point(event.getX(), event.getY(), configuration);
            circleCreated = new Circle(centre, 0, configuration);
        }
    }

    /**
     * When mouse dragged, increase the radius of the circle by taking the distance
     * between centre and current position.
     * @param event
     */
    public void mouseDragged(MouseEvent event){
        if(this.circleCreated != null) {
            Point currentPosition = new Point(event.getX(), event.getY(), null);
            int newRadius = circleCreated.getCentre().getDistance(currentPosition);
            circleCreated.setRadius(newRadius);
        }
    }

    /**
     * When mouse released, stopped changing the radius by
     * setting the radius one last time.
     */
    public void mouseReleased(MouseEvent event){
        if(this.circleCreated != null){
            Point currentPosition = new Point(event.getX(), event.getY(), null);
            int newRadius = circleCreated.getCentre().getDistance(currentPosition);
            circleCreated.setRadius(newRadius);
            this.completed = true;
        }
    }

    public void mouseEntered(MouseEvent event){}

    public void mouseExited(MouseEvent event){}

    public void mouseClicked(MouseEvent event){}

    public Circle getShapeCreated(){
        return this.circleCreated;
    }

    public void reset(){
        this.circleCreated = null;
        this.completed = false;
    }

    public boolean isCompleted(){
        return this.completed;
    }

    public Circle getCreation(){
        return getShapeCreated();
    }

    public void setConfiguration(Configuration configuration){
        this.configuration = configuration;
        if (this.getCreation() != null) {
            this.getCreation().setConfiguration(configuration);
        }
    }

    public Configuration getConfiguration(){
        return this.configuration;
    }
}
