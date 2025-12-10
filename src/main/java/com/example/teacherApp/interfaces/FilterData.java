package com.example.teacherApp.interfaces;

import com.example.teacherApp.models.Student;
import javafx.collections.transformation.FilteredList;

public interface FilterData {

    void filter(FilteredList<Student> filteredList);
}
