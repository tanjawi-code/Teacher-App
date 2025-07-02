public class Student{

    private String studentName;
    private int studentAge;
    private double[] studentGrades = new double[3];
    private double studentPoint;

    public void setStudentName(String name){
        this.studentName = name;
    }
    public String getStudentName(){
        return studentName;
    }
    public void setStudentAge(int Age) {
        this.studentAge = Age;
    }
    public void setGrades(double grade , int index){
        this.studentGrades[index] = grade;
   }

    public void calculateGrades(){
        double sum = 0;
        for(int x = 0 ; x<studentGrades.length ; x++){
            sum += studentGrades[x];
        }
        this.studentPoint = sum/studentGrades.length;
   }
    public double getStudentGrades(int index){
        return this.studentGrades[index];
   }
    public void addToTheGrade(int index , double grade){
        this.studentGrades[index] = this.studentGrades[index] + grade ;
        if(this.studentGrades[index] > 20){
            this.studentGrades[index] = 20;
        }
   }
    public void subtractFromTheGrade(int index , double grade){
        this.studentGrades[index] = this.studentGrades[index] - grade ;
    }

    public double getStudentPoint(){
        return studentPoint;
    }

    public void studentDetails(){
        System.out.printf("Name : "+studentName+" || Age : "+studentAge+" || Point : %.2f\n ", studentPoint);
    }
}
