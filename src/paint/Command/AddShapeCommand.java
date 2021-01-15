package paint.Command;


import paint.PaintModel;
import paint.Shape.Shape;

public class AddShapeCommand implements Command{

    private PaintModel model; // invoker
    private Shape shape; // object to be passed
    private boolean executed;

    public AddShapeCommand(PaintModel model, Shape shape){
        this.model = model;
        this.shape = shape;
    }

    public void execute(){
        model.addShape(shape);
        this.executed = true;
    }

    public void unexecute(){
        model.removeShape(shape);
        this.executed = false;
    }

    public boolean isReversable(){
        return true;
    }

    @Override
    public boolean isExecuted() {
        return executed;
    }

    public Shape getShape(){
        return shape;
    }

    public String toString(){
        return "Shape, " + shape.toString();
    }
}
