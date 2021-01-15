package paint.State;


import paint.Configuration;
import paint.Line.PolyLine;
import paint.Point;

import java.awt.event.MouseEvent;

public class PolyLineState implements LineComponentState{
    private PolyLine polyLineCreated;
    private boolean completed;
    private Configuration configuration;

    public PolyLineState(Configuration configuration){
        this.polyLineCreated = null;
        this.completed = false;
        this.configuration = configuration;
    }

    /**
     * If there is no polyline that has been created,
     * initialize the creation of the polyline,
     * with only one point created, and no line
     * visible.
     *
     * Else, extend the existing polyline.
     *
     * @param event
     */
    public void mouseClicked(MouseEvent event){
        if (polyLineCreated == null){
            polyLineCreated = new PolyLine(new Point(event.getX(), event.getY(), null), configuration);
        } else {
            polyLineCreated.addPoint(new Point(event.getX(), event.getY(), null));
        }
    }

    /**
     * This doesn't do anything on the poly line created. But
     * it indicates the end of drawing by changing "completed"
     * to true.
     *
     * @param event
     */
    public void mouseExited(MouseEvent event){
        if (polyLineCreated != null) {
            this.completed = true;
            System.out.println("Polyline is completed");
        }
    }

    public void mouseMoved(MouseEvent event){
    }

    public void mouseDragged(MouseEvent event){
    }

    public void mousePressed(MouseEvent event){
    }

    public void mouseReleased(MouseEvent event){
    }

    public void mouseEntered(MouseEvent event){
    }

    public PolyLine getLineComponentCreated(){
        return this.polyLineCreated;
    }

    public void reset(){
        this.polyLineCreated = null;
        this.completed = false;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    public PolyLine getCreation(){
        return getLineComponentCreated();
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        if (this.getCreation() != null){
            this.getCreation().setConfiguration(configuration);
        }
    }
}
