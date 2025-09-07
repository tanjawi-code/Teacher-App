import java.util.InputMismatchException;
import java.util.Scanner;

enum Gender {male, female}

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

   // The function to print all the details of every student.
    public void studentDetails(){
        System.out.printf("Name : "+studentName+" || ID : "+studentID+" || Age :"+studentAge+" || Point : %.2f || Grades : [exam 1 : %.2f, exam 2 : %.2f, exam 3 : %.2f].\n",studentPoint, studentGrades[0], studentGrades[1], studentGrades[2]);
    }

    // This function is used to change the grades of the student that we take from the function (searchForStudent).
    public void changeStudentGrades() {
        Scanner input = new Scanner(System.in);
        for(int x = 0;  x<3 ; x++){
            double grade;
            System.out.printf("Do you want to change the grade %.2f exam "+(x+1)+"\n",this.studentGrades[x]);
            System.out.print("Choose 1 to put a new grade or 0 to skip this grade : ");
            int choice = input.nextInt();
            if(choice == 1 ){
                System.out.print("Put the new grade of the exam "+(x+1)+" : ");
                grade = input.nextDouble();
                if(grade < 0 || grade > 20){
                    System.out.println("The grade does not contains the conditions.\n");
                    x--;
                }
                else{
                    this.studentGrades[x] = grade;
                    System.out.println();
                }
            }
            else{
                System.out.println();
            }
        }
        double average =0;
        for(Double grade : studentGrades){
            average += grade;
        }
        this.studentPoint = average / this.studentGrades.length;
    }

    // This function is used to change the name of the student that we take from the function (searchForStudent).
    public void changeStudentName(){
        Scanner input = new Scanner(System.in);
        String name;

        while(true){
            System.out.print("Enter the new name of the student "+this.studentName+" : ");
            name = input.nextLine().trim().toLowerCase();
            if(name.equals(this.studentName)){
                System.out.println("The name is already in the list.");
            }
            else if(name.matches("[a-zA-Z]+(\\s[a-zA-Z]+)*")){
                System.out.println("The new name of the student "+this.studentName+" is : "+name);
                this.studentName = name;
                break;
            }
            else{
                System.out.println("Cannot enter symbols or numbers in the name.");
            }
        }
    }

    // This function is used to change the age of the student that we take from the function (searchForStudent).
    public void changeStudentAge(){
        Scanner input = new Scanner(System.in);
        int age;
        while(true){
            try{
                System.out.print("Enter the new age of the student "+this.studentName+" : ");
                age = input.nextInt();
                if(age >= 14 && age <= 20){
                    System.out.println("The new age of the student "+this.studentName+" : "+age);
                    this.studentAge = age;
                    break;
                }
                else{
                    System.out.println("Less than 13 or above 20 can't be in this school.");
                }
            }
            catch(InputMismatchException e){
                System.out.println("Cannot enter letters or symbols.");
                input.nextLine();
            }
        }
    }

    // This function will be used only to print the three top points in the class.
    public void studentInfo(){
        System.out.printf("ID : "+studentID+" || Name : "+studentName+" || Point : %.2f\n",studentPoint );
    }
}
