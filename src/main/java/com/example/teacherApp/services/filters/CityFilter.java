package com.example.teacherApp.services.filters;

import com.example.teacherApp.Enums.City;
import com.example.teacherApp.interfaces.FilterData;
import com.example.teacherApp.models.Student;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ChoiceBox;

public class CityFilter implements FilterData {

    ChoiceBox<City>  cityChoiceBox;

    public CityFilter(ChoiceBox<City> cityChoiceBox) {
        this.cityChoiceBox = cityChoiceBox;
    }

    @Override
    public void filter(FilteredList<Student> filteredList) {
        Runnable runnable = () -> filteredList.setPredicate(student -> {
            City city = cityChoiceBox.getValue();
            return city != null && student.getCity() == city;
        });
        cityChoiceBox.valueProperty().addListener((_, _, _) -> runnable.run());

    }
}
