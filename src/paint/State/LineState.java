package paint.State;


import paint.Configuration;
import paint.Line.Line;
import paint.Point;

import java.awt.event.MouseEvent;

public class LineState implements LineComponentState{
    private Line lineCreated;
    private boolean completed;
    private Configuration configuration;

    public LineState(Configuration configuration){
        this.lineCreated = null;
        this.completed = false;
        this.configuration = configuration;
    }

    public void mouseMoved(MouseEvent mouseEvent){
    }

    /**
     * Keep updating the end point of the line created.
     *
     * @param mouseEvent
     */
    public void mouseDragged(MouseEvent mouseEvent){
        if(this.lineCreated != null){
            lineCreated.setEndPoint(new Point(mouseEvent.getX(), mouseEvent.getY(), null));
        }
    };

    public void mouseClicked(MouseEvent mouseEvent){
    }

    /**
     * When mouse is pressed, create the start point and endpoint
     * of the line, and both points are the same point, at the current
     * position of the mouse.
     *
     * @param mouseEvent
     */
    public void mousePressed(MouseEvent mouseEvent){
        if(lineCreated == null) {
            Point startPoint = new Point(mouseEvent.getX(), mouseEvent.getY(), null);
            Point endPoint = new Point(mouseEvent.getX(), mouseEvent.getY(), null);

            lineCreated = new Line(startPoint, endPoint, configuration);
        }
    };

    /**
     * When mouse is released, change the line end point one last time.
     *
     * @param mouseEvent
     */
    public void mouseReleased(MouseEvent mouseEvent){
        if(this.lineCreated != null){
            lineCreated.setEndPoint(new Point(mouseEvent.getX(), mouseEvent.getY(), null));
            this.completed = true;
        }
    };

    public void mouseEntered(MouseEvent mouseEvent){
    }

    public void mouseExited(MouseEvent mouseEvent){
    }

    public Line getLineComponentCreated(){
        return lineCreated;
    }

    public void reset(){
        this.lineCreated = null;
        this.completed = false;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Line getCreation(){
        return getLineComponentCreated();
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
