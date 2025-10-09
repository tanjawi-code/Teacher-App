import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Login extends JFrame implements ActionListener {

    private final StudentsManager manager;
    private final TeachersManager teachersManager;

    // This is for the log of the screen.
    ImageIcon icon = new ImageIcon("school.png");
    // This is for the icons of showing, hiding password.
    ImageIcon icon1 = new ImageIcon("see.png");
    Image scaledImage1 = icon1.getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH);
    ImageIcon seePassword = new ImageIcon(scaledImage1);
    ImageIcon icon2 = new ImageIcon("invisible.png");
    Image scaledImage2 = icon2.getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH);
    ImageIcon closePassword = new ImageIcon(scaledImage2);

    String[] buttonsTitles = {"Login","Create new account"};
    JButton[] buttons = new JButton[buttonsTitles.length];
    JButton forgetPasswordButton = new JButton("Did you forget the password?");
    JButton buttonClear = new JButton("Clear");
    JButton showPasswordButton = new JButton("Show password",seePassword);
    JButton hidePasswordButton = new JButton("Hide password",closePassword);

    JTextField textName = new JTextField(20);
    JPasswordField textPassword = new JPasswordField(20);
    JLabel labelName = new JLabel("Name : ");
    JLabel labelPassword = new JLabel("Password : ");
    JLabel welcomeMessage = new JLabel("Welcome to the students management app");

    JPanel northPanel = new JPanel(new FlowLayout());
    JPanel centerPanel = new JPanel(new BorderLayout());
    JPanel southPanel = new JPanel(new FlowLayout());

    Login(StudentsManager manager,TeachersManager teachersManager){
        this.setTitle("Login-Screen");
        this.setSize(420,420);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setIconImage(icon.getImage());
        this.manager = manager;
        this.teachersManager = teachersManager;

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
        JPanel middlePanel = new JPanel(new GridLayout(4,2));
        labelName.setFont(new Font("MV Boli",Font.ITALIC,20));
        labelPassword.setFont(new Font("MV Boli",Font.ITALIC,20));
        textName.setFont(new Font("MV Boli",Font.PLAIN,20));
        textPassword.setFont(new Font("MV Boli",Font.BOLD,20));

        buttonClear.setBackground(Color.LIGHT_GRAY); // Starting of the clearing fields.
        buttonClear.setFocusable(false);
        buttonClear.setBorder(BorderFactory.createEtchedBorder());
        buttonClear.addActionListener(this);
        showPasswordButton.setBackground(Color.LIGHT_GRAY); // Starting of the showing password.
        showPasswordButton.setFocusable(false);
        showPasswordButton.setBorder(BorderFactory.createEtchedBorder());
        showPasswordButton.addActionListener(this);
        hidePasswordButton.setBackground(Color.LIGHT_GRAY); // Starting of the hiding button.
        hidePasswordButton.setFocusable(false);
        hidePasswordButton.setBorder(BorderFactory.createEtchedBorder());
        hidePasswordButton.addActionListener(this);
        middlePanel.add(labelName);
        middlePanel.add(textName);
        middlePanel.add(labelPassword);
        middlePanel.add(textPassword);
        middlePanel.add(showPasswordButton);
        middlePanel.add(hidePasswordButton);
        middlePanel.add(buttonClear);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String text = button.getText();

        switch (text) {
            case "Login" -> loginButton();
            case "Create new account" -> new Register(manager, teachersManager);
            case "Did you forget the password?" -> forgetPassword();
            case "Clear" -> clearButtonFields();
            case "Show password" -> textPassword.setEchoChar((char) 0);
            case "Hide password" -> textPassword.setEchoChar('•');
        }
    }

    // This is for logging into the user's account.
    private void loginButton(){
        String name = textName.getText();

        if(textName.getText().isEmpty() || textPassword.getPassword().length == 0){
            JOptionPane.showMessageDialog(null,"The fields are empty",
                    "Empty fields",JOptionPane.ERROR_MESSAGE);
        }
        else if(!checkInputName(name)){
            JOptionPane.showMessageDialog(null,"The name should only be letters",
                    "ERROR",JOptionPane.ERROR_MESSAGE);
        }
        else if(!checkNameLength(name)){
            JOptionPane.showMessageDialog(null,"The length of the name is too long",
                    "The limit is 20",JOptionPane.ERROR_MESSAGE);
        }
        else{
            String password = new String(textPassword.getPassword());
            Boolean exists = teachersManager.selectTeacherFromTable(name,password);
            if(exists){
                new MainWindow(manager,teachersManager);
                JOptionPane.showMessageDialog(null,"Welcome again "+name,"Welcome",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(null,"The name or the password is incorrect",
                        "Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    // This is for forgetting the password.
    private void forgetPassword(){
        String name;
        boolean isCancel = false;
        while (true){
            name = JOptionPane.showInputDialog("What is your account name : ");
            if(name == null){
                isCancel = true;
                break;
            }
            else if(checkInputName(name)){
                break;
            }
            else {
                JOptionPane.showMessageDialog(null,"The name must only letters",
                        "Name",JOptionPane.ERROR_MESSAGE);
            }
        }

        boolean nameExists = teachersManager.checkUserAccountName(name);
        if(!isCancel){
            if(nameExists){
                checkUserCode();
            }
            else {
                JOptionPane.showMessageDialog(null,"Couldn't find an account bu this name",
                        "Name is not found",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    // This is for clearing fields.
    private void clearButtonFields(){
        textName.setText("");
        textPassword.setText("");
    }

    // This is for if the teacher forgets the password. it will create a code and send it to desktop.
    private int getAccountCode(){
        Random random = new Random();
        int code = random.nextInt(100000,9999999);
        String codeValue = String.valueOf(code);
        String filePath = "C:\\Users\\asus\\Desktop\\Account code.text";
        try(FileWriter fileWriter = new FileWriter(filePath)){
            fileWriter.write(codeValue);
        }
        catch (FileNotFoundException e){
            JOptionPane.showMessageDialog(null,"The location of the file is not found",
                    "Location is not found",JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(null,"Something is wrong","Something wrong",
                    JOptionPane.ERROR_MESSAGE);
        }
        return code;
    }
    // This is for making the code unavailable.
    private void brokeTheCode(){
        String filePath = "C:\\Users\\asus\\Desktop\\Account code.text";
        try(FileWriter fileWriter = new FileWriter(filePath)){
            fileWriter.write("");
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
    // This is for writing the code.
    private void checkUserCode() {
        String code;
        String realCode = String.valueOf(getAccountCode());
        int count = 5;
        boolean isEqual;
        while (true){
            code = JOptionPane.showInputDialog("Tries: "+count+" -- Enter the code: ");
            if (code == null){
                isEqual = false;
                break;
            }
            else if(code.equals(realCode)){
                isEqual = true;
                brokeTheCode();
                break;
            }
            else if(count == 1){
                JOptionPane.showMessageDialog(null,"time's up","Time is finished",
                        JOptionPane.ERROR_MESSAGE);
                isEqual = false;
                break;
            }
            else {
                JOptionPane.showMessageDialog(null,"The code is incorrect","Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            count--;
        }


        if(isEqual){
            new MainWindow(manager,teachersManager);
        }
    }

    private Boolean checkInputName(String name){
        return name.matches("[a-zA-Z]+(\\s[a-zA-z]+)*");
    }
    private Boolean checkNameLength(String name){
        return name.length() <= 20;
    }
}
