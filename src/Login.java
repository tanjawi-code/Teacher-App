import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    int width = 420;
    int height = 420;
    Login(){
        this.setTitle("Login-Screen");
        this.setSize(width,height);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String text = button.getText();
    }
}
