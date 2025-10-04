import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StudentsManager {

    private final ArrayList<Student> students = new ArrayList<>();

    // Getters.
    String getFirstStudentName(int index){
        return students.get(index).getFirstStudentName();
    }
    String getSecondStudentName(int index){
        return students.get(index).getSecondStudentName();
    }
    String getStudentFullName(int index){
        return students.get(index).getFullName();
    }
    String saveFile(int index){
        return students.get(index).saveFile();
    }

    // Setters for updating.
    void setStudentFirstName(int index,String name){
        students.get(index).setFirstName(name);
    }
    void setStudentSecondName(int index,String name){
        students.get(index).setSecondName(name);
    }
    void setStudentAge(int index,int age){
        students.get(index).setAge(age);
    }
    void setStudentGender(int index,Gender gender){
        students.get(index).setGender(gender);
    }
    void setStudentAddress(int index,City city){
        students.get(index).setCity(city);
    }
    void setStudentGrades(int index,double[] grades){
        students.get(index).setGrades(grades);
    }

    // Saving a student.
    void saveStudent(Student s){
        students.add(new Student(s));
    }

    // The size of the students.
    int studentsSize(){
        return students.size();
    }

    // Checking if the list is empty.
    boolean isEmpty(){
        return students.isEmpty();
    }

    // Show the top three student points from top to bottoms.
    ArrayList<Student> showTopThreePoints(){
        final ArrayList<Student> sortedStudent = new ArrayList<>(students);
        sortedStudent.sort(Comparator.comparingDouble(Student::getStudentPoint).reversed());
        return sortedStudent;
    }

    // Removing a student.
    void removeStudent(int index){
        students.remove(index);
    }

    // Class statistics.

    // Number of passed students.
    int getNumberOfPassedStudents(){
        int passed = 0;
        for (Student student : students) {
            if (student.getStudentPoint() >= 10) {
                passed++;
            }
        }
        return passed;
    }
    // Number of failed students.
    int getNumberOfFailedStudents(){
        int failed = 0;
        for(Student student : students){
            if(student.getStudentPoint() <= 9.99){
                failed++;
            }
        }
        return failed;
    }
    // Number of males.
    int getMalesNumber(){
        int males = 0;
        for(Student student : students){
            if(student.getStudentGender() == Gender.male){
                males++;
            }
        }
        return males;
    }
    // Number of females.
    int getFemalesNumber(){
        int females = 0;
        for(Student student : students){
            if(student.getStudentGender() == Gender.female){
                females++;
            }
        }
        return females;
    }
    // The general average of the class.
    double getClassAverage(){
        double average = 0;
        for(Student student : students){
            average += student.getStudentPoint();
        }
        average = average / students.size();

        return average;
    }
    // The succuss rate.
    String getSuccussRate(){
        double percentage = (getNumberOfPassedStudents() * 100.0) / students.size();

        DecimalFormat decimalFormat = new DecimalFormat("#");
        decimalFormat.format(percentage);
        String value = String.valueOf(decimalFormat.format(Double.parseDouble(String.valueOf(percentage))));
        value = value+"%";

        return value;
    }
    // The highest point.
    double getHighestPoint(){
        ArrayList<Double> points = new ArrayList<>();
        for(Student student : students){
            points.add(student.getStudentPoint());
        }
        return Collections.max(points);
    }
    // The lowest point.
    double getLowestPoint(){
        ArrayList<Double> points = new ArrayList<>();
        for(Student student : students){
            points.add(student.getStudentPoint());
        }
        return Collections.min(points);
    }

    // Student statistics.

    // The highest garde of the student.
    double getHighestGrade(int studentIndex){
        ArrayList<Double> grades = new ArrayList<>();
        for(int x =0 ; x<3; x++){
            grades.add(students.get(studentIndex).getStudentGrades(x));
        }
        return Collections.max(grades);
    }
    // The lowest garde of the student.
    double getLowestGrade(int studentIndex){
        ArrayList<Double> grades = new ArrayList<>();
        for(int x =0 ; x<3; x++){
            grades.add(students.get(studentIndex).getStudentGrades(x));
        }
        return Collections.min(grades);
    }
    // The lowest garde of the student.
    double getGradesAverage(int studentIndex){
        double average = 0;
        for(int x =0 ; x<3; x++){
            average = students.get(studentIndex).getStudentGrades(x);
        }

        average = average / 3;

        return average;
    }
    // The number of grades that less than 10.
    int getGradesLessTen(int studentIndex){
        int count = 0;
        for(int x = 0 ;x<3; x++){
            if(students.get(studentIndex).getStudentGrades(x) <= 9.99){
                count++;
            }
        }
        return count;
    }
    // The rate of the student.
    String getStudentRate(int studentIndex){
        String rate;
        if(students.get(studentIndex).getStudentPoint() >= 18){
            rate = "Excellent";
        }
        else if(students.get(studentIndex).getStudentPoint() >= 14){
            rate = "Very good";
        }
        else if(students.get(studentIndex).getStudentPoint() >= 10){
            rate = "Good";
        }
        else{
            rate = "Work harder";
        }
        return rate;
    }

    // Check the name if it's not repeated.
    String checkName(String name){
        for (Student student : students) {
            if (student.getFullName().toLowerCase().contains(name.trim())) {
                return name;
            }
        }
        return "";
    }

    // This is for increasing the class number.
    int increaseClassNumber(int count){
        for(int x = 0; x<students.size(); x++){
            count = x;
        }
        return  count + 1;
    }
}
