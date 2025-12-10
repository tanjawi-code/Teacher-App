package com.example.teacherApp.services.filters;

import com.example.teacherApp.interfaces.FilterData;
import com.example.teacherApp.models.Student;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ChoiceBox;

public class PointFilter implements FilterData {

    ChoiceBox<String> pointChoiceBox;

    public PointFilter(ChoiceBox<String> pointChoiceBox) {
        this.pointChoiceBox = pointChoiceBox;
    }

    @Override
    public void filter(FilteredList<Student> filteredList) {
        Runnable runnable = () -> filteredList.setPredicate(student -> {
            String point = pointChoiceBox.getValue();
            double max = 0;
            double min = 0;

            if (point != null) {
                switch (point) {
                    case "18-20" : max = 20; min = 18; break;
                    case "15-17.99" : max = 17.99; min = 15; break;
                    case "10-14.99" : max = 14.99; min = 10; break;
                    case "5-9.99" : max = 9.99; min = 5; break;
                    case "0-4.99" : max = 4.99; min = 0; break;
                    default: System.out.println();
                }
                return student.getPoint() >= min && student.getPoint() <= max;
            }
            else {
                return false;
            }
        });

        pointChoiceBox.valueProperty().addListener((_,_,_) -> runnable.run());
    }
}
