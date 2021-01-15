package paint.Command;


import paint.Line.LineComponent;
import paint.PaintModel;

public class AddLineCommand implements Command{

    private PaintModel model;
    private LineComponent line;
    private boolean executed;

    public AddLineCommand(PaintModel model, LineComponent line){
        this.model = model;
        this.line = line;

    }

    public void execute(){
        model.addLine(line);
        this.executed = true;
    }

    public void unexecute(){
        model.removeLine(line);
        this.executed = false;
    }

    public boolean isReversable(){
        return true;
    }

    @Override
    public boolean isExecuted() {
        return executed;
    }

    public LineComponent getLine(){
        return line;
    }

    public String toString(){
        return "Line, " + line.toString();
    }
}
