import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class ClassStatistics {

    JFrame frame = new JFrame("Statistics");
    JButton button = new JButton("Back");
    JPanel statisticsClassPanel = new JPanel(new BorderLayout());

    // This is for statistics of the class.
    ClassStatistics(StudentsManager manager, TeachersManager teachersManager){
        if(manager.isEmpty()){
            JOptionPane.showMessageDialog(null,"The class is empty","Empty class",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            getClassStatistics(manager,teachersManager);
        }
    }

    private void getClassStatistics(StudentsManager manager, TeachersManager teachersManager){
        frame.setSize(420,350);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(11,2,0,5));

        String[] titles = {
                "The subject : ","The number of students : ","Number of males : ","Number of females : ",
                "Passed students : ","Failed students : ","The general average : ","The succuss rate : ",
                "The highest point in the class : ","The lowest point in the class : "};
        JLabel[] labels = new JLabel[titles.length];
        JLabel[] labelsStatistics = new JLabel[titles.length];

        for(int x = 0; x<labels.length; x++){
            labels[x] = new JLabel(titles[x]);
            labels[x].setFont(new Font("Arial",Font.PLAIN,20));
            labels[x].setForeground(Color.RED);
            labelsStatistics[x] = new JLabel();
            labelsStatistics[x].setFont(new Font("Arial",Font.PLAIN,15));
        }

        double max = manager.getHighestPoint();
        double min = manager.getLowestPoint();
        double average = manager.getClassAverage();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String maxPoint = String.valueOf(decimalFormat.format(Double.parseDouble(String.valueOf(max))));
        String minPoint = String.valueOf(decimalFormat.format(Double.parseDouble(String.valueOf(min))));
        String averagePoint = String.valueOf(decimalFormat.format(Double.parseDouble(String.valueOf(average))));

        labelsStatistics[0].setText(String.valueOf(teachersManager.getTeacherSubject()));
        labelsStatistics[1].setText(String.valueOf(manager.studentsSize()));
        labelsStatistics[2].setText(String.valueOf(manager.getMalesNumber()));
        labelsStatistics[3].setText(String.valueOf(manager.getFemalesNumber()));
        labelsStatistics[4].setText(String.valueOf(manager.getNumberOfPassedStudents()));
        labelsStatistics[5].setText(String.valueOf(manager.getNumberOfFailedStudents()));
        labelsStatistics[6].setText(averagePoint);
        labelsStatistics[7].setText(manager.getSuccussRate());
        labelsStatistics[8].setText(maxPoint);
        labelsStatistics[9].setText(minPoint);


        for(int x = 0 ; x<labels.length; x++){
            gridPanel.add(labels[x]);
            gridPanel.add(labelsStatistics[x]);
        }

        button.setFocusable(false);
        button.setBorder(BorderFactory.createEtchedBorder());
        button.setBackground(Color.CYAN);
        button.addActionListener(e -> frame.dispose());
        gridPanel.add(button);

        statisticsClassPanel.add(gridPanel,BorderLayout.NORTH);
        gridPanel.setBackground(Color.LIGHT_GRAY);

        frame.add(statisticsClassPanel,BorderLayout.WEST);
        frame.setVisible(true);
    }
}
