import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class studentStatistics implements ActionListener {

    JFrame frame = new JFrame();
    JPanel panel = new JPanel(new BorderLayout());
    JButton button = new JButton("Back");

    studentStatistics(StudentsManager manager){
        String name = "";
        boolean isEmpty = false;
        boolean isCancel = true;

        // Checking the class if it is empty.
        if(manager.isEmpty()){
            JOptionPane.showMessageDialog(null,"The class is empty","Empty class",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            isEmpty = true;
        }

        // Checking if the user presses cancel in the name field.
        if(isEmpty){
            while(true){
                name = JOptionPane.showInputDialog("What is the name of the student?");
                if(name == null){
                    isCancel = false;
                    break;
                }
                else if(name.matches("[a-zA-Z]+")){
                    break;
                }
                else{
                    JOptionPane.showMessageDialog(null,"The name must be only letters",
                            "Wrong input",JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        // Checking if the student is in the class.
        if(isCancel){
            boolean isFound = false;
            int index = 0;
            for(int x = 0; x<manager.studentsSize(); x++){
                if(manager.getStudentFullName(x).toLowerCase().contains(name.toLowerCase().trim())){
                    index = x;
                    isFound = true;
                    break;
                }
            }

            if(isFound){
                getStudentStatistics(index,manager);
            }
            else{
                JOptionPane.showMessageDialog(null,"The student is not found",
                        "Student not found",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            frame.dispose();
        }
    }

    private void getStudentStatistics(int index, StudentsManager manager){
        frame.setSize(350,220);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(6,2,0,10));
        String[] titles = {
                "The highest grade : ","The lowest grade : ","The average of the grades : ",
                "Grades less than 10 : ","The rate of the student : "};
        JLabel[] labels = new JLabel[titles.length];
        JLabel[] studentLabel = new JLabel[titles.length];

        for(int x = 0 ;x< labels.length; x++){
            labels[x] = new JLabel(titles[x]);
            labels[x].setFont(new Font("Arial",Font.PLAIN,20));
            labels[x].setForeground(Color.RED);
            studentLabel[x] = new JLabel();
            studentLabel[x].setFont(new Font("Arial",Font.PLAIN,15));
        }

        double getHighGrade = manager.getHighestGrade(index);
        double getLowGrade = manager.getLowestGrade(index);
        double getGradesAverage = manager.getGradesAverage(index);
        int getGradesLessTen = manager.getGradesLessTen(index);
        String rate = manager.getStudentRate(index);

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String maxGrade = String.valueOf(decimalFormat.format(Double.parseDouble(String.valueOf(getHighGrade))));
        String minGrade = String.valueOf(decimalFormat.format(Double.parseDouble(String.valueOf(getLowGrade))));
        String averageGrades = String.valueOf(decimalFormat.format(Double.parseDouble(String.valueOf(getGradesAverage))));
        String belowTen = String.valueOf(getGradesLessTen);

        studentLabel[0].setText(maxGrade);
        studentLabel[1].setText(minGrade);
        studentLabel[2].setText(averageGrades);
        studentLabel[3].setText(belowTen);
        studentLabel[4].setText(rate);

        button.setFocusable(false);
        button.setBorder(BorderFactory.createEtchedBorder());
        button.setBackground(Color.CYAN);
        button.addActionListener(this);
        gridPanel.add(button);

        for(int x = 0; x< labels.length; x++){
            gridPanel.add(labels[x]);
            gridPanel.add(studentLabel[x]);
        }
        gridPanel.add(button);
        gridPanel.setBackground(Color.LIGHT_GRAY);
        panel.add(gridPanel,BorderLayout.NORTH);

        frame.add(panel,BorderLayout.WEST);
        frame.setVisible(true);
    }
}
