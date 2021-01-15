package paint;

import java.awt.*;

public class Configuration {
    private Color color;
    private int lineThickness;
    private boolean isFilled;

    public Configuration(Color color, int lineThickness, boolean filled){
        this.color = color;
        this.lineThickness = lineThickness;
        this.isFilled = filled;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setIsFilled(boolean isFilled) {
        this.isFilled = isFilled;
    }


    public void setLineThickness(int lineThickness) {
        this.lineThickness = lineThickness;
    }

    public Color getColor() {
        return color;
    }


    public boolean isFilled() {
        return isFilled;
    }

    public int getLineThickness() {
        return lineThickness;
    }

    public String toString(){
        return ("(" + color.toString() + ", "
                + String.valueOf(lineThickness) + ", "
                + Boolean.toString(isFilled) + ")");
    }
}
