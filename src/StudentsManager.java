import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StudentsManager {

    private final ArrayList<Student> students = new ArrayList<>();
    private int studentPosition;

    // Getters.
    String getStudentName(int index){
        return students.get(index).getStudentName();
    }
    double getStudentGrades(int index, int grade){
        return students.get(index).getStudentGrades(grade);
    }
    double getStudentPoint(int index){
        return students.get(index).getStudentPoint();
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

    // Returning the details of one student.
    void OneStudentDetails(int index){
        students.get(index).studentDetails();
    }

    // Set and Get the position of one student.
    void setStudentPosition(int index){
        this.studentPosition = index;
    }
    int getStudentPosition(){
        return this.studentPosition;
    }

    // Changing grades and calculating new grades.
    void changeGrades(int index ,double newGrade){
        students.get(this.studentPosition).setNewGrades(index,newGrade);
    }
    void calculateNewGrades(){
        students.get(this.studentPosition).calculateGrades();
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

    // Statistics
    void classStatistics(ArrayList<Double> points){
        int passedStudents = 0, failedStudents = 0;
        double average = 0;
        System.out.println("The subject : Math.");
        System.out.println("The number of students are : "+students.size());
        for (Student value : students) {
            if (value.getStudentPoint() >= 10) {
                passedStudents++;
            }
            else {
                failedStudents++;
            }
        }
        System.out.println("The number of passed students are : "+passedStudents);
        System.out.println("The number of failed students are : "+failedStudents);

        for(Student point : students){
            average += point.getStudentPoint();
        }
        average = average/students.size();
        System.out.printf("The general average of the class is : %.2f\n",average);

        double percentage = (passedStudents * 100.0) / students.size();
        System.out.printf("The success rate is : %.0f%%\n",percentage);

        double max = Collections.max(points);
        double min = Collections.min(points);
        System.out.printf("The highest point in the class is : %.2f\n",max);
        System.out.printf("The lowest point in the class is : %.2f\n",min);
    }

    void displayStudentInfo(){
        for(Student Students : students){
            Students.studentDetails();
        }
        System.out.println();
    }

}
