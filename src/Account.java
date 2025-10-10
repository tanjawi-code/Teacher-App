import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Account implements ActionListener {

    TeachersManager manager;

    JFrame frame = new JFrame("My account");
    JButton changePasswordButton = new JButton("Change password");
    JButton deleteAccountButton = new JButton("Delete account");
    JButton buttonBack = new JButton("Back");
    JPanel westPanel = new JPanel(new BorderLayout());
    String password;

    Account(TeachersManager manager){
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        this.manager = manager;

        changePasswordButton.setFocusable(false); // Changing password button.
        changePasswordButton.setBorder(BorderFactory.createEtchedBorder());
        changePasswordButton.setBackground(Color.GRAY);
        changePasswordButton.addActionListener(this);
        deleteAccountButton.setFocusable(false); // Delete account button.
        deleteAccountButton.setBorder(BorderFactory.createEtchedBorder());
        deleteAccountButton.setBackground(Color.RED);
        deleteAccountButton.addActionListener(this);
        buttonBack.setFocusable(false); // Button back.
        buttonBack.setBorder(BorderFactory.createEtchedBorder());
        buttonBack.setBackground(Color.CYAN);
        buttonBack.addActionListener(this);

        String[] titles = {"Name : ","Age : ","Gender : ","Subject : ","School : "};
        JLabel[] labels = new JLabel[titles.length];
        JLabel[] teacherLabel = new JLabel[titles.length];
        JPanel gridPanel = new JPanel(new GridLayout(12,2,0,5));

        for(int x = 0 ;x< labels.length; x++){
            labels[x] = new JLabel(titles[x]);
            labels[x].setFont(new Font("MV Boli",Font.PLAIN,20));
            teacherLabel[x] = new JLabel();
            teacherLabel[x].setFont(new Font("Arial",Font.PLAIN,15));
        }

        String name = manager.getTeacherName();
        int age = manager.getTeacherAge();
        password = manager.getTeacherPassword();
        String gender = String.valueOf(manager.getTeacherGender());
        String school = String.valueOf(manager.getTeacherSchool());
        String subject = String.valueOf(manager.getTeacherSubject());

        teacherLabel[0].setText(name);
        teacherLabel[1].setText(String.valueOf(age));
        teacherLabel[2].setText(gender);
        teacherLabel[3].setText(subject);
        teacherLabel[4].setText(school);

        for(int x = 0; x< labels.length; x++){
            gridPanel.add(labels[x]);
            gridPanel.add(teacherLabel[x]);
        }

        gridPanel.add(changePasswordButton);
        gridPanel.add(buttonBack);
        gridPanel.add(deleteAccountButton);
        gridPanel.setBackground(Color.LIGHT_GRAY);

        westPanel.add(gridPanel,BorderLayout.NORTH);
        frame.add(westPanel,BorderLayout.WEST);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == changePasswordButton){
            changePassword(password);
        }
        else if(e.getSource() == deleteAccountButton){
            deleteAccount();
        }
        else if(e.getSource() == buttonBack){
            frame.dispose();
        }
    }

    // This is for changing password.
    private void changePassword(String password){

    }
    // This is for deleting the account.
    private void deleteAccount(){
        int choice;
        choice = JOptionPane.showConfirmDialog(null,
                """
                        Deleting your account will remove everything you have in this account:
                        your profile, your students' details and you will start from the beginning.
                        Are sure you want to delete and lose everything in this account?""",
                "Deleting the account",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

        if(choice == 0){
            JOptionPane.showMessageDialog(null,"It's coming soon","Coming soon",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
