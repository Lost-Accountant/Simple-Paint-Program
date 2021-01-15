package paint.Line;


import paint.Configuration;
import paint.Point;

import java.util.ArrayList;

public class Line implements LineComponent {

    private Point startPoint;
    private Point endPoint;
    private Configuration configuration;

    public Line(Point startPoint, Point endPoint, Configuration configuration){
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.configuration = configuration;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public Point getStartPoint(){
        return startPoint;
    }

    public void setStartPoint(Point point){
        this.startPoint = point;
    }

    public void setEndPoint(Point point){
        this.endPoint = point;
    }

    public ArrayList<Point> getPoints() {
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(this.getStartPoint());
        points.add(this.getEndPoint());
        return points;
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
        return "Line{" +
                "points=["+ startPoint.toStringSimplified() + ", " +
                endPoint.toStringSimplified() + ", " +
                "], configuration=" + configuration.toString() +
                '}';
    }
}
