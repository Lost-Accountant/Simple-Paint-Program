package paint;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class LineThicknessPanel extends JPanel implements ChangeListener {

    private View view;

    // line thickness
    static final int LINE_MIN = 1;
    static final int LINE_MAX = 10;

    // title label
    private JLabel lineThichknessTitle;
    private JSlider lineThickness;

    public LineThicknessPanel(View view) {
        this.view = view;

        this.setLayout(new BorderLayout());

        // Slider for Line Thickness
        this.createLineThicknessSlider();
    }

    public void createLineThicknessSlider(){
        lineThickness = new JSlider(JSlider.HORIZONTAL,
                LINE_MIN, LINE_MAX, LINE_MIN);
        lineThickness.addChangeListener(this);
        // provide labels
        lineThickness.setMajorTickSpacing(1);
        lineThickness.setPaintTicks(true);
        lineThickness.setPaintLabels(true);
        // provide title label
        lineThichknessTitle = new JLabel("Slide for line thickness adjustment",
                JLabel.CENTER);
        // set font
        //Font font = new Font("Serif", Font.ITALIC, 15);
        //lineThichknessTitle.setFont(font);
        this.add(lineThickness, BorderLayout.WEST);
        this.add(lineThichknessTitle,BorderLayout.EAST);
    }

    @Override
    public void stateChanged(ChangeEvent e){
        lineThichknessTitle.setText("Line Thickness: " + lineThickness.getValue());
        this.view.getPaintPanel().setLineThickness(lineThickness.getValue());
    }
}
