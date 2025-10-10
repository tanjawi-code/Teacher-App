import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class TeachersManager {

    ArrayList<Teacher> teachers = new ArrayList<>();

    // Getters.
    String getTeacherName(){
        return teachers.getFirst().getName();
    }
    int getTeacherAge(){
        return teachers.getFirst().getAge();
    }
    String getTeacherPassword(){
        return teachers.getFirst().getPassword();
    }
    teacherGender getTeacherGender(){
        return teachers.getFirst().getGender();
    }
    schools getTeacherSchool(){
        return teachers.getFirst().getSchool();
    }
    subjects getTeacherSubject(){
        return teachers.getFirst().getSubject();
    }

    // Saving a teacher.
    void saveTeacher(Teacher teacher){
        teachers.add(new Teacher(teacher));
    }

    int teachersSize(){
        return teachers.size();
    }

    boolean isEmpty(){
        return teachers.isEmpty();
    }

    void removeTeacher(int index){
        teachers.remove(index);
    }
}
