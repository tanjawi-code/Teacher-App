import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFile {

    SaveFile(){}

    void saveFile(StudentsManager manager,SavedFilesSQLite savedFilesSQLite, int teachersID){
        if(manager.isEmpty()){
            JOptionPane.showMessageDialog(null,"The class is empty","Empty class",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showSaveDialog(null);

            if(response == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                String filePath = file.getPath();
                String fileName = file.getName();
                String fileType = "";

                int dotIndex = fileName.lastIndexOf('.');
                if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                    fileType = fileName.substring(dotIndex + 1);
                }

                savedFilesSQLite.insertFileToTable(filePath,fileName,fileType,teachersID);

                try(FileWriter fileWriter = new FileWriter(filePath)){
                    for(int x = 0 ; x<manager.studentsSize(); x++){
                        fileWriter.write(manager.saveFile(x));
                    }
                    JOptionPane.showMessageDialog(null,
                            "The file has been written","File",JOptionPane.INFORMATION_MESSAGE);
                }
                catch (FileNotFoundException e){
                    JOptionPane.showMessageDialog(null,"The location of the file is not found",
                            "Location is not found",JOptionPane.ERROR_MESSAGE);
                }
                catch (IOException e){
                    JOptionPane.showMessageDialog(null,"Something is wrong","Something wrong",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
