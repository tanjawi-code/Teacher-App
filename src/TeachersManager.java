import java.util.ArrayList;

public class TeachersManager {

    ArrayList<Teacher> teachers = new ArrayList<>();

    // Getters.
    String getTeacherName(int index){
        return teachers.get(index).getName();
    }
    int getTeacherAge(int index){
        return teachers.get(index).getAge();
    }
    String password(int index){
        return teachers.get(index).getPassword();
    }
    teacherGender getTeacherGender(int index){
        return teachers.get(index).getGender();
    }
    schools getTeacherSchool(int index){
        return teachers.get(index).getSchool();
    }
    subjects getTeacherSubject(int index){
        return teachers.get(index).getSubject();
    }

    // Saving a teacher.
    void saveTeacher(Teacher teacher){
        teachers.add(new Teacher(teacher));
    }
}
