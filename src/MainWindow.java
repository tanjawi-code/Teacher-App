import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            "First name : ","Second name : ","Age : ","Gender : ",
            "Grade 1 : ","Grade 2 : ","Grade 3 : ","Address : "};
    JTextField[] textInputs = new JTextField[leftTopTitles.length];
    JLabel[] labels = new JLabel[leftTopTitles.length];
    JButton addStudent = new JButton("Add student");
    JButton cleanButton = new JButton("Clean");
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
    JTextField searchName = new JTextField(50);

    DefaultTableModel model = new DefaultTableModel(tableTitles,0);
    JTable table = new JTable(model);
    JScrollPane pane = new JScrollPane(table);

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
        addStudent.setFocusable(false); // Button of adding a student.
        addStudent.setBorder(BorderFactory.createEtchedBorder());
        addStudent.setBackground(Color.GREEN);
        addStudent.addActionListener(this);
        cleanButton.setFocusable(false); // Button of cleaning the fields.
        cleanButton.setBorder(BorderFactory.createEtchedBorder());
        cleanButton.setBackground(Color.GRAY);
        cleanButton.addActionListener(this);
        textPanel.add(addStudent);
        textPanel.add(cleanButton);
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
        searchName.setPreferredSize(new Dimension(100,30)); // Starting of searching field.
        searchName.setBackground(Color.LIGHT_GRAY);
        tableChoices.add(refresh);
        tableChoices.add(search);
        tableChoices.add(searchName);

        // This is for the table.
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
        this.add(westPanel,BorderLayout.WEST); // Holds the north and center in the west
        this.add(southPanel,BorderLayout.SOUTH); // Holds the south in the west.
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String value = button.getText();

        if(Arrays.asList(leftUnderTitles).contains(value)){
            switch (value){
                case "Statistics" :
                    if(manager.isEmpty()){
                        JOptionPane.showMessageDialog(null,
                                "There are no students in the class yet","Empty class",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "Passed" :
                    if(manager.isEmpty()){
                        JOptionPane.showMessageDialog(null,"There are no students",
                                "Empty class",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "Failed" :
                    if(manager.isEmpty()){
                        JOptionPane.showMessageDialog(null,
                                "There are no students in the class","Empty class",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "Top 3" :
                    if(manager.isEmpty()){
                        JOptionPane.showMessageDialog(null,"The class is empty",
                                "Empty class",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    break;
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

        }
        else if (value.equals("Add student")) {
            JOptionPane.showMessageDialog(null,"It's coming soon",
                    "Adding students",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
