package com.example.teacherApp.services.filters;

import com.example.teacherApp.interfaces.FilterData;
import com.example.teacherApp.models.Student;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ChoiceBox;

public class StatusFilter implements FilterData {

    ChoiceBox<String> statusChoiceBox;

    public StatusFilter(ChoiceBox<String> statusChoiceBox) {
        this.statusChoiceBox = statusChoiceBox;
    }

    @Override
    public void filter(FilteredList<Student> filteredList) {
        Runnable runnable = () -> filteredList.setPredicate(student -> {
            String status = statusChoiceBox.getValue();

            if (status != null && status.equals("Passed") && student.getPoint() >= 10) {
                return true;
            }
            else return status != null && status.equals("Failed") && student.getPoint() <= 9.99;
        });
        statusChoiceBox.valueProperty().addListener((_,_,_) -> runnable.run());
    }
}
