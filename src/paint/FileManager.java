package paint;



import paint.Command.Command;

import javax.swing.*;
import java.io.*;

public class FileManager {

    private JFileChooser fileChooser;
    private View view;
    private FileDecoder decoder;

    public FileManager(View view){
        this.fileChooser = new JFileChooser();
        this.view = view;
        this.decoder = new FileDecoder();

    }

    public void save(){
        fileChooser.setDialogTitle("Save your paint somewhere");
        // show directories only
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = fileChooser.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            createSaveFile(file.getAbsolutePath());
            System.out.println("File saves as: " + file.getAbsolutePath() + ".txt");
            // can maybe write file here.
        } else {
            System.out.println("File save failed");
        }
    }

    public void load(){
        fileChooser.setDialogTitle("Load your saved paint record");
        // show all files
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        int returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            readSaveFile(file);
        }
    }

    public File createSaveFile(String filePath){
        // create save file
        File saveFile = new File(filePath + ".txt");
        this.saveCommands(saveFile);

        return saveFile;
    }

    private void saveCommands(File file){
        try {
            PrintStream commandWriter = new PrintStream(file);

            // write each command's attributes
            for (Command command : view.getModel().getCommands()) {
                    commandWriter.println(command.toString());
            }
            commandWriter.close();

        } catch (IOException e){
            System.out.println("Error occurred");
            e.printStackTrace();
        }
    }

    private void readSaveFile(File file){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null){
                System.out.println(line);

                // construct and execute the command
                Command constructedCommand = decoder.recognizeCommand(line, view.getModel());
                view.getModel().invokeCommand(constructedCommand);

                // read next line
                line = reader.readLine();
            }

        } catch (IOException e){
            System.out.println("Error occurred");
            e.printStackTrace();
        }
    }
}
