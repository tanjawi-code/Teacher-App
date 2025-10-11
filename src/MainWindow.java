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
    private final TeachersManager teachersManager;
    private final StudentsSQLite studentsSQLite;
    private final TeachersSQLite teachersSQLite;

    // This is for the buttons in the lower part of the screen.
    String[] leftUnderTitles = {"Statistics","Passed","Failed","Top 3","Account","Settings"};
    String[] rightUnderTitles = {"Delete","Update","Student Statistics","Save as a file","Get a file","Searching ways"};
    JButton[] leftUnderButtons = new JButton[leftUnderTitles.length];
    JButton[] rightUnderButtons = new JButton[rightUnderTitles.length];

    // This is for inputs in the higher part of the screen in left side.
    String[] leftTopTitles = {
            "First name : ","Second name : ","Age : ","Grade 1 : ",
            "Grade 2 : ","Grade 3 : ","City : ","Gender : "};
    JTextField[] textInputs = new JTextField[leftTopTitles.length];
    JLabel[] labels = new JLabel[leftTopTitles.length];
    JButton addStudent = new JButton("Add student");
    JButton clearButton = new JButton("Clear");
    JComboBox<Gender> boxGender = new JComboBox<>(Gender.values());
    JComboBox<City> cityBox = new JComboBox<>(City.values());

    // These are the main panels that are in the frame.
    JPanel northPanel = new JPanel(new BorderLayout());
    JPanel westPanel = new JPanel(new BorderLayout());
    JPanel southPanel = new JPanel(new BorderLayout());

    // This is for the table.
    String[] tableTitles = {
            "First name","Second name","Age","Gender","ID","Grade 1","Grade 2","Grade 3","Point","City","Class number"};
    JButton refresh = new JButton("Refresh");
    JButton search = new JButton("Search");
    JButton buttonConfirmUpdate = new JButton("Confirm update");
    JTextField searchName = new JTextField(50);

    DefaultTableModel model = new DefaultTableModel(tableTitles,0){
        @Override
        public boolean isCellEditable(int row, int column){
            return column != 0 && column != 1 && column != 3 && column != 4 && column != 8 &&
                    column != 9 && column != 10;
        }
    };
    JTable table = new JTable(model);
    JScrollPane pane = new JScrollPane(table);
    TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);

    private Gender gender;
    private City studentCity;
    private boolean genderIsSelected = false;
    private boolean cityIsSelected = false;
    MainWindow(StudentsManager manager, TeachersManager teachersManager,StudentsSQLite students,TeachersSQLite teachers, Boolean userIsFound){
        this.setTitle("Students management");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLayout(new BorderLayout());
        this.manager = manager;
        this.teachersManager = teachersManager;
        this.studentsSQLite = students;
        this.teachersSQLite = teachers;

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
            else if(labels[x].getText().equals("City : ")){
                textPanel.add(cityBox);
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
        cityBox.addActionListener(e -> {
            Object object = cityBox.getSelectedItem();
            if(object instanceof City){
                studentCity = (City) object;
                cityIsSelected = true;
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

        if(userIsFound){
            getTeacherStudents();
        }

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
                case "Statistics" -> new ClassStatistics(manager,teachersManager);
                case "Passed" -> passedStudents();
                case "Failed" -> failedStudents();
                case "Top 3" -> topThreeStudents();
                case "Account" ->  new Account(teachersManager);
                case "Settings" -> JOptionPane.showMessageDialog(null,"It's coming later","Settings",JOptionPane.INFORMATION_MESSAGE);
                default -> System.out.println("\n");
            }
        }
        else if(Arrays.asList(rightUnderTitles).contains(value)){
            SaveFile saveFile = new SaveFile();
            String filePath = saveFile.getFilePath();

            switch (value){
                case "Delete" -> deleteStudent();
                case "Update" -> updateStudent();
                case "Student Statistics" -> new studentStatistics(manager);
                case "Save as a file" -> saveFile.saveFile(manager);
                case "Get a file" -> new GetFile(manager,filePath,model);
                case "Searching ways" -> new SearchingWays(sorter);
                default -> System.out.println();
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
            confirmUpdate();
        }
    }

    // This is for adding the student to the table.
    private void buttonAddStudent(){
        String fullName = textInputs[0].getText().toLowerCase()+" "+textInputs[1].getText().toLowerCase();
        if(checkFieldsAreEmpty()){
            JOptionPane.showMessageDialog(null,"The fields are empty.","Empty fields",
                    JOptionPane.ERROR_MESSAGE);
        }
        else if(!checkName(textInputs[0].getText()) || !checkName(textInputs[1].getText())){
            JOptionPane.showMessageDialog(null,
                    "The name must be only letters and one name in the filed","Wrong input",
                    JOptionPane.ERROR_MESSAGE);
        }
        else if(checkFullName(textInputs[0].getText(),textInputs[1].getText()).equals(fullName)){
            JOptionPane.showMessageDialog(null,"The student name is already in the list",
                    "Wrong input",JOptionPane.ERROR_MESSAGE);
        }
        else if(!checkAge(textInputs[2].getText())){
            JOptionPane.showMessageDialog(null,"The student age must be only numbers",
                    "Wrong input",JOptionPane.ERROR_MESSAGE);
        }
        else if(!checkValidAge(Integer.parseInt(textInputs[2].getText()))){
            JOptionPane.showMessageDialog(null,
                    "The student's age can't be less than 15 or bigger than 20 in this school",
                    "Age is not available is this school",JOptionPane.ERROR_MESSAGE);
        }
        else if(!checkGrade(textInputs[3].getText()) || !checkGrade(textInputs[4].getText()) || !checkGrade(textInputs[5].getText())){
            JOptionPane.showMessageDialog(null,"The student grade must be only numbers",
                    "Grades",JOptionPane.ERROR_MESSAGE);
        }
        else if(!checkValidGrades(Double.parseDouble(textInputs[3].getText())) ||
                !checkValidGrades(Double.parseDouble(textInputs[4].getText())) ||
                !checkValidGrades(Double.parseDouble(textInputs[5].getText()))){
            JOptionPane.showMessageDialog(null,"The student grade must be between 0 and 20",
                    "Between 0 and 20",JOptionPane.ERROR_MESSAGE);
        }
        else if(!cityIsSelected){
            JOptionPane.showMessageDialog(null,"The student city is not selected yet",
                    "Select Address",JOptionPane.ERROR_MESSAGE);
        }
        else if(!genderIsSelected){
            JOptionPane.showMessageDialog(null,"The student gender is not selected yet",
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
            storeDataTable(firstName,secondName,age,grades);
        }
    }

    private void storeDataTable(String firstName, String secondName, int age, double[] grades){
        firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase();
        secondName = secondName.substring(0,1).toUpperCase() + secondName.substring(1).toLowerCase();
        String fullName = firstName+" "+secondName;

        Student student = new Student(firstName,secondName,age,grades,gender,studentCity,fullName);
        student.calculateGrades();
        int studentID = student.GenerateStudentID();
        double point = student.getStudentPoint();
        int classNumber = 0;

        for(int x = 0; x<manager.studentsSize(); x++){
            classNumber = manager.increaseClassNumber(classNumber);
        }
        student.setStudentClassNumber(classNumber+1);
        student.setStudentTeacherID(teachersSQLite.getTeacherID());

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        point = Double.parseDouble(decimalFormat.format(Double.parseDouble(String.valueOf(point))));

        model.addRow(new Object[] {firstName,secondName,age,gender,studentID,grades[0],grades[1],grades[2],point,
                studentCity,classNumber+1});
        studentsSQLite.insertStudentsToTable(student);
        manager.saveStudent(student);
    }

    // This is for clearing the fields.
    private void buttonClearFields(){
        for(int x = 0 ; x< labels.length; x++){
            textInputs[x].setText("");
            if(labels[x].getText().equals("Gender : ")){
                boxGender.setSelectedIndex(0);
                genderIsSelected = false;
            }
            else if(labels[x].getText().equals("City : ")){
                cityBox.setSelectedIndex(0);
                cityIsSelected = false;
            }
        }
    }

    // These four methods are for deleting, searching, updating, refreshing, and confirming.
    private void deleteStudent(){
        String name ="";
        boolean isCancel = false;
        if(!manager.isEmpty()){
            while(true){
                name = JOptionPane.showInputDialog("What is the student's name you want to remove?");
                if(name == null){
                    isCancel = true;
                    break;
                }
                else if(name.matches("[a-zA-Z]+(\\s[a-zA-z]+)*")){
                    break;
                }
                else {
                    JOptionPane.showMessageDialog(null,"the name must be only letters",
                            "Wrong input",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(null,"The class is empty.","Empty class",
                    JOptionPane.INFORMATION_MESSAGE);
            isCancel = true;
        }

        boolean isFound = false;
        if(!isCancel){
            for(int x =0 ;x<manager.studentsSize(); x++){
                if(manager.getStudentFullName(x).toLowerCase().trim().contains(name.trim().toLowerCase())){
                    sorter.setRowFilter(RowFilter.regexFilter("^"+manager.getStudentID(x)+"$",4));
                    confirmDelete(x);
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
    private void confirmDelete(int index){
        String name = manager.getStudentFullName(index);
        int studentID = manager.getStudentID(index);
        int id = studentsSQLite.deleteStudent(name, studentID);
        if(id != -1){
            int choice = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to remove this student: "+name,"Removing a student",
                    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(choice == 0){
                model.removeRow(index);
                manager.removeStudent(index);
                boolean isDeleted = studentsSQLite.deleteStudentFromTable(id);
                if(isDeleted){
                    JOptionPane.showMessageDialog(null,
                            "The student is removed successfully","Student is removed",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"The student is not removed",
                        "Student is not removed",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    private void searchForStudent(){
        String name = searchName.getText().trim().toLowerCase();
        boolean check = false;
        for(int x = 0 ; x< manager.studentsSize(); x++){
            if(name.isEmpty()){
                sorter.setRowFilter(null);
            }
            else if (manager.getFirstStudentName(x).toLowerCase().equals(name) || manager.getSecondStudentName(x).toLowerCase().equals(name)){
                sorter.setRowFilter(RowFilter.regexFilter("(?i)"+name,0,1));
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
                    if(manager.getStudentFullName(x).toLowerCase().contains(name.trim().toLowerCase())){
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)"+name,0,1));
                        JOptionPane.showMessageDialog(null,
                                """
                                                                  You can modify details through the table.
                                        When you finish updating details of the student click confirm, and then refresh
                                        """,
                                "Update student's details",JOptionPane.INFORMATION_MESSAGE);
                        buttonConfirmUpdate.setVisible(true);
                        isFound = true;
                        table.setEnabled(true);
                        break;
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
        table.setModel(model);
        table.setRowSorter(sorter);
        sorter.setRowFilter(null);
        searchName.setText("");
        table.setEnabled(false);
        buttonConfirmUpdate.setVisible(false);
    }
    private void confirmUpdate(){
        int row = table.getSelectedRow();
        int rowModel = table.convertRowIndexToModel(row);
        if(row != -1){
            int ID = Integer.parseInt(table.getValueAt(row,4).toString());
            for(int x = 0 ;x<manager.studentsSize(); x++){
                if(ID == manager.getStudentID(x)){
                    Student student = manager.getStudent(rowModel);
                    student.setAge(Integer.parseInt(table.getValueAt(row,2).toString()));
                    double[] grades = new double[3];
                    grades[0] = Double.parseDouble(table.getValueAt(row,5).toString());
                    grades[1] = Double.parseDouble(table.getValueAt(row,6).toString());
                    grades[2] = Double.parseDouble(table.getValueAt(row,7).toString());
                    student.setGrades(grades);

                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    double point = student.getStudentPoint();
                    point = Double.parseDouble(decimalFormat.format(Double.parseDouble(String.valueOf(point))));

                    model.setValueAt(student.getStudentAge(),rowModel,2);
                    model.setValueAt(student.getStudentGrades(0),rowModel,5);
                    model.setValueAt(student.getStudentGrades(1),rowModel,6);
                    model.setValueAt(student.getStudentGrades(2),rowModel,7);
                    model.setValueAt(point,rowModel,8);
                    break;
                }
            }
        }
    }

    // These three methods are for showing in the table: passed students, failed students, and top 3 students.
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
        if(manager.isEmpty()){
            JOptionPane.showMessageDialog(null,"The class is empty","Empty class",
                    JOptionPane.ERROR_MESSAGE);
        }
        else{
            ArrayList<Student> sortedList = manager.showTopThreePoints();
            DefaultTableModel topThreeModel = new DefaultTableModel(tableTitles,0);
            TableRowSorter<TableModel> sortedSorter = new TableRowSorter<>(topThreeModel);
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            int count = 1;
            for (Student student : sortedList) {
                double point = student.getStudentPoint();
                point = Double.parseDouble(decimalFormat.format(Double.parseDouble(String.valueOf(point))));
                topThreeModel.addRow(new Object[]{
                        student.getFirstStudentName(), student.getSecondStudentName(), student.getStudentAge(),
                        student.getStudentGender(), student.getStudentID(), student.getStudentGrades(0),
                        student.getStudentGrades(1), student.getStudentGrades(2), point,
                        student.getStudentCity(), student.getStudentClassNumber()});
                if(count == 3){
                    break;
                }
                count++;
            }
            table.setModel(topThreeModel);
            table.setRowSorter(sortedSorter);
            sortedSorter.setRowFilter(RowFilter.numberFilter(RowFilter.ComparisonType.AFTER,9.99,8));
        }
    }

    // This is for getting students details if the user(teacher) is found(has an account).
    void getTeacherStudents(){
        for(int x = 0 ; x<manager.studentsSize() ; x++){
            Student student = manager.getStudent(x);
            model.addRow(new Object[] {
                    student.getFirstStudentName(), student.getSecondStudentName(), student.getStudentAge(),
                    student.getStudentGender(), student.getStudentID(), student.getStudentGrades(0),
                    student.getStudentGrades(1), student.getStudentGrades(2),
                    student.getStudentPoint(), student.getStudentCity(), student.getStudentClassNumber()});
        }
    }

    // These are methods are for checking the fields.
    private Boolean checkFieldsAreEmpty(){
        return textInputs[0].getText().isEmpty() || textInputs[1].getText().isEmpty() ||
                textInputs[2].getText().isEmpty() || textInputs[3].getText().isEmpty() ||
                textInputs[4].getText().isEmpty() || textInputs[5].getText().isEmpty();
        // 0 = first name || 1 = second name || 2 = age || 3 = grade 1 || 4 = grade 2 || 5 = grade 3.
    }
    private Boolean checkName(String name){
        return name.matches("[a-zA-Z]+");
    }
    private String checkFullName(String firstName, String secondName){
        String fullName = firstName+" "+secondName;
        return manager.checkName(fullName);
    }
    private Boolean checkAge(String age){
        return age.matches("\\d+");
    }
    private Boolean checkValidAge(int age){
        return age >= 15 && age <= 20;
    }
    private Boolean checkGrade(String grade){
        return grade.matches("\\d+(\\.\\d+)?");
    }
    private Boolean checkValidGrades(double grade){
        return grade >= 0 && grade <= 20;
    }
}
