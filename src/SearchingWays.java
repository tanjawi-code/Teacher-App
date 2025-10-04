import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class SearchingWays implements searchAble {

    JFrame frame = new JFrame("Searching ways");
    private final TableRowSorter<TableModel> sorter;

    String[] labelTitles = {"Age","Gender","City","Point"};
    String[] ageTitles = {"15","16","17","18","19","20"};
    String[] pointTitles = {"18-20","15-17.99","10-14.99","5-9.99","0-4.99"};

    JLabel[] labels = new JLabel[labelTitles.length];
    JComboBox<String> ageBox = new JComboBox<>(ageTitles);
    JComboBox<Gender> genderBox = new JComboBox<>(Gender.values());
    JComboBox<City> cityBox = new JComboBox<>(City.values());
    JComboBox<String> pointBox = new JComboBox<>(pointTitles);
    JButton button = new JButton("Back");

    private String chosenAge;
    private String chosenPoint;

    SearchingWays(TableRowSorter<TableModel> sorter){
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        this.sorter = sorter;

        for(int x = 0 ;x< labels.length; x++){
            labels[x] = new JLabel(labelTitles[x]);
            labels[x].setFont(new Font("MV Boli",Font.BOLD,20));
            labels[x].setForeground(Color.GRAY);
        }

        JPanel gridPanel = new JPanel(new GridLayout(2,4,10,5));
        gridPanel.add(labels[0]);
        gridPanel.add(labels[1]);
        gridPanel.add(labels[2]);
        gridPanel.add(labels[3]);
        gridPanel.add(ageBox);
        gridPanel.add(genderBox);
        gridPanel.add(cityBox);
        gridPanel.add(pointBox);

        button.setFocusable(false);
        button.setBorder(BorderFactory.createEtchedBorder());
        button.setBackground(Color.CYAN);
        button.setPreferredSize(new Dimension(40,30));
        button.addActionListener(e -> frame.dispose());

        ageBox.addActionListener(e -> {
            Object object = ageBox.getSelectedItem();
            chosenAge = (String) object;
            searchByAge();
        });
        genderBox.addActionListener(e -> {
            Gender gender = (Gender) genderBox.getSelectedItem();
            sorter.setRowFilter(new RowFilter<>() {
                @Override
                public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                    return entry.getValue(3) == gender;
                }
            });
        });
        pointBox.addActionListener(e -> {
            Object object = pointBox.getSelectedItem();
            chosenPoint = (String) object;
            searchByPoint();
        });
        cityBox.addActionListener(e -> {
            City city = (City) cityBox.getSelectedItem();
            sorter.setRowFilter(new RowFilter<>() {
                @Override
                public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                    return entry.getValue(9) == city;
                }
            });
        });

        frame.add(gridPanel);
        frame.add(button);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void searchByAge() {
        switch (chosenAge){
            case "15" :
                sorter.setRowFilter(new RowFilter<Object,  Object>() {
                    @Override
                    public boolean include(Entry<? ,  ? > entry) {
                        int age = (int) entry.getValue(2);
                        return age == 15;
                    }
                });
                break;
            case "16" :
                sorter.setRowFilter(new RowFilter<Object,  Object>() {
                    @Override
                    public boolean include(Entry<? ,  ? > entry) {
                        int age = (int) entry.getValue(2);
                        return age == 16;
                    }
                });
                break;
            case "17" :
                sorter.setRowFilter(new RowFilter<Object,  Object>() {
                    @Override
                    public boolean include(Entry<? ,  ? > entry) {
                        int age = (int) entry.getValue(2);
                        return age == 17;
                    }
                });
                break;
            case "18" :
                sorter.setRowFilter(new RowFilter<Object,  Object>() {
                    @Override
                    public boolean include(Entry<? ,  ? > entry) {
                        int age = (int) entry.getValue(2);
                        return age == 18;
                    }
                });
                break;
            case "19" :
                sorter.setRowFilter(new RowFilter<Object,  Object>() {
                    @Override
                    public boolean include(Entry<? ,  ? > entry) {
                        int age = (int) entry.getValue(2);
                        return age == 19;
                    }
                });
                break;
            case "20" :
                sorter.setRowFilter(new RowFilter<Object,  Object>() {
                    @Override
                    public boolean include(Entry<? ,  ? > entry) {
                        int age = (int) entry.getValue(2);
                        return age == 20;
                    }
                });
                break;
        }
    }

    @Override
    public void searchByGender() {
    }

    @Override
    public void searchByAddress() {
    }

    @Override
    public void searchByPoint() {
        switch (chosenPoint){
            case "18-20" :
                sorter.setRowFilter(new RowFilter<Object,  Object>() {
                    @Override
                    public boolean include(Entry<? ,  ? > entry) {
                        double point = (double) entry.getValue(8);
                        return point >= 18 && point <= 20;
                    }
                });
                break;
            case "15-17.99" :
                sorter.setRowFilter(new RowFilter<Object,  Object>() {
                    @Override
                    public boolean include(Entry<? ,  ? > entry) {
                        double point = (double) entry.getValue(8);
                        return point >= 15 && point <= 17.99;
                    }
                });
                break;
            case "10-14.99" :
                sorter.setRowFilter(new RowFilter<Object,  Object>() {
                    @Override
                    public boolean include(Entry<? ,  ? > entry) {
                        double point = (double) entry.getValue(8);
                        return point >= 10 && point <= 14.99;
                    }
                });
                break;
            case "5-9.99" :
                sorter.setRowFilter(new RowFilter<Object,  Object>() {
                    @Override
                    public boolean include(Entry<? ,  ? > entry) {
                        double point = (double) entry.getValue(8);
                        return point >= 5 && point <= 9.99;
                    }
                });
                break;
            case "0-4.99" :
                sorter.setRowFilter(new RowFilter<Object,  Object>() {
                    @Override
                    public boolean include(Entry<? ,  ? > entry) {
                        double point = (double) entry.getValue(8);
                        return point >= 0 && point <= 4.99;
                    }
                });
                break;
        }
    }
}
