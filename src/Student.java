public class Student{

    private String studentName;
    private int studentAge;
    private double[] studentGrades = new double[3];
    private double studentPoint;

    Student(){
        this.studentName = "No name";
        this.studentAge = 0;
        this.studentGrades[0] = 0;
        this.studentGrades[1] = 0;
        this.studentGrades[2] = 0;
        this.studentPoint = 0;
    }

    Student(Student other){
        this.studentName = other.studentName;
        this.studentAge = other.studentAge;
        this.studentGrades = other.studentGrades;
        this.studentPoint = other.studentPoint;
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
    public double getStudentPoint(){
        return this.studentPoint;
    }

    // Calculating The grades of the students.
    public void calculateGrades(){
        double sum = 0;
        for(int x = 0 ; x<studentGrades.length ; x++){
            sum += studentGrades[x];
        }
        this.studentPoint = sum/studentGrades.length;
   }

   void setNewGrades(int index , double newGrade){
        this.studentGrades[index] = newGrade;
   }

    public void studentDetails(){
        System.out.printf("Name : "+studentName+" || Age : "+studentAge+" || Point : %.2f || Grades : [exam 1 : %.2f, exam 2 : %.2f, exam 3 : %.2f].\n",studentPoint, studentGrades[0], studentGrades[1], studentGrades[2]);
    }
}
