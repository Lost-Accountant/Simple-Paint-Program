package paint.Line;


import paint.Configuration;
import paint.Point;

import java.util.ArrayList;

public interface LineComponent {
    public Point getStartPoint();

    public Point getEndPoint();

    public void setStartPoint(Point point);

    public void setEndPoint(Point point);

    public ArrayList<Point> getPoints();

    public void setConfiguration(Configuration configuration);

    public Configuration getConfiguration();

    public String toString();
}
