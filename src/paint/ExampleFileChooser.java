package paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExampleFileChooser extends JFrame implements ActionListener{
    private static String OPEN_COMMAND="open";
    private static String EXIT_APP_COMMAND="exit";
    public ExampleFileChooser(){
        super("Menu Bar Example App");           //sets the title for the JFrame
        this.createInterface();
    }
    public void createInterface(){
        setLayout(new BorderLayout());           //main Layout of the JFrame is BorderLayout
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = ((int) tk.getScreenSize().getWidth());
        int ySize = ((int) tk.getScreenSize().getHeight());
        setSize(xSize,ySize);                //setting the JFrame size according to the screen width and height
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createMenuBar(),BorderLayout.NORTH); //creating a JMenuBar and adding it on top of the frame
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }
    public JMenuBar createMenuBar(){
        JMenuBar menuBar=new JMenuBar();
        JMenu menuFile=new JMenu("File");          //File,Edit,View and Help are the main menus in the JMenuBar
        JMenu menuEdit=new JMenu("Edit");
        JMenu menuView=new JMenu("View");
        JMenu menuHelp=new JMenu("Help");
        JMenuItem subMenuItemOpen=new JMenuItem("Open");  //Open and Exit are the sub menu items under 'File' menu item
        subMenuItemOpen.setActionCommand(OPEN_COMMAND);
        subMenuItemOpen.addActionListener(this);
        JMenuItem subMenuItemExit=new JMenuItem("Exit");
        subMenuItemExit.setActionCommand(EXIT_APP_COMMAND);
        subMenuItemExit.addActionListener(this);
        menuFile.add(subMenuItemOpen);
        menuFile.add(subMenuItemExit);
        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuView);
        menuBar.add(menuHelp);
        return menuBar;
    }
    public static void main(String[] args){
        ExampleFileChooser main=new ExampleFileChooser();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (OPEN_COMMAND.equals(command)) {
            browseFiles();
        }else if (EXIT_APP_COMMAND.equals(command)) {
            exitProgram();
        }
    }
    //opens a JFileChooser to browse XML files to parse to the application
    public void browseFiles(){
        final JFileChooser fileChooser = new JFileChooser();
        //allows user to select more than one file
        fileChooser.setMultiSelectionEnabled(true);
        int returnVal = fileChooser.showOpenDialog(null);
        String fileExtension;
        //if the user confirms file selection display a message
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "User made the selection");
        }
        else{
            JOptionPane.showMessageDialog(null, "User canceled the selection");
        }
    }
    public void exitProgram(){
        System.exit(0);
    }
}