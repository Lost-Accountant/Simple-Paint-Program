package paint.State;


import paint.Configuration;
import paint.Point;

import java.awt.event.MouseEvent;

public class PointState implements State{
    private Point pointCreated;
    private boolean completed;
    private Configuration configuration;

    public PointState(Configuration configuration){
        this.pointCreated = null;
        this.completed = false;
        this.configuration = configuration;
    }

    /**
     * Create the point where mouse clicked
     * @param event
     */
    public void mouseClicked(MouseEvent event){}
    public void mouseMoved(MouseEvent mouseEvent){
    }
    public void mouseDragged(MouseEvent mouseEvent){
    }

    public void mousePressed(MouseEvent event){
        if(pointCreated == null) {
            pointCreated = new Point(event.getX(), event.getY(), configuration);
        }
    }
    public void mouseReleased(MouseEvent event){
        this.completed = true;
    }

    public void mouseEntered(MouseEvent mouseEvent){
    }
    public void mouseExited(MouseEvent mouseEvent){
    }

    public Point getPointCreated(){
        return this.pointCreated;
    }

    public void reset(){
        this.pointCreated = null;
        this.completed = false;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Point getCreation(){
        return getPointCreated();
    }

    /**
     * Update both current state and the current creation.
     * @param configuration
     */
    public void setConfiguration(Configuration configuration){
        this.configuration = configuration;
        if( this.getCreation() != null) {
            this.getCreation().setConfiguration(configuration);
        }
    }

    public Configuration getConfiguration(){
        return configuration;
    }
}
