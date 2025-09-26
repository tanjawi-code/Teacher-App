import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class MainWindow extends JFrame implements ActionListener {

    private final StudentsManager manager;

    // This is for the buttons in the lower part of the screen.
    String[] leftUnderTitles = {"Statistics","Passed","Failed","Top 3","Account","Sittings"};
    String[] rightUnderTitles = {"Delete","Update","Student Statistics","Save as a file","Get a file","Searching ways"};
    JButton[] leftUnderButtons = new JButton[leftUnderTitles.length];
    JButton[] rightUnderButtons = new JButton[rightUnderTitles.length];

    // This is for inputs in the higher part of the screen in left side.
    String[] leftTopTitles = {
            "First name : ","Second name : ","Age : ","Grade 1 : ",
            "Grade 2 : ","Grade 3 : ","Address : ","Gender : "};
    JTextField[] textInputs = new JTextField[leftTopTitles.length];
    JLabel[] labels = new JLabel[leftTopTitles.length];
    JButton addStudent = new JButton("Add student");
    JButton clearButton = new JButton("Clear");
    JComboBox<Gender> boxGender = new JComboBox<>(Gender.values());

    // These are the main panels that are in the frame.
    JPanel northPanel = new JPanel(new BorderLayout());
    JPanel westPanel = new JPanel(new BorderLayout());
    JPanel southPanel = new JPanel(new BorderLayout());

    // This is for the table.
    String[] tableTitles = {
            "First name","Second name","Age","Gender","ID","Grade 1","Grade 2","Grade 3","Point","Address","Class number"};
    JButton refresh = new JButton("Refresh");
    JButton search = new JButton("Search");
    JButton buttonConfirmUpdate = new JButton("Confirm update");
    JTextField searchName = new JTextField(50);

    DefaultTableModel model = new DefaultTableModel(tableTitles,0);
    JTable table = new JTable(model);
    JScrollPane pane = new JScrollPane(table);
    TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);

    Gender gender;
    boolean genderIsSelected = false;
    int selectedRow = table.getSelectedRow();
    int studentIndex;
    MainWindow(StudentsManager manager){
        this.setTitle("Students management");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLayout(new BorderLayout());
        this.manager = manager;

        // This is for the higher part of the screen in left side.
        JPanel textPanel = new JPanel(new GridLayout(9,2,0,5));
        for(int x = 0; x< leftTopTitles.length; x++){
            labels[x] = new JLabel(leftTopTitles[x]);
            labels[x].setFont(new Font("MV Boli",Font.BOLD,20));
        }
        for(int x =0 ; x<textInputs.length; x++){
            textInputs[x] = new JTextField(10);
            textInputs[x].setFont(new Font("Arial",Font.PLAIN,20));
            textInputs[x].setCaretColor(Color.BLACK);
            textPanel.add(labels[x]);
            if(labels[x].getText().equals("Gender : ")){
                textPanel.add(boxGender);
            }
            else {
                textPanel.add(textInputs[x]);
            }
        }
        boxGender.addActionListener(e -> {
            Object object = boxGender.getSelectedItem();
            if(object instanceof Gender){
                gender = (Gender) object;
                genderIsSelected = true;
            }
        });
        addStudent.setFocusable(false); // Button of adding a student.
        addStudent.setBorder(BorderFactory.createEtchedBorder());
        addStudent.setBackground(Color.GREEN);
        addStudent.addActionListener(this);
        clearButton.setFocusable(false); // Button of cleaning the fields.
        clearButton.setBorder(BorderFactory.createEtchedBorder());
        clearButton.setBackground(Color.GRAY);
        clearButton.addActionListener(this);
        textPanel.add(addStudent);
        textPanel.add(clearButton);
        westPanel.add(textPanel,BorderLayout.NORTH);

        // This is for the lower part of the screen in left side.
        JPanel leftButtonsPanel = new JPanel(new GridLayout(3,2,30,30));
        for(int x = 0; x<leftUnderButtons.length; x++){
            leftUnderButtons[x] = new JButton(leftUnderTitles[x]);
            leftUnderButtons[x].setBackground(Color.LIGHT_GRAY);
            leftUnderButtons[x].setFocusable(false);
            leftUnderButtons[x].setBorder(BorderFactory.createEtchedBorder());
            leftUnderButtons[x].addActionListener(this);
            leftUnderButtons[x].setPreferredSize(new Dimension(160,80));
            leftUnderButtons[x].setFont(new Font("MV Boli",Font.BOLD,25));
            leftButtonsPanel.add(leftUnderButtons[x]);
        }
        southPanel.add(leftButtonsPanel,BorderLayout.WEST);

        // This is for the higher part of the screen in right side.
        JPanel tableChoices = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        refresh.setFont(new Font("MV Boli",Font.ITALIC,20)); // Starting of refresh button.
        refresh.setFocusable(false);
        refresh.setBorder(BorderFactory.createEtchedBorder());
        refresh.addActionListener(this);
        refresh.setBackground(Color.GRAY);
        refresh.setPreferredSize(new Dimension(150,30));
        search.setFont(new Font("MV Boli",Font.ITALIC,20)); // Starting of searching button.
        search.setFocusable(false);
        search.setBorder(BorderFactory.createEtchedBorder());
        search.addActionListener(this);
        search.setBackground(new Color(0x00FF1C));
        search.setPreferredSize(new Dimension(150,30));
        buttonConfirmUpdate.setFont(new Font("MV Boli",Font.ITALIC,20)); // Starting of confirming update button.
        buttonConfirmUpdate.setFocusable(false);
        buttonConfirmUpdate.setBorder(BorderFactory.createEtchedBorder());
        buttonConfirmUpdate.addActionListener(this);
        buttonConfirmUpdate.setBackground(new Color(0x00FF1C));
        buttonConfirmUpdate.setPreferredSize(new Dimension(155,30));
        buttonConfirmUpdate.setVisible(false);
        searchName.setPreferredSize(new Dimension(100,30)); // Starting of searching field.
        searchName.setBackground(Color.LIGHT_GRAY);
        tableChoices.add(buttonConfirmUpdate);
        tableChoices.add(refresh);
        tableChoices.add(search);
        tableChoices.add(searchName);

        // This is for the table.
        table.setRowSorter(sorter);
        northPanel.add(pane,BorderLayout.CENTER);
        northPanel.add(tableChoices,BorderLayout.NORTH);
        table.setEnabled(false);

        // This is for the lower part in right side.
        JPanel rightButtonsPanel = new JPanel(new GridLayout(2,3,0,40));
        JPanel rightSouthPanel = new JPanel(new BorderLayout());
        for(int x = 0; x<rightUnderButtons.length; x++){
            rightUnderButtons[x] = new JButton(rightUnderTitles[x]);
            String title = rightUnderButtons[x].getText();
            rightUnderButtons[x].setFocusable(false);
            rightUnderButtons[x].setBorder(BorderFactory.createEtchedBorder());
            rightUnderButtons[x].addActionListener(this);
            rightUnderButtons[x].setPreferredSize(new Dimension(300,100));
            rightUnderButtons[x].setFont(new Font("MV Boli",Font.BOLD,20));
            if(Arrays.asList(rightUnderTitles).contains(title)){
                switch (title) {
                    case "Delete" -> rightUnderButtons[x].setBackground(Color.RED);
                    case "Update" -> rightUnderButtons[x].setBackground(new Color(0, 118, 192));
                    case "Student Statistics" -> rightUnderButtons[x].setBackground(new Color(244, 121, 43));
                    default -> rightUnderButtons[x].setBackground(Color.LIGHT_GRAY);
                }
            }
            rightButtonsPanel.add(rightUnderButtons[x]);
        }
        rightSouthPanel.add(rightButtonsPanel,BorderLayout.SOUTH);
        southPanel.add(rightSouthPanel,BorderLayout.EAST);

        this.add(northPanel,BorderLayout.CENTER); // Holds the north and center and south in the screen's center.
        this.add(westPanel,BorderLayout.WEST); // Holds the north and center in the west.
        this.add(southPanel,BorderLayout.SOUTH); // Holds the south in the west and east.
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String value = button.getText();

        if(Arrays.asList(leftUnderTitles).contains(value)){
            switch (value){
                case "Statistics" : new ClassStatistics(manager); break;
                case "Passed" : passedStudents(); break;
                case "Failed" : failedStudents(); break;
                case "Top 3" : topThreeStudents(); break;
                case "Account" :
                    JOptionPane.showMessageDialog(null,"It's coming soon",
                            "Teacher's account",JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Sittings" :
                    break;
                default:
            }
        }
        else if(Arrays.asList(rightUnderTitles).contains(value)){
            switch (value){
                case "Delete" : deleteStudent(); break;
                case "Update" : updateStudent(); break;
                case "Student Statistics" : new studentStatistics(manager); break;
                case "Save as a file" : break;
                case "Get a file" : break;
                case "Searching ways" : break;
                default:
            }
        }
        else if (value.equals("Add student")) {
            buttonAddStudent();
        }
        else if(value.equals("Clear")){
            buttonClearFields();
        }
        else if(value.equals("Search")){
            searchForStudent();
        }
        else if(value.equals("Refresh")){
            refreshTable();
        }
        else if(value.equals("Confirm update")){
            confirmUpdate(selectedRow);
        }
    }

    // This is for adding the student to the table.
    private void buttonAddStudent(){
        if(textInputs[0].getText().isEmpty() || textInputs[1].getText().isEmpty() ||
                textInputs[2].getText().isEmpty() || textInputs[3].getText().isEmpty() ||
                textInputs[4].getText().isEmpty() || textInputs[5].getText().isEmpty() ||
                textInputs[6].getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"The fields are empty.","Empty fields",
                    JOptionPane.ERROR_MESSAGE);
        }
        else if(!checkName(textInputs[0].getText()) || !checkName(textInputs[1].getText())){
            JOptionPane.showMessageDialog(null,
                    "The name must be only letters and one name in the filed","Wrong input",
                    JOptionPane.ERROR_MESSAGE);
        }
        else if(textInputs[1].getText().equals(manager.checkName(textInputs[1].getText()))){
            JOptionPane.showMessageDialog(null,"The family name is already in the list",
                    "Wrong input",JOptionPane.ERROR_MESSAGE);
        }
        else if(!checkAge(textInputs[2].getText())){
            JOptionPane.showMessageDialog(null,"The age must be only numbers",
                    "Wrong input",JOptionPane.ERROR_MESSAGE);
        }
        else if(!checkValidAge(Integer.parseInt(textInputs[2].getText()))){
            JOptionPane.showMessageDialog(null,
                    "The student's age can't be less than 15 or bigger than 20",
                    "Age is not available is this school",JOptionPane.ERROR_MESSAGE);
        }
        else if(!checkGrade(textInputs[3].getText()) || !checkGrade(textInputs[4].getText()) || !checkGrade(textInputs[5].getText())){
            JOptionPane.showMessageDialog(null,"The grade must be only numbers",
                    "Grades",JOptionPane.ERROR_MESSAGE);
        }
        else if(!checkValidGrades(Double.parseDouble(textInputs[3].getText())) ||
                !checkValidGrades(Double.parseDouble(textInputs[4].getText())) ||
                !checkValidGrades(Double.parseDouble(textInputs[5].getText()))){
            JOptionPane.showMessageDialog(null,"The grade must be between 0 and 20",
                    "Between 0 and 20",JOptionPane.ERROR_MESSAGE);
        }
        else if(!checkAddress(textInputs[6].getText())){
            JOptionPane.showMessageDialog(null,"The address must be only letters",
                    "Address",JOptionPane.ERROR_MESSAGE);
        }
        else if(!genderIsSelected){
            JOptionPane.showMessageDialog(null,"The gender is not selected yet",
                    "Select Gender",JOptionPane.ERROR_MESSAGE);
        }
        else{
            String firstName = textInputs[0].getText();
            String secondName = textInputs[1].getText();
            int age = Integer.parseInt(textInputs[2].getText());
            double[] grades = new double[3];
            grades[0] = Double.parseDouble(textInputs[3].getText());
            grades[1] = Double.parseDouble(textInputs[4].getText());
            grades[2] = Double.parseDouble(textInputs[5].getText());
            String address = textInputs[6].getText();
            storeDataTable(firstName,secondName,age,grades,gender,address);
        }
    }

    private void storeDataTable(String firstName,String secondName,int age,double[] grades,Gender gender, String address){
        firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase();
        secondName = secondName.substring(0,1).toUpperCase() + secondName.substring(1).toLowerCase();
        address = address.substring(0,1).toUpperCase() + address.substring(1).toLowerCase();
        String fullName = firstName+" "+secondName;

        Student student = new Student(firstName,secondName,age,grades,gender,address,fullName);
        student.calculateGrades();
        int studentID = student.GenerateStudentID();
        double point = student.getStudentPoint();
        int classNumber = 0;

        for(int x = 0; x<manager.studentsSize(); x++){
            classNumber = manager.increaseClassNumber(classNumber);
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        point = Double.parseDouble(decimalFormat.format(Double.parseDouble(String.valueOf(point))));

        model.addRow(new Object[] {firstName,secondName,age,gender,studentID,grades[0],grades[1],grades[2],point,
                address,classNumber+1});
        manager.saveStudent(student);
    }

    // This is for clearing the fields.
    private void buttonClearFields(){
        for(int x = 0 ; x< labels.length; x++){
            textInputs[x].setText("");
            if(labels[x].getText().equals("Gender : ")){
                boxGender.setSelectedIndex(0);
            }
        }
    }

    // These four methods are for deleting, searching, updating, refreshing.
    private void deleteStudent(){
        String name;
        boolean isCancel = false;
        while(true){
            name = JOptionPane.showInputDialog("What is the student's first name you want to remove?");
            if(name == null){
                isCancel = true;
                break;
            }
            else if(name.matches("[a-zA-Z]+")){
                break;
            }
            else {
                JOptionPane.showMessageDialog(null,"the name must be only letters",
                        "Wrong input",JOptionPane.ERROR_MESSAGE);
            }
        }

        if(!isCancel){
            boolean isFound = false;
            for(int x =0 ;x<manager.studentsSize(); x++){
                if(manager.getStudentFullName(x).toLowerCase().contains(name.trim().toLowerCase())){
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)"+name,0));
                    int choice = JOptionPane.showConfirmDialog(null,
                            "Do you want to remove the student "+manager.getStudentFullName(x),
                            "Removing a student",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(choice == 0){
                        model.removeRow(x);
                        manager.removeStudent(x);
                        JOptionPane.showMessageDialog(null,
                                "The student is removed successfully","Student is removed",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"The student is not removed",
                                "Student is not removed",JOptionPane.INFORMATION_MESSAGE);
                    }
                    isFound = true;
                    break;
                }
            }
            if(!isFound){
                JOptionPane.showMessageDialog(null,"The student name is not found",
                        "Student is not found",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    private void searchForStudent(){
        String name = searchName.getText().trim();
        boolean check = false;
        for(int x = 0 ; x< manager.studentsSize(); x++){
            if(name.isEmpty()){
                sorter.setRowFilter(null);
            }
            else if (manager.getFirstStudentName(x).trim().toLowerCase().equals(name.trim())){
                sorter.setRowFilter(RowFilter.regexFilter("(?i)"+name,0));
                check = true;
            }
        }

        if(!check){
            JOptionPane.showMessageDialog(null,"The student name is not found",
                    "Student is not found",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void updateStudent(){
        String name = "";
        boolean isEmpty = false;
        boolean isCancel = false;
        if(manager.isEmpty()){
            JOptionPane.showMessageDialog(null,"There are no students in the class",
                    "Empty class",JOptionPane.ERROR_MESSAGE);
        }
        else {
            while (true) {
                name = JOptionPane.showInputDialog("What is the student name you want to modify?");
                if (name == null) {
                    isCancel = true;
                    break;
                }
                else if(name.matches("[a-zA-Z]+") ){
                    isEmpty = true;
                    break;
                }
                else {
                    JOptionPane.showMessageDialog(null, "The must have only letters",
                            "Wrong input", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if(!isCancel){
            if(isEmpty){
                boolean isFound = false;
                for(int x =0 ; x<manager.studentsSize(); x++){
                    if(manager.getFirstStudentName(x).trim().toLowerCase().equals(name.trim())){
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)"+name,0));
                        JOptionPane.showMessageDialog(null,
                                "You can modify details through the table","Update student's details",
                                JOptionPane.INFORMATION_MESSAGE);
                        studentIndex = x;
                        buttonConfirmUpdate.setVisible(true);
                        isFound = true;
                    }
                }
                if(!isFound){
                    JOptionPane.showMessageDialog(null,"The student is not found",
                            "Student not found",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    private void refreshTable(){
        sorter.setRowFilter(null);
        searchName.setText("");
    }
    private void confirmUpdate(int row){
        buttonConfirmUpdate.setVisible(false);
    }

    // These three methods are for showing in the table: passed students, failed students, top 3 students.
    private void passedStudents(){
        if(manager.isEmpty()){
            JOptionPane.showMessageDialog(null,"There are no students","Empty class",
                    JOptionPane.ERROR_MESSAGE);
        }
        else {
            sorter.setRowFilter(RowFilter.numberFilter(RowFilter.ComparisonType.AFTER,9.99,8));
        }
    }
    private  void failedStudents(){
        if(manager.isEmpty()){
            JOptionPane.showMessageDialog(null,"There are no students","Empty class",
                    JOptionPane.ERROR_MESSAGE);
        }
        else{
            sorter.setRowFilter(RowFilter.numberFilter(RowFilter.ComparisonType.BEFORE,10,8));
        }
    }
    private void topThreeStudents(){
        ArrayList<Student> sortedManager = manager.showTopThreePoints();
        JOptionPane.showMessageDialog(null,"It's coming later","In the future",
                JOptionPane.ERROR_MESSAGE);
    }

    // These are methods are for checking the fields.
    private Boolean checkName(String name){
        return name.matches("[a-zA-Z]+");
    }
    private Boolean checkAge(String age){
        return age.matches("\\d+");
    }
    private Boolean checkValidAge(int age){
        return age >= 15 && age <= 20;
    }
    private Boolean checkAddress(String address){
        return address.matches("[a-zA-Z]+");
    }
    private boolean checkGrade(String grade){
        return grade.matches("\\d+(\\.\\d+)?");
    }
    private Boolean checkValidGrades(double grade){
        return grade >= 0 && grade <= 20;
    }
}
