import java.util.Random;

enum Gender {male,female}
enum City {Tangier,Tetouan,Al_Hoceima,Rabat,Casablanca,Fes,Chefchaouen,Marrakesh}

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
    private City studentCity;

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
        this.fullName = other.fullName;
        this.studentCity = other.studentCity;
    }

    Student(String firstName, String secondName,int age, double[] Grades,Gender gender, City city, String fullName){
        this.firstStudentName = firstName;
        this.studentAge = age;
        this.studentGrades = Grades;
        this.secondStudentName = secondName;
        this.studentGender = gender;
        this.studentCity = city;
        this.fullName = fullName;
    }
    Student(String firstName,String secondName,int age,Gender gender,int ID,double[] grades,double point,City city,int classNumber,String fullName){
        this.firstStudentName = firstName;
        this.secondStudentName = secondName;
        this.studentAge = age;
        this.studentGender = gender;
        this.studentID = ID;
        this.studentGrades = grades;
        this.studentPoint = point;
        this.studentClassNumber = classNumber;
        this.fullName = fullName;
        this.studentCity = city;
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
    public String getFullName(){
        return this.fullName;
    }
    City getStudentCity() {
        return studentCity;
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
    void setCity(City city){
        this.studentCity = city;
    }

    // Calculating The grades of the students.
    public void calculateGrades(){
        double sum = 0;
        for (double studentGrade : studentGrades) {
            sum += studentGrade;
        }
        this.studentPoint = sum/studentGrades.length;
   }

    // This is the method of the interface DisplayAble.
    @Override
    public String saveFile(){
        return firstStudentName+","+secondStudentName+","+studentAge+","+studentGender+","+studentID+","+
               studentGrades[0]+","+studentGrades[1]+","+studentGrades[2]+","+studentPoint+","+studentCity+","+
               studentClassNumber+"\n";
    }

    // Generate ID.
    int GenerateStudentID(){
        Random random  = new Random();
        this.studentID = random.nextInt(100,1000);
        return this.studentID;
    }
}
