package com.example.teacherApp.services.filters;

import com.example.teacherApp.Enums.Gender;
import com.example.teacherApp.interfaces.FilterData;
import com.example.teacherApp.models.Student;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ChoiceBox;

public class GenderFilter implements FilterData {

    ChoiceBox<Gender> genderChoiceBox;

    public GenderFilter(ChoiceBox<Gender> genderChoiceBox) {
        this.genderChoiceBox = genderChoiceBox;
    }

    @Override
    public void filter(FilteredList<Student> filteredList) {
        Runnable runnable = () -> filteredList.setPredicate(student -> {
            Gender gender = genderChoiceBox.getValue();
            return gender != null && student.getGender() == gender;
        });
        genderChoiceBox.valueProperty().addListener((_, _, _) -> runnable.run());
    }
}
