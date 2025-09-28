import java.util.Random;

enum Gender {male,female}

public class Student implements SaveAble{

    private String firstStudentName;
    private String secondStudentName;
    private String fullName;
    private int studentAge;
    private double[] studentGrades = new double[3];
    private double studentPoint;
    private int studentID;
    private Gender studentGender;
    private int studentClassNumber;
    private String studentAddress;

    Student(){
        this.firstStudentName = "No name";
        this.studentAge = 0;
        this.studentGrades[0] = 0;
        this.studentGrades[1] = 0;
        this.studentGrades[2] = 0;
        this.studentPoint = 0;
        this.studentID = 0;
        this.studentClassNumber = 0;
    }

    Student(Student other){
        this.firstStudentName = other.firstStudentName;
        this.studentAge = other.studentAge;
        this.studentGrades = other.studentGrades;
        this.studentPoint = other.studentPoint;
        this.studentID = other.studentID;
        this.studentGender = other.studentGender;
        this.secondStudentName = other.secondStudentName;
        this.studentClassNumber = other.studentClassNumber;
        this.studentAddress = other.studentAddress;
        this.fullName = other.fullName;
    }

    Student(String firstName, String secondName,int age, double[] Grades,Gender gender, String address, String fullName){
        this.firstStudentName = firstName;
        this.studentAge = age;
        this.studentGrades = Grades;
        this.secondStudentName = secondName;
        this.studentGender = gender;
        this.studentAddress = address;
        this.fullName = fullName;
    }

    // Setters and Getters.
    public String getFirstStudentName(){
        return firstStudentName;
    }
    public String getSecondStudentName(){
        return this.secondStudentName;
    }
    public int getStudentAge(){
        return this.studentAge;
    }
    public double getStudentGrades(int index){
        return this.studentGrades[index];
    }
    public int getStudentID() {
        return this.studentID;
    }
    Gender getStudentGender(){
        return this.studentGender;
    }
    public int getStudentClassNumber(){
        return this.studentClassNumber;
    }
    public double getStudentPoint(){
        return this.studentPoint;
    }
    public String getStudentAddress(){
        return this.studentAddress;
    }
    public String getFullName(){
        return this.fullName;
    }
    public void setStudentClassNumber(int classNumber){
        this.studentClassNumber = classNumber;
    }

    // Setters for updating data.
    public void setFirstName(String name){
        this.firstStudentName = name;
    }
    public void setSecondName(String name){
        this.secondStudentName = name;
    }
    public void setAge(int age){
        this.studentAge = age;
    }
    public void setGrades(double[] grades){
        this.studentGrades = grades;
    }
    void setGender(Gender gender){
        this.studentGender = gender;
    }
    public void setAddress(String address){
        this.studentAddress = address;
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
        System.out.println("Name : "+this.firstStudentName+" || Gender : "+this.studentGender+" || ID : "+this.studentID+" || Class number : "+this.studentClassNumber);
        System.out.printf("Point : %.2f || Grades : [exam 1 : %.2f, exam 2 : %.2f, exam 3 : %.2f].\n",studentPoint, studentGrades[0], studentGrades[1], studentGrades[2]);
    }

    // This function will be used only to print the three top points in the class.
    public void studentInfo(){
        System.out.printf("ID : "+studentID+" || Class number : "+this.studentClassNumber+" || Gender : "+this.studentGender +" || Name : "+firstStudentName+" || Point : %.2f\n",studentPoint );
    }

    // This is the method of the interface DisplayAble.
    @Override
    public String saveFile(){
        return studentClassNumber+","+studentID+","+firstStudentName+","+studentAge+","+studentGender+","+studentPoint
                +","+studentGrades[0]+","+studentGrades[1]+","+studentGrades[2]+"\n";
    }

    // Generate ID.
    int GenerateStudentID(){
        Random random  = new Random();
        this.studentID = random.nextInt(100,1000);
        return this.studentID;
    }
}
