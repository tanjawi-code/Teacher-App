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
    void changeGrades(int index ,double newGrade, int studentIndex){
        students.get(studentIndex).setNewGrades(index,newGrade);
    }
    void calculateNewGrades(int index){
        students.get(index).calculateGrades();
    }

    // This is for changing the name and age of one student.
    void changeName(String name,int index){
        students.get(index).setNewName(name);
    }
    void changeAge(int age, int index){
        students.get(index).setNewAge(age);
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

    // Check the name if it's not repeated.
    String checkName(String name){
        for (Student student : students) {
            if (name.equals(student.getStudentName())) {
                return student.getStudentName();
            }
        }
        return "";
    }

    // This method is used to show more Statistics about one student. It's used in the function (searchForStudent).
    void studentStatistics(int studentIndex){
        ArrayList<Double> grades = new ArrayList<>();

        System.out.println("\nThe student statistics.\n");

        for(int x = 0; x<3; x++){
            grades.add(students.get(studentIndex).getStudentGrades(x));
        }
        System.out.printf("The highest grade of the student is %.2f\n",Collections.max(grades));
        System.out.printf("The lowest grade of the student is %.2f\n",Collections.min(grades));

        double average =0;
        for(Double grade : grades){
            average += grade;
        }
        average = average/grades.size();
        System.out.printf("The average of the grades is %.2f\n",average);

        int belowTen =0;
        for(Double grade : grades){
            if(grade < 10){
                belowTen++;
            }
        }
        System.out.println("The number of grades below 10 is "+belowTen);

        System.out.print("The rate of "+students.get(studentIndex).getStudentName()+"'s point : ");
        if(students.get(studentIndex).getStudentPoint() >= 18){
            System.out.println("Excellent.");
        }
        else if(students.get(studentIndex).getStudentPoint() >= 14){
            System.out.println("Very good.");
        }
        else if(students.get(studentIndex).getStudentPoint() >= 10){
            System.out.println("Good.");
        }
        else{
            System.out.println("Work harder next time.");
        }
    }

    void displayStudentInfo(){
        for(Student Students : students){
            Students.studentDetails();
        }
        System.out.println();
    }

}
