package paint.Line;


import paint.Configuration;
import paint.Point;

import java.util.ArrayList;

public class Squiggle implements LineComponent{

    private ArrayList<Point> points;
    private Configuration configuration;

    public Squiggle(Point startPoint, Configuration configuration){
        this.points = new ArrayList<Point>();
        this.points.add(startPoint);
        this.configuration = configuration;
    }

    public Point getStartPoint(){
        return points.get(0);
    }

    public Point getEndPoint(){
        return points.get(points.size() - 1);
    }

    public void setStartPoint(Point startPoint){
        points.set(0, startPoint);
    }

    public void setEndPoint(Point endPoint){
        points.set(points.size() - 1, endPoint);
    }

    public void addPoint(Point newPoint){
        points.add(newPoint);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void removeLastPoint(){
        points.remove(points.size() - 1);
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
        String pointsList = "[";
        for (Point point: points){
            pointsList += point.toStringSimplified() + ", ";
        }
        pointsList += "]";

        return "Squiggle{" +
                "points=" + pointsList +
                ", configuration=" + configuration +
                '}';
    }
}
