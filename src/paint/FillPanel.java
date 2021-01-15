package paint;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FillPanel extends JPanel implements ActionListener {

    private View view;
    private JButton fillButton;
    private boolean isFilled;

    public FillPanel(View view){
        this.view = view;

        // create fill button
        // default is not filled
        this.isFilled = false;
        this.fillButton = new JButton("Not Filled");
        this.add(fillButton);
        fillButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        // when clicked, switch filled status
        isFilled = !isFilled;
        this.switchButton(isFilled);
        this.view.getPaintPanel().setIsFilled(isFilled);

    }

    public void switchButton(boolean status){
        if (status){
            fillButton.setText("Filled");
        } else {
            fillButton.setText("Not Filled");
        }
    }
}
