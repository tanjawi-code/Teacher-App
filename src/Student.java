// I will add the gender (male,female).

public class Student{

    private String studentName;
    private int studentAge;
    private double[] studentGrades = new double[3];
    private double studentPoint;
    private int studentID;

    Student(){
        this.studentName = "No name";
        this.studentAge = 0;
        this.studentGrades[0] = 0;
        this.studentGrades[1] = 0;
        this.studentGrades[2] = 0;
        this.studentPoint = 0;
        this.studentID = 0;
    }

    Student(Student other){
        this.studentName = other.studentName;
        this.studentAge = other.studentAge;
        this.studentGrades = other.studentGrades;
        this.studentPoint = other.studentPoint;
        this.studentID = other.studentID;
    }

    // Setters and Getters.
    public void setStudentName(String name){
        this.studentName = name;
    }
    public String getStudentName(){
        return studentName;
    }
    public void setStudentAge(int Age) {
        this.studentAge = Age;
    }
    public void setGrades(double[] grades){
        this.studentGrades = grades;
   }
    public double getStudentGrades(int index){
        return this.studentGrades[index];
    }
    public void setStudentID(int ID){
        this.studentID = ID;
    }
    public int getStudentID() {
        return this.studentID;
    }
    public double getStudentPoint(){
        return this.studentPoint;
    }

    // Calculating The grades of the students.
    public void calculateGrades(){
        double sum = 0;
        for (double studentGrade : studentGrades) {
            sum += studentGrade;
        }
        this.studentPoint = sum/studentGrades.length;
   }

   // This is for setting new grades.
   void setNewGrades(int index , double newGrade){
        this.studentGrades[index] = newGrade;
   }

   // This is for setting new name.
    void setNewName(String newName){
        studentName = newName;
    }

    // This is for setting new age.
    void setNewAge(int newAge){
        studentAge = newAge;
    }

   // The function to print all the details of every student.
    public void studentDetails(){
        System.out.printf("Name : "+studentName+" || ID : "+studentID+" || Age :"+studentAge+" || Point : %.2f || Grades : [exam 1 : %.2f, exam 2 : %.2f, exam 3 : %.2f].\n",studentPoint, studentGrades[0], studentGrades[1], studentGrades[2]);
    }

    // This function will be used only to print the three top points in the class.
    public void studentInfo(){
        System.out.printf("ID : "+studentID+" || Name : "+studentName+" || Point : %.2f\n",studentPoint );
    }
}
