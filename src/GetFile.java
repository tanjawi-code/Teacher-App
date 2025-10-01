import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;

public class GetFile extends Component implements ActionListener {

    private final StudentsManager manager;
    private final String fileName;
    private final DefaultTableModel model;

    JFrame frame = new JFrame("Get a file from file explorer");
    JTextArea textArea = new JTextArea();
    JButton selectButton = new JButton("Select file");
    JButton buttonBack = new JButton("Back");
    JPanel northPanel = new JPanel(new BorderLayout());

    GetFile(StudentsManager manager, String filePath, DefaultTableModel model){
        frame.setSize(500,500);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        this.manager = manager;
        this.fileName = filePath;
        this.model = model;

        JPanel buttonsPanel = new JPanel(new FlowLayout());

        selectButton.setFocusable(false); // Starting of selecting button file.
        selectButton.setBorder(BorderFactory.createEtchedBorder());
        selectButton.setBackground(Color.CYAN);
        selectButton.addActionListener(this);
        buttonBack.setFocusable(false); // Starting of backing button.
        buttonBack.setBorder(BorderFactory.createEtchedBorder());
        buttonBack.setBackground(Color.GRAY);
        buttonBack.addActionListener(this);
        buttonsPanel.add(selectButton);
        buttonsPanel.add(buttonBack);
        northPanel.add(buttonsPanel);

        frame.add(northPanel,BorderLayout.NORTH);
        frame.add(textArea);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == selectButton){
            selectFileButton();
        }
        else if(e.getSource() == buttonBack){
            frame.dispose();
        }
    }

    // This is for selecting file button.
    private void selectFileButton(){
        JFileChooser fileChooser = new JFileChooser();
        int choice = fileChooser.showOpenDialog(this);
        File file = fileChooser.getSelectedFile();
        String filePath = file.getPath();
        String[] confirm = {"Yes, Get the file","No, make new data (clear file)"};

        if(choice == JFileChooser.CANCEL_OPTION){
            selectButton.setEnabled(true);
        }
        else if (choice == JFileChooser.APPROVE_OPTION){
            if(fileName.equals(filePath)){
                if(!(file.length() == 0)){
                    int choose = JOptionPane.showOptionDialog(null,
                            "Do you want to use the previous data of this file or making new one",
                            "Using file",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,
                            confirm,0);
                    if(choose == 0){
                        getFileData(filePath);
                        selectButton.setEnabled(false);
                    }
                    else {
                        clearFile(filePath);
                        selectButton.setEnabled(false);
                        JOptionPane.showMessageDialog(null,
                                "The File cleared. now The file is empty","The file is cleared",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"the file is empty","Empty file",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(null,"The file name must be studentsFile.csv",
                        "File name is wrong",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // This is for clearing the file (make it empty if the user wants new data).
    private void clearFile(String filePath){
        File file = new File(filePath);

        try (FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write("");
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Something went wrong","Something wrong",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // This is for getting the data from the file.
    private void getFileData(String filePath){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String paragraph;
            StringBuilder text = new StringBuilder();
            while ((paragraph = bufferedReader.readLine()) != null) {
                text.append(paragraph).append("\n");
                String[] data = paragraph.split(",");
                double[] grades = new double[3];
                String firstName = data[0];
                String secondName = data[1];
                int age = Integer.parseInt(data[2]);
                Gender gender = Gender.valueOf(data[3]);
                int ID = Integer.parseInt(data[4]);
                double grade1 = Double.parseDouble(data[5]);
                double grade2 = Double.parseDouble(data[6]);
                double grade3 = Double.parseDouble(data[7]);
                double point = Double.parseDouble(decimalFormat.format(Double.parseDouble(String.valueOf(data[8]))));
                String address = data[9];
                int classNumber = Integer.parseInt(data[10]);
                grades[0] = grade1;
                grades[1] = grade2;
                grades[2] = grade3;
                Student student = new Student(firstName,secondName,age,gender,ID,grades,point,address,classNumber);
                model.addRow(new Object[] {firstName,secondName,age,gender,ID,grades[0],grades[1],grades[2],point,
                address,classNumber});
                manager.saveStudent(student);
            }
            textArea.setText(text.toString());
        }
        catch (FileNotFoundException h){
            JOptionPane.showMessageDialog(null,"File location is not found.",
                    "Unknown Location",JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException h){
            JOptionPane.showMessageDialog(null,"Something went wrong","Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
