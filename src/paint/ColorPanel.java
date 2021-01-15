package paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorPanel extends JPanel implements ActionListener {

    private View view;

    public ColorPanel(View view) {
        this.view = view;

        this.createColorButtons();
    }

    public void createColorButtons() {
        String[] colorLabels = {"Red", "Green", "Blue", "Black", "White", "Yellow", "Pink", "Orange"};
        this.setLayout(new GridLayout(colorLabels.length, 1));
        for (String label : colorLabels) {
            JButton button = new JButton(label);
            button.setBackground(getColor(label));
            button.setForeground(Color.WHITE);
            this.add(button, BorderLayout.SOUTH);
            button.addActionListener(this);
        }
    }

    public Color getColor(String color) {
        Color returnColor = null;
        switch (color) {
            case "Red":
                returnColor = Color.RED;
                break;
            case "Green":
                returnColor = Color.GREEN;
                break;
            case "Blue":
                returnColor = Color.BLUE;
                break;
            case "Black":
                returnColor = Color.BLACK;
                break;
            case "White":
                returnColor = Color.WHITE;
                break;
            case "Yellow":
                returnColor = Color.YELLOW;
                break;
            case "Pink":
                returnColor = Color.PINK;
                break;
            case "Orange":
                returnColor = Color.ORANGE;
                break;
        }
        return returnColor;
    }

    /**
     * Controller aspect
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());

        Color newColor = getColor(e.getActionCommand());

        if (newColor != null) {
            this.view.getPaintPanel().setColor(newColor);
        }

    }
}