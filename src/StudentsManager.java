import java.util.ArrayList;

public class StudentsManager {

    private ArrayList<Student> students = new ArrayList<>();
    private Student student = new Student();
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
        student = new Student();
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

    // Changing grades and calculating them.
    void changeGrades(int index ,double newGrade){
        students.get(this.studentPosition).setNewGrades(index,newGrade);
    }
    void calculateNewGrades(){
        students.get(this.studentPosition).calculateGrades();
    }

    // Removing a student.
    void removeStudent(int index){
        students.remove(index);
    }

    void displayStudentInfo(){
        for(Student Students : students){
            Students.studentDetails();
        }
        System.out.println();
    }

}
