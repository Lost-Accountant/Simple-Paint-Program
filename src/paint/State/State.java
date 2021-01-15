package paint.State;


import paint.Configuration;

import java.awt.event.MouseEvent;

public interface State {
    public void mouseMoved(MouseEvent mouseEvent);
    public void mouseDragged(MouseEvent mouseEvent);
    public void mouseClicked(MouseEvent mouseEvent);
    public void mousePressed(MouseEvent mouseEvent);
    public void mouseReleased(MouseEvent mouseEvent);
    public void mouseEntered(MouseEvent mouseEvent);
    public void mouseExited(MouseEvent mouseEvent);
    public void reset();
    public boolean isCompleted();
    public Object getCreation();
    public Configuration getConfiguration();
    public void setConfiguration(Configuration configuration);
}
