package paint.State;


import paint.Configuration;
import paint.Line.LineComponent;

public interface LineComponentState extends State{
    public LineComponent getLineComponentCreated();

    public void setConfiguration(Configuration configuration);
}
