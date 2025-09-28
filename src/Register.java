import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register implements ActionListener {

    JFrame frame = new JFrame("Creating an account");
    private final StudentsManager studentsManager;
    private final TeachersManager manager; // Create teachers' accounts.

    private teacherGender chosenGender;
    private schools chosenSchool;
    private subjects chosenSubject;

    ImageIcon icon = new ImageIcon("school.png");
    ImageIcon imageIcon1 = new ImageIcon("see.png");
    Image scaledImage1 = imageIcon1.getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH);
    ImageIcon seeIconPassword = new ImageIcon(scaledImage1);
    ImageIcon imageIcon2 = new ImageIcon("invisible.png");
    Image scaledImage2 = imageIcon2.getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH);
    ImageIcon closeIconPassword = new ImageIcon(scaledImage2);

    // This is for holding the components.
    JPanel panel = new JPanel(new BorderLayout());
    JPanel gridPanel = new JPanel(new GridLayout(9,2,0,10));

    // This is for name, age, password, confirm password.
    JTextField teacherName = new JTextField(15);
    JTextField teacherAge = new JTextField(15);
    JPasswordField teacherPassword = new JPasswordField(15);
    JPasswordField confirmPassword = new JPasswordField(15);

    // This is for the choices that the teacher should choose : gender, school, subject.
    JComboBox<teacherGender> boxGender = new JComboBox<>(teacherGender.values());
    JComboBox<schools> boxSchools = new JComboBox<>(schools.values());
    JComboBox<subjects> boxSubjects = new JComboBox<>(subjects.values());

    // This is for the buttons.
    JButton buttonAccount = new JButton("Create account");
    JButton buttonClear = new JButton("Clear");
    JButton buttonBack = new JButton("Back");
    JButton showPasswordButton = new JButton("Show password",seeIconPassword);
    JButton HidePasswordButton = new JButton("Hide password",closeIconPassword);

    boolean genderIsSelected = false;
    boolean schoolIsSelected = false;
    boolean subjectIsSelected = false;
    Register(StudentsManager studentsManager, TeachersManager manager){
        frame.setTitle("Login-Screen");
        frame.setSize(460,450);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setIconImage(icon.getImage());
        this.studentsManager = studentsManager;
        this.manager = manager;

        // These are labels .
        String[] titles = {"Name : ","Age : ","Gender : ","School : ","Subject : ","Password : ","Confirm Password : "};
        JLabel[] labels = new JLabel[titles.length];
        for(int x = 0; x< labels.length; x++){
            labels[x] = new JLabel(titles[x]);
            labels[x].setFont(new Font("Arial",Font.PLAIN,20));
        }

        teacherName.setFont(new Font("MV Boli",Font.PLAIN,15));
        teacherAge.setFont(new Font("MV Boli",Font.PLAIN,15));
        teacherPassword.setFont(new Font("MV Boli",Font.PLAIN,15));
        confirmPassword.setFont(new Font("MV Boli",Font.PLAIN,15));

        boxGender.addActionListener(e -> {
            Object object = boxGender.getSelectedItem();
            if(object instanceof teacherGender){
                chosenGender = (teacherGender) object;
                genderIsSelected = true;
            }
        });
        boxSchools.addActionListener(e -> {
            Object object = boxSchools.getSelectedItem();
            if(object instanceof schools){
                chosenSchool = (schools) object;
                schoolIsSelected = true;
            }
        });
        boxSubjects.addActionListener(e -> {
            Object object = boxSubjects.getSelectedItem();
            if(object instanceof subjects){
                chosenSubject = (subjects) object;
                subjectIsSelected = true;
            }
        });

        // The button of creating an account.
        buttonAccount.setBackground(Color.CYAN);
        buttonAccount.setBorder(BorderFactory.createEtchedBorder());
        buttonAccount.setFocusable(false);
        buttonAccount.addActionListener(this);
        // The button of clearing the fields.
        buttonClear.setBackground(Color.GRAY);
        buttonClear.setBorder(BorderFactory.createEtchedBorder());
        buttonClear.setFocusable(false);
        buttonClear.addActionListener(this);
        // The button of backing to the main login.
        buttonBack.setBackground(Color.GRAY);
        buttonBack.setBorder(BorderFactory.createEtchedBorder());
        buttonBack.setFocusable(false);
        buttonBack.addActionListener(this);
        // The button of showing the password.
        showPasswordButton.setBackground(Color.LIGHT_GRAY);
        showPasswordButton.setBorder(BorderFactory.createEtchedBorder());
        showPasswordButton.setFocusable(false);
        showPasswordButton.addActionListener(this);
        // The button of hiding the password.
        HidePasswordButton.setBackground(Color.LIGHT_GRAY);
        HidePasswordButton.setBorder(BorderFactory.createEtchedBorder());
        HidePasswordButton.setFocusable(false);
        HidePasswordButton.addActionListener(this);

        gridPanel.add(labels[0]);
        gridPanel.add(teacherName);
        gridPanel.add(labels[1]);
        gridPanel.add(teacherAge);
        gridPanel.add(labels[2]);
        gridPanel.add(boxGender);
        gridPanel.add(labels[3]);
        gridPanel.add(boxSchools);
        gridPanel.add(labels[4]);
        gridPanel.add(boxSubjects);
        gridPanel.add(labels[5]);
        gridPanel.add(teacherPassword);
        gridPanel.add(labels[6]);
        gridPanel.add(confirmPassword);
        gridPanel.add(showPasswordButton);
        gridPanel.add(HidePasswordButton);
        gridPanel.add(buttonAccount);
        gridPanel.add(buttonClear);
        gridPanel.setBackground(Color.LIGHT_GRAY);
        panel.add(gridPanel,BorderLayout.NORTH);
        panel.setBackground(Color.LIGHT_GRAY);
        panel.add(buttonBack,BorderLayout.SOUTH);

        frame.add(panel,BorderLayout.WEST);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String value = button.getText();

        switch (value) {
            case "Create account" : createAccountButton(); break;
            case "Clear" : clearButton(); break;
            case "Back" : frame.dispose(); break;
            case "Show password" : teacherPassword.setEchoChar((char) 0); break;
            case "Hide password" : teacherPassword.setEchoChar('•'); break;
            default: System.out.println("Something went wrong.");
        }
    }

    // This is for creating an account.
    private void createAccountButton(){
        String password = new String(teacherPassword.getPassword());
        String confirm = new String(confirmPassword.getPassword());
        String name = teacherName.getText();

        if(teacherName.getText().isEmpty() || teacherAge.getText().isEmpty() ||
                teacherPassword.getPassword().length == 0 || confirmPassword.getPassword().length == 0){
            JOptionPane.showMessageDialog(null,"The fields are empty ",
                    "Empty fields",JOptionPane.ERROR_MESSAGE);
        }
        else if(!checkInputName(teacherName.getText())){
            JOptionPane.showMessageDialog(null,"The name should be only letters",
                    "Letters",JOptionPane.ERROR_MESSAGE);
        }
        else if(teacherPassword.getPassword().length <= 5){
            JOptionPane.showMessageDialog(null,
                    "The password's length is weak, The password must have between 6 and more(more than 10 is good).",
                    "Weak password",JOptionPane.WARNING_MESSAGE);
        }
        else if(!checkPassword(password,confirm)){
            JOptionPane.showMessageDialog(null,
                    "The password dose not match the confirmed password",
                    "Error password!",JOptionPane.ERROR_MESSAGE);
        }
        else if (!checkAge(teacherAge.getText())){
            JOptionPane.showMessageDialog(null,"The age must have only numbers.",
                    "Age",JOptionPane.ERROR_MESSAGE);
        }
        else if(!checkValidAge(Integer.parseInt(teacherAge.getText()))){
            JOptionPane.showMessageDialog(null,
                    "The teacher's age can't be less than 20 or bigger than 51",
                    "Age is not available",JOptionPane.ERROR_MESSAGE);
        }
        else if(!genderIsSelected){
            JOptionPane.showMessageDialog(null,"The gender is not selected",
                    "Gender is not selected",JOptionPane.ERROR_MESSAGE);
        }
        else if(!schoolIsSelected){
            JOptionPane.showMessageDialog(null,"The school is not selected",
                    "School is not selected",JOptionPane.ERROR_MESSAGE);
        }
        else if(!subjectIsSelected){
            JOptionPane.showMessageDialog(null,"The subject is not selected",
                    "Subject is not selected",JOptionPane.ERROR_MESSAGE);
        }
        else if(teacherPassword.getPassword().length > 30){
            JOptionPane.showMessageDialog(null,
                    "The password's length is more than 30","The password's length",
                    JOptionPane.ERROR_MESSAGE);
        }
        else{
            int age = Integer.parseInt(teacherAge.getText());
            Teacher teacher = new Teacher(name,age,password,chosenSubject,chosenSchool,chosenGender);

            manager.saveTeacher(teacher);
            new MainWindow(studentsManager,manager);
            JOptionPane.showMessageDialog(null,"The account is created.",
                    "Welcome",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    // This is for clearing the fields.
    private void clearButton(){
        teacherName.setText("");
        teacherAge.setText("");
        teacherPassword.setText("");
        confirmPassword.setText("");
        boxGender.setSelectedIndex(0);
        boxSchools.setSelectedIndex(0);
        boxSubjects.setSelectedIndex(0);
        genderIsSelected = false;
        schoolIsSelected = false;
        subjectIsSelected = false;
    }

    private Boolean checkPassword(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }
    private Boolean checkAge(String age){
        return age.matches("\\d+");
    }
    private Boolean checkValidAge(int age){
        return age >= 20 && age <= 50;
    }
    private Boolean checkInputName(String name){
        return name.matches("[a-zA-Z]+(\\s[a-zA-z]+)*");
    }
}
