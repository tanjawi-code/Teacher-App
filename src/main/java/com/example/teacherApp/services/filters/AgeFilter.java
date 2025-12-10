package com.example.teacherApp.services.filters;

import com.example.teacherApp.interfaces.FilterData;
import com.example.teacherApp.models.Student;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ChoiceBox;

public class AgeFilter implements FilterData {

    ChoiceBox<Integer> ageChoiceBox;

    public AgeFilter(ChoiceBox<Integer> ageChoiceBox) {
        this.ageChoiceBox = ageChoiceBox;
    }

    @Override
    public void filter(FilteredList<Student> filteredList) {
        Runnable runnable = () -> filteredList.setPredicate(student -> {
            Integer age = ageChoiceBox.getValue();
            return age != null && age == student.getAge();
        });
        ageChoiceBox.valueProperty().addListener((_, _,_) -> runnable.run());
    }
}
