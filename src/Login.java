import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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

        if(Arrays.asList(buttonsTitles).contains(text)){
            if(text.equals("Login")){
                loginButton();
            }
            else{
                new Register(manager,teachersManager);
            }
        }
        else if(text.equals(forgetPasswordButton.getText())){
            forgetPassword();
        }
        else if(text.equals("Clear")){
            clearButtonFields();
        }
        else if(text.equals("Show password")){
            textPassword.setEchoChar((char) 0);
        }
        else if (text.equals("Hide password")) {
            textPassword.setEchoChar('•');
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
            JOptionPane.showMessageDialog(null,"Hello Mr."+name,
                    "Welcome message",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    // This is for forgetting the password.
    private void forgetPassword(){
        JOptionPane.showMessageDialog(null,"It's coming soon","Forgetting the password",
                JOptionPane.INFORMATION_MESSAGE);
    }
    // This is for clearing fields.
    private void clearButtonFields(){
        textName.setText("");
        textPassword.setText("");
    }

    private Boolean checkInputName(String name){
        return name.matches("[a-zA-Z]+(\\s[a-zA-z]+)*");
    }
    private Boolean checkNameLength(String name){
        return name.length() <= 20;
    }
}
