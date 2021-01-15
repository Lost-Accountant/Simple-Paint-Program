package paint;


import paint.Command.AddLineCommand;
import paint.Command.AddPointCommand;
import paint.Command.AddShapeCommand;
import paint.Command.Command;
import paint.Line.Line;
import paint.Line.LineComponent;
import paint.Line.PolyLine;
import paint.Line.Squiggle;
import paint.Shape.Circle;
import paint.Shape.Rectangle;
import paint.Shape.Shape;
import paint.Shape.Square;


import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileDecoder {

    private Command constructedCommand;


    public FileDecoder(){
        this.constructedCommand = null;
    }

    public Command recognizeCommand(String line, PaintModel model){
        Pattern commandPattern = Pattern.compile("[A-Z][a-z]+,.*}");

        // match entire string
        Matcher commandMatcher = commandPattern.matcher(line);
        if(commandMatcher.matches()){
            // step 1: find out command type
            String commandType = line.substring(0, line.indexOf(","));
            //System.out.println(commandType);

            // step 2: find out configuration
            Configuration storedConfig = recognizeConfiguration(line);
            //System.out.println(storedConfig);

            // step 3: find out about the object
            Object creation = recognizeObject(line, commandType);

            // step 4: construct and deliver the command
            constructedCommand = constructCommand(commandType, creation, model);
            return constructedCommand;
        }
        return null;
    }

    public Configuration recognizeConfiguration(String line){
        Pattern configPattern = Pattern.compile("configuration=\\(java\\.awt\\.Color\\[" +
                "r=(\\d{1,3})\\,g=(\\d{1,3}),b=(\\d{1,3})]\\,\\s(10|[1-9])\\, (true|false)\\)");
        // Find section
        Matcher configMatcher = configPattern.matcher(line);
        if(configMatcher.find()){
            //System.out.println(configMatcher.group(0));

            Color storedColor = new Color(Integer.parseInt(configMatcher.group(1)),
                    Integer.parseInt(configMatcher.group(2)),
                    Integer.parseInt(configMatcher.group(3)));
            int storedLineThickness = Integer.parseInt(configMatcher.group(4));
            boolean storedFill = Boolean.parseBoolean(configMatcher.group(5));

            Configuration storedConfig = new Configuration(storedColor, storedLineThickness, storedFill);

            return storedConfig;
        }
        return null;
    }


    public Object recognizeObject(String line, String commandType){
        // judge based on rough type
        if (commandType.equals("Shape")){
            return recognizeShape(line);
        } else if (commandType.equals("Line")){
            return recognizeLine(line);
        } else if (commandType.equals("Point")){
            return recognizePoint(line);
        } else {
            return null;
        }
    }

    public LineComponent recognizeLine(String line){

        // judge specific line
        Matcher lineMatcher = Pattern.compile(",\\s([a-zA-z]*)\\{").matcher(line);
        if (lineMatcher.find()){
            ArrayList<Point> pointsSaved = recognizePoints(line);

            String lineType = lineMatcher.group(1);
            if (lineType.equals("Line")){
                Line storedLine = new Line(pointsSaved.get(0), pointsSaved.get(1), recognizeConfiguration(line));
                return storedLine;
            } else if (lineType.equals("Squiggle")){
                Squiggle storedLine = new Squiggle(pointsSaved.get(0), recognizeConfiguration(line));

                int i = 1;
                while(i < pointsSaved.size()){
                    storedLine.addPoint(pointsSaved.get(i));
                    i += 1;
                }
                return storedLine;

            } else if (lineType.equals("PolyLine")){
                PolyLine storedLine = new PolyLine(pointsSaved.get(0), recognizeConfiguration(line));

                int i = 1;
                while(i < pointsSaved.size()){
                    storedLine.addPoint(pointsSaved.get(i));
                    i += 1;
                }

                return storedLine;
            } else {
                return null;
            }
        }
        return null;
    }

    public Shape recognizeShape(String line){
        // judge specific shape
        Matcher shapeMatcher = Pattern.compile(",\\s([A-z][a-z]+)\\{").matcher(line);
        if(shapeMatcher.find()){
            String shapeType = shapeMatcher.group(1);
            // collect center
            Point centre = recognizePoint(line);

            // collect width
            int width = recognizeWidth(line);
            // collect height
            int height = recognizeHeight(line);
            // collect configuration
            Configuration configStored = recognizeConfiguration(line);

            // separate situations based on shape type
            if (shapeType.equals("Circle")){
                return new Circle(centre, width/2, configStored);
            } else if (shapeType.equals("Rectangle")){
                return new Rectangle(centre, height, width, configStored);
            } else if (shapeType.equals("Square")){
                return new Square(centre, width, configStored);
            }
        }

        return null;
    }

    /**
     * Helper function on finding height for a shape
     * @param line
     * @return
     */
    private int recognizeHeight(String line){
        Matcher heightMatcher = Pattern.compile("height\\=(\\d+)\\,").matcher(line);
        if (heightMatcher.find()){
            return Integer.parseInt(heightMatcher.group(1));
        } else {
            return -1;
        }
    }

    /**
     * Helper function on finding width for a shape
     * @param line
     * @return
     */
    private int recognizeWidth(String line){
        Matcher widthMatcher = Pattern.compile("width\\=(\\d+)\\,").matcher(line);
        if (widthMatcher.find()){
            return Integer.parseInt(widthMatcher.group(1));
        } else {
            return -1;
        }
    }


    /**
     * Used by recognizeShape.
     * Extract the point when the line was simply storing for a point.
     * @param line
     * @return
     */
    private Point recognizePoint(String line){
        return recognizePoints(line).get(0);
    }

    /**
     * Collect all points stored in a line.
     * @param line
     * @return
     */
    private ArrayList<Point> recognizePoints(String line){
        Pattern simplePointPattern = Pattern.compile("\\(\\d+\\,\\s\\d+\\)\\,");
        ArrayList<Point> collectedPoints = new ArrayList<Point>();
        // find section
        Matcher simplePointMatcher = simplePointPattern.matcher(line);
        // a loop to collect all points
        while(simplePointMatcher.find()){

            // remove brackets and comma
            String point = simplePointMatcher.group().replaceAll("\\p{P}","");
            // extract coordinates
            String[] coordinates = point.split("\\s+");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            // build point
            Point eachPoint = new Point(x, y, recognizeConfiguration(line));

            // add each point to collection
            collectedPoints.add(eachPoint);
        }
        return collectedPoints;
    }

    private Command constructCommand(String commandType, Object creation, PaintModel model){
        if (commandType.equals("Point") && creation instanceof Point){
            constructedCommand = new AddPointCommand(model, (Point) creation);
        } else if (commandType.equals("Line") && creation instanceof LineComponent){
            constructedCommand = new AddLineCommand(model, (LineComponent) creation);
        } else if (commandType.equals("Shape") && creation instanceof Shape){
            constructedCommand = new AddShapeCommand(model, (Shape) creation);
        }
        return constructedCommand;
    }
}
