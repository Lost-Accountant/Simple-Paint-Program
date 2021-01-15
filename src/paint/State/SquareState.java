package paint.State;


import paint.Configuration;
import paint.Point;
import paint.Shape.Square;

import java.awt.event.MouseEvent;

public class SquareState implements ShapeState{
    private Square squareCreated;
    private boolean completed;
    private Configuration configuration;

    public SquareState(Configuration configuration){
        this.squareCreated = null;
        this.completed = false;
        this.configuration = configuration;
    }

    /**
     * When mouse pressed, create a square centered at where
     *      * the mouse position is. Before mouse dragged, the width
     *      * and height are both zero.
     */
    public void mousePressed(MouseEvent event){
        if(squareCreated == null) {
            Point centre = new Point(event.getX(), event.getY(), configuration);
            squareCreated = new Square(centre, 0, configuration);
        }
    }

    /**
     * When mouse dragged, update the width of the square based on the distance between current position
     * and centre.
     *
     * The width is twice the distance in X or Y, whichever is bigger.
     *
     * @param event
     */
    public void mouseDragged(MouseEvent event){
        if(squareCreated != null){
            // update width, taking max distance
            int maxDistance = Math.max(Math.abs(event.getX() - squareCreated.getCentre().getX()),
                    Math.abs(event.getY() - squareCreated.getCentre().getY()));
            squareCreated.setWidth(2 * maxDistance);
        }
    }

    /**
     * When mouse is released, stopped changing the width
     * by setting the width one last time.
     *
     * @param event
     */
    public void mouseReleased(MouseEvent event){
        if(squareCreated != null){
            // update width, taking max distance
            int maxDistance = Math.max(Math.abs(event.getX() - squareCreated.getCentre().getX()),
                    Math.abs(event.getY() - squareCreated.getCentre().getY()));
            squareCreated.setWidth(2 * maxDistance);
            this.completed = true;
        }
    }
    public void mouseMoved(MouseEvent event){}

    public void mouseEntered(MouseEvent event){}

    public void mouseExited(MouseEvent event){}

    public void mouseClicked(MouseEvent event){}

    public Square getShapeCreated(){
        return this.squareCreated;
    }

    public void reset(){
        this.squareCreated = null;
        this.completed = false;
    }

    public boolean isCompleted(){
        return this.completed;
    }

    @Override
    public Square getCreation() {
        return getShapeCreated();
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        if (this.getCreation() != null) {
            this.getCreation().setConfiguration(configuration);
        }
    }
}
