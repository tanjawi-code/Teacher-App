import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFile {

    private final String filePath = "C:\\Users\\asus\\Desktop\\studentsFile.csv";

    SaveFile(){}

    void saveFile(StudentsManager manager){
        if(manager.isEmpty()){
            JOptionPane.showMessageDialog(null,"The class is empty","Empty class",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            try(FileWriter fileWriter = new FileWriter(filePath)){
                for(int x = 0 ; x<manager.studentsSize(); x++){
                    fileWriter.write(manager.saveFile(x));
                }
                JOptionPane.showMessageDialog(null,
                        "The file has been written in desktop, name studentsFile.cvs","File",
                        JOptionPane.INFORMATION_MESSAGE);
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

    String getFilePath(){
        return filePath;
    }
}
