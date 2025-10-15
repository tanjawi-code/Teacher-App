import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Objects;

public class MainWindow extends JFrame implements ActionListener {

    private final StudentsManager manager;
    private final TeachersManager teachersManager;
    private final StudentsSQLite studentsSQLite;
    private final TeachersSQLite teachersSQLite;

    // These are the main panels that are in the frame.
    JPanel northPanel = new JPanel(new BorderLayout());
    JPanel centerPanel = new JPanel(new BorderLayout());
    JPanel westPanel = new JPanel(new BorderLayout());
    JPanel southPanel = new JPanel(new BorderLayout());

    // This is for settings image.
    ImageIcon schoolIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/school.png")));
    ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/cogwheel.png")));
    Image scaledImage = icon.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
    ImageIcon settingsIcon = new ImageIcon(scaledImage);
    ImageIcon icon2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/user.png")));
    Image scaledImage2 = icon2.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
    ImageIcon userIcon = new ImageIcon(scaledImage2);
    JButton settingsButton = new JButton();
    JButton userButton = new JButton();

    // This is for the menuBar.
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem openItem = new JMenuItem("Open file");
    JMenuItem saveItem = new JMenuItem("save as file");
    JMenuItem exitItem = new JMenuItem("Exit");
    JMenu statisticsMenu = new JMenu("Statistics");
    JMenuItem classStatisticsItem = new JMenuItem("Class Statistics");
    JMenuItem studentStatisticsItem = new JMenuItem("Student Statistics");

    // This is for the buttons in the lower part of the screen.
    String[] rightUnderTitles = {"Delete","Update","Searching ways"};
    String[] otherButtonsTitles = {"Add student","Clear","Search","Refresh","Confirm update"};
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
    MainWindow(StudentsManager  manager,TeachersManager teachersManager,StudentsSQLite students,TeachersSQLite teachers,Boolean userIsFound,SavedFilesSQLite savedFilesSQLite){
        this.setTitle("Students management");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLayout(new BorderLayout());
        this.setIconImage(schoolIcon.getImage());
        this.manager = manager;
        this.teachersManager = teachersManager;
        this.studentsSQLite = students;
        this.teachersSQLite = teachers;

        JPanel northLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEADING,20,0));
        northLeftPanel.setBackground(new Color(4, 53, 83));
        northLeftPanel.setOpaque(true);
        JPanel messagePanel = new JPanel(new FlowLayout());
        messagePanel.setBackground(new Color(4, 53, 83));
        messagePanel.setOpaque(true);

        // This is for the label message.
        JLabel messageLabel = new JLabel("Students Management");
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial",Font.BOLD,40));
        messagePanel.add(messageLabel);

        // This is for the menu in the north.
        settingsButton.setFocusable(false); // Starting of settings button.
        settingsButton.setBorder(BorderFactory.createEtchedBorder());
        settingsButton.setBackground(Color.WHITE);
        settingsButton.setIcon(settingsIcon);
        settingsButton.addActionListener(this);
        userButton.setFocusable(false); // Starting of user button.
        userButton.setBorder(BorderFactory.createEtchedBorder());
        userButton.setBackground(Color.WHITE);
        userButton.setIcon(userIcon);
        userButton.addActionListener(this);

        // This is for the menu bar.
        SaveFile saveFile = new SaveFile();
        menuBar.setPreferredSize(new Dimension(95,30));
        menuBar.setBackground(Color.LIGHT_GRAY);

        fileMenu.setForeground(Color.BLACK);
        openItem.setMnemonic(KeyEvent.VK_O);
        saveItem.setMnemonic(KeyEvent.VK_S);
        exitItem.setMnemonic(KeyEvent.VK_E);
        openItem.addActionListener(e -> new GetFile(manager,model,studentsSQLite,teachersSQLite,savedFilesSQLite));
        saveItem.addActionListener(e -> saveFile.saveFile(manager,savedFilesSQLite,teachersSQLite.getTeacherID()));
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.setMnemonic(KeyEvent.VK_F); // Alt + F/f.
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        statisticsMenu.setForeground(Color.BLACK);
        classStatisticsItem.setMnemonic(KeyEvent.VK_C);
        studentStatisticsItem.setMnemonic(KeyEvent.VK_S);
        classStatisticsItem.addActionListener(e -> new ClassStatistics(manager,teachersManager));
        studentStatisticsItem.addActionListener(e -> new studentStatistics(manager));

        statisticsMenu.setMnemonic(KeyEvent.VK_S); // Alt + S/s.
        statisticsMenu.add(classStatisticsItem);
        statisticsMenu.add(studentStatisticsItem);

        menuBar.add(fileMenu);
        menuBar.add(statisticsMenu);
        northLeftPanel.add(settingsButton);
        northLeftPanel.add(userButton);
        northLeftPanel.add(menuBar);
        northPanel.setBackground(new Color(4, 53, 83));
        northPanel.setPreferredSize(new Dimension(0,100));
        northPanel.setOpaque(true);
        northPanel.add(messagePanel,BorderLayout.NORTH);
        northPanel.add(northLeftPanel,BorderLayout.WEST);

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

        JPanel westNorthPanel = new JPanel(new BorderLayout());
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
        westNorthPanel.add(textPanel,BorderLayout.NORTH);
        westPanel.add(westNorthPanel,BorderLayout.CENTER);

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
        centerPanel.add(pane,BorderLayout.CENTER);
        centerPanel.add(tableChoices,BorderLayout.NORTH);
        table.setEnabled(false);

        // This is for the lower part in right side.
        JPanel southFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,0));
        for(int x = 0; x<rightUnderButtons.length; x++){
            rightUnderButtons[x] = new JButton(rightUnderTitles[x]);
            String title = rightUnderButtons[x].getText();
            rightUnderButtons[x].setFocusable(false);
            rightUnderButtons[x].setBorder(BorderFactory.createEtchedBorder());
            rightUnderButtons[x].addActionListener(this);
            rightUnderButtons[x].setPreferredSize(new Dimension(200,100));
            rightUnderButtons[x].setFont(new Font("MV Boli",Font.BOLD,20));
            if(Arrays.asList(rightUnderTitles).contains(title)){
                switch (title) {
                    case "Delete" -> rightUnderButtons[x].setBackground(Color.RED);
                    case "Update" -> rightUnderButtons[x].setBackground(new Color(68, 110, 136));
                    default -> rightUnderButtons[x].setBackground(new Color(0, 118, 192));
                }
            }
            southFlowPanel.add(rightUnderButtons[x]);

        }
        southPanel.add(southFlowPanel,BorderLayout.CENTER);

        if(userIsFound){
            getTeacherStudents();
        }

        this.add(northPanel,BorderLayout.NORTH); // Holds The menu bar, welcome message, and two buttons.
        this.add(centerPanel,BorderLayout.CENTER); // Holds the menu table and table.
        this.add(westPanel,BorderLayout.WEST); // Holds the fields.
        this.add(southPanel,BorderLayout.SOUTH); // Holds 5 buttons.
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String value = button.getText();

        if(Arrays.asList(rightUnderTitles).contains(value)){
            switch (value){
                case "Delete" -> deleteStudent();
                case "Update" -> updateStudent();
                case "Searching ways" -> new SearchingWays(sorter,manager,table,tableTitles);
                default -> System.out.println();
            }
        }
        else if(Arrays.asList(otherButtonsTitles).contains(value)){
            switch (value){
                case "Add student" -> buttonAddStudent();
                case "Clear" -> buttonClearFields();
                case "Search" -> searchForStudent();
                case "Refresh" -> refreshTable();
                case "Confirm update" -> confirmUpdate();
                default -> System.out.println();
            }
        }
        else if(e.getSource() == userButton){
            new Account(teachersManager,teachersSQLite);
        }
        else if (e.getSource() == settingsButton){
            JOptionPane.showMessageDialog(null,"It's coming later","Settings",
                    JOptionPane.INFORMATION_MESSAGE);
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
                else if(name.matches("[a-zA-Z]+(\\s[a-zA-z]+)*") ){
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
                    Student student = manager.getStudent(x);
                    String fullName = manager.getStudentFullName(x);
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
                    studentsSQLite.updateStudentGrades(fullName,ID,grades,student.getStudentAge(),point);
                    break;
                }
            }
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
