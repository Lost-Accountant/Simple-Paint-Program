package paint;


public class Point {
    private int x, y;
    private Configuration configuration;

    public Point(int x, int y, Configuration config){
        this.x=x; this.y=y;
        this.configuration = config;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setConfiguration(Configuration config){
        this.configuration = config;
    }

    public Configuration getConfiguration(){
        return configuration;
    }

    public int getDistance(Point p1){
        return (int) Math.hypot(Math.abs(this.getX() - p1.getX()), Math.abs(this.getY() - p1.getY()));
    }

    @Override
    public String toString() {
        return "Point{" +
                "(" + x + ", " +
                 + y + ")" +
                ", configuration=" + configuration.toString() +
                '}';
    }

    public String toStringSimplified(){
        return "(" + x + ", " + y + ")";
    }
}