import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Login extends JFrame implements ActionListener {

    String[] buttonsTitles = {"Login","Create new account"};
    JButton[] buttons = new JButton[buttonsTitles.length];
    JButton forgetPasswordButton = new JButton("Did you forget the password?");

    JTextField textName = new JTextField(20);
    JPasswordField textPassword = new JPasswordField(20);
    JLabel labelName = new JLabel("Name : ");
    JLabel labelPassword = new JLabel("Password : ");
    JLabel welcomeMessage = new JLabel("Welcome to the students management app");

    JPanel northPanel = new JPanel(new FlowLayout());
    JPanel centerPanel = new JPanel(new BorderLayout());
    JPanel southPanel = new JPanel(new FlowLayout());

    private teacherGender chosenGender;
    private schools chosenSchool;
    private subjects chosenSubject;
    Login(){
        this.setTitle("Login-Screen");
        this.setSize(420,420);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        // This is for displaying a welcome message in the top.
        welcomeMessage.setForeground(Color.RED);
        welcomeMessage.setFont(new Font("MV Boli",Font.ITALIC,19));
        northPanel.add(welcomeMessage);

        // This is for south buttons (login, create new account).
        for(int x = 0 ; x<buttons.length; x++){
            buttons[x] = new JButton(buttonsTitles[x]);
            buttons[x].setPreferredSize(new Dimension(150,30));
            buttons[x].setBackground(Color.CYAN);
            buttons[x].setFocusable(false);
            buttons[x].setBorder(BorderFactory.createEtchedBorder());
            buttons[x].addActionListener(this);
            southPanel.add(buttons[x]);
        }
        southPanel.setBackground(Color.LIGHT_GRAY);

        // This is for the middle part.
        JPanel middlePanel = new JPanel(new GridLayout(2,2));
        labelName.setFont(new Font("MV Boli",Font.ITALIC,20));
        labelPassword.setFont(new Font("MV Boli",Font.ITALIC,20));
        textName.setFont(new Font("MV Boli",Font.PLAIN,20));
        textPassword.setFont(new Font("MV Boli",Font.BOLD,20));
        middlePanel.add(labelName);
        middlePanel.add(textName);
        middlePanel.add(labelPassword);
        middlePanel.add(textPassword);
        middlePanel.setBackground(Color.GRAY);
        forgetPasswordButton.setPreferredSize(new Dimension(0,40));
        forgetPasswordButton.setBackground(Color.RED);
        forgetPasswordButton.setFocusable(false);
        forgetPasswordButton.setBorder(BorderFactory.createEtchedBorder());
        forgetPasswordButton.addActionListener(this);
        centerPanel.add(middlePanel,BorderLayout.NORTH);
        centerPanel.add(forgetPasswordButton,BorderLayout.SOUTH);
        centerPanel.setBackground(Color.GRAY);

        this.add(northPanel,BorderLayout.NORTH);
        this.add(centerPanel,BorderLayout.CENTER);
        this.add(southPanel,BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private void createAccount(){
        JFrame frame = new JFrame("Creating an account");
        frame.setTitle("Login-Screen");
        frame.setSize(430,420);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        JPanel gridPanel = new JPanel(new GridLayout(8,2,0,10));

        // These are labels .
        String[] titles = {"Name : ","Age : ","Gender : ","School : ","Subject : ","Password : ","Confirm Password : "};
        JLabel[] labels = new JLabel[titles.length];
        for(int x = 0; x< labels.length; x++){
            labels[x] = new JLabel(titles[x]);
            labels[x].setFont(new Font("Arial",Font.PLAIN,20));
        }

        // This is for the name, age, password.
        JTextField teacherName = new JTextField(15);
        JTextField teacherAge = new JTextField(15);
        JPasswordField teacherPassword = new JPasswordField(15);
        JPasswordField confirmPassword = new JPasswordField(15);

        teacherName.setFont(new Font("MV Boli",Font.PLAIN,15));
        teacherAge.setFont(new Font("MV Boli",Font.PLAIN,15));
        teacherPassword.setFont(new Font("MV Boli",Font.PLAIN,15));
        confirmPassword.setFont(new Font("MV Boli",Font.PLAIN,15));

        // This is for the choices that the teacher should choose : gender, school, subject.
        JComboBox<teacherGender> boxGender = new JComboBox<>(teacherGender.values());
        JComboBox<schools> boxSchools = new JComboBox<>(schools.values());
        JComboBox<subjects> boxSubjects = new JComboBox<>(subjects.values());

        boxGender.addActionListener(e -> {
            Object check = boxGender.getSelectedItem();
            if(check instanceof teacherGender){
                chosenGender = (teacherGender) check;
            }
        });
        boxSchools.addActionListener(e -> {
            Object object = boxSchools.getSelectedItem();
            if(object instanceof schools){
                chosenSchool = (schools) object;
            }
        });
        boxSubjects.addActionListener(e -> {
            Object check = boxSubjects.getSelectedItem();
            if(check instanceof subjects){
                chosenSubject = (subjects) check;
            }
        });

        // This is for buttons (create button and clear button).
        JButton buttonAccount = new JButton("Create account"); // The button of creating an account.
        buttonAccount.setBackground(Color.CYAN);
        buttonAccount.setBorder(BorderFactory.createEtchedBorder());
        buttonAccount.setFocusable(false);
        JButton buttonClear = new JButton("Clear"); // The button of clearing the fields.
        buttonClear.setBackground(Color.GRAY);
        buttonClear.setBorder(BorderFactory.createEtchedBorder());
        buttonClear.setFocusable(false);

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
        gridPanel.add(buttonAccount);
        gridPanel.add(buttonClear);
        gridPanel.setBackground(Color.LIGHT_GRAY);
        panel.add(gridPanel,BorderLayout.NORTH);
        panel.setBackground(Color.LIGHT_GRAY);

        buttonAccount.addActionListener(e -> {
            String password = new String(teacherPassword.getPassword());
            String confirm = new String(confirmPassword.getPassword());

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
            else{
                String name = teacherName.getText();
                int age = Integer.parseInt(teacherAge.getText());

                Teacher teacher = new Teacher(name,age,password,chosenSubject,chosenSchool,chosenGender);
                JOptionPane.showMessageDialog(null,"The account is created.",
                        "Welcome",JOptionPane.INFORMATION_MESSAGE);

                TeachersManager manager = new TeachersManager();
                manager.saveTeacher(teacher);
            }
        });
        buttonClear.addActionListener(e -> {
            teacherName.setText("");
            teacherAge.setText("");
            teacherPassword.setText("");
            confirmPassword.setText("");
            boxGender.setSelectedIndex(0);
            boxSchools.setSelectedIndex(0);
            boxSubjects.setSelectedIndex(0);
        });

        frame.add(panel,BorderLayout.WEST);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String text = button.getText();

        String name = textName.getText();

        if(Arrays.asList(buttonsTitles).contains(text)){
            if(text.equals("Login")){
                if(textName.getText().isEmpty() || textPassword.getPassword().length == 0){
                    JOptionPane.showMessageDialog(null,"The fields are empty",
                            "Empty fields",JOptionPane.ERROR_MESSAGE);
                }
                else if(!checkInputName(name)){
                    JOptionPane.showMessageDialog(null,"The name should only be letters",
                            "ERROR",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Hello Mr."+name,
                            "Welcome message",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else{
                createAccount();
            }
        }
        else if(text.equals(forgetPasswordButton.getText())){
            JOptionPane.showMessageDialog(null,"It's coming soon","Forgetting the password",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private Boolean checkInputName(String name){
        return name.matches("[a-zA-Z]+(\\s[a-zA-z]+)*");
    }
    private Boolean checkPassword(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }
    private Boolean checkAge(String age){
        return age.matches("\\d+");
    }
}
