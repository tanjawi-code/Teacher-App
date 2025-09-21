import javax.swing.*;
import java.io.*;
import java.util.*;

public class Main {
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {

        System.out.println("Hello teacher, I am here to inform you that I added more features and good things.\n");

        Student student = new Student();
        StudentsManager manager = new StudentsManager();

        Login login = new Login();

        //takeStudentsFile(manager,student);

        int choose ;
        do{
            System.out.println("""
                    1 : Add new students.
                    2 : Show all the students with their details.
                    3 : Checking a student and change his/her details if you want.
                    4 : Show all the passed students.
                    5 : Show all the failed students.
                    6 : Show Statistics of the class.
                    7 : Remove a student from the class.
                    8 : Show the top three students.
                    9 : Save students' details in a file.
                    10 : Exit.""");
            while(true){
                try{
                    System.out.print("Enter your choice : ");
                    choose = input.nextInt();
                    input.nextLine();
                    if(choose >= 0){
                        System.out.println();
                        break;
                    }
                }
                catch (InputMismatchException e){
                    System.out.println("The choice should be a number.");
                    input.nextLine();
                }
            }
            switch (choose){
                case 1 : addStudent(student,manager); break;
                case 2 : manager.displayStudentInfo(); break;
                case 3 : searchForStudent(manager); break;
                case 4 : passedStudents(manager); break;
                case 5 : failedStudents(manager); break;
                case 6 : statistics(manager); break;
                case 7 :
                    if(!manager.isEmpty()){
                        removeStudent(manager);
                    }
                    else{
                        System.out.println("There are no students yet in the class.\n");
                    }
                    break;
                case 8 : topThreeStudents(manager); break;
                case 9 : saveStudentsFile(manager); break;
                case 10 : System.out.println("The program is finished."); break;
                default :
                    System.out.println("Wrong choice , try again.\n");
            }

        }while(choose!=10);

        input.close();
    }

    // This function is used only with (addStudent).
    static Gender addStudentGender(){
        System.out.println("What is the gender of the employee : (1 : male || 2 : female) ");
        do{
            try{
                System.out.print("Enter the choice number : ");
                int choice = input.nextInt();
                if(choice == 1){
                    input.nextLine();
                    return Gender.male;
                }
                else if(choice == 2){
                    input.nextLine();
                    return Gender.female;
                }
                else{
                    System.out.println("The choice must be only 1 or 2.");
                }
            }
            catch (InputMismatchException e){
                System.out.println("The choice cannot have letters or symbols.");
                input.nextLine();
            }
        } while(true);
    }

    // This function is used only with (addStudent).
    public static String addStudentName(int count, StudentsManager manager){
        String name;
        System.out.println("Enter the details of the student number " + (count + 1) + " : ");
        while(true) {
            System.out.print("The name of the student : ");
            name = input.nextLine().trim().toLowerCase();
            if (name.equals(manager.checkName(name))) {
                System.out.println("The name is already in the list.");
            }
            else if(name.matches("[a-zA-Z]+(\\s[a-zA-Z]+)*")){
                return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
            }
            else {
                System.out.println("The name should only accept letters.");
            }
        }
    }

    // This function is used only with (addStudent).
    public static int addStudentAge(String name){
        int age;
        do{
            try{
                System.out.print("The age for the student " + name+ " : ");
                age = input.nextInt();
                if(age >= 14 && age <= 20){
                    input.nextLine();
                    return age;
                }
                else{
                    System.out.println("Less than 13 or more than 20 can't be in this school.");
                }
            }
            catch(InputMismatchException e){
                System.out.println("The age cannot has letters.");
                input.nextLine();
            }
        } while(true);
    }

    // This function is used only with (addStudent).
    public static double[] addStudentGrade(String name){
        double[] grades = new double[3];
        while(true){
            try{
                for(int b = 0; b <grades.length ; b++){
                    System.out.print(name+"'s grade for exam "+(b+1)+" : ");
                    grades[b] = input.nextDouble();
                    if(grades[b] < 0 || grades[b] > 20){
                        System.out.println("Enter a grade between 0 and 20.");
                        b--;
                    }
                }
                break;
            }
            catch(InputMismatchException e){
                System.out.println("The garde cannot have letters.");
                input.nextLine();
            }
        }
        input.nextLine();
        return grades;
    }

    // This function is used only with (addStudent).
    public static int addStudentID(){
        Random random = new Random();
        return random.nextInt(1000,10000);
    }

    // This function has (addStudentName, addStudentAge, addStudentGrades, addStudentID, and addStudentGender).
    // It used them to take the details of the student.
    public static void addStudent(Student student, StudentsManager manager){
        int count =0;
        int choice;
        do{
            student.setStudentGender(addStudentGender());
            student.setStudentName(addStudentName(count,manager));
            student.setStudentAge(addStudentAge(student.getStudentName()));
            student.setGrades(addStudentGrade(student.getStudentName()));
            student.calculateGrades();
            student.setStudentID(addStudentID());
            student.setStudentClassNumber(count+1);
            manager.saveStudent(student);
            count++;
            do{
                try{
                    System.out.print("\nDo you want to continue adding student (yes : 1 || no : 0) : ");
                    choice = input.nextInt();
                    input.nextLine();
                    if(choice == 1 || choice == 0){
                        break;
                    }
                    else{
                        System.out.println("The choice should only have 1 or 0.");
                    }
                }
                catch (InputMismatchException e){
                    System.out.println("The choice can't have letters or symbols.");
                    input.nextLine();
                }
            } while(true);

            System.out.println();
        } while(choice != 0);
        System.out.println();
    }

    // This function is used to show the passed students.
    public static void passedStudents(StudentsManager manager){
        if(!manager.isEmpty()){
            double passed = 10;
            int passedStudents =0;
            for(int x =0 ; x< manager.studentsSize() ; x++){
                if(passed <= manager.getStudentPoint(x)){
                    System.out.printf(manager.getStudentName(x)+ " : %.2f\n",manager.getStudentPoint(x));
                    passedStudents++;
                }
            }
            if(passedStudents == 0){
                System.out.println("There are no passed students.\n");
            }
            else{
                System.out.println("The number of passed students are : "+passedStudents+"\n");
            }
        }
        else{
            System.out.println("There are no students yet in the class.\n");
        }
    }

    // This function is used to show the failed students.
    public static void failedStudents(StudentsManager manager){
        if(!manager.isEmpty()){
            double failed = 9.99d;
            int failedStudents = 0;
            for(int x = 0; x<manager.studentsSize() ; x++){
                if(failed >= manager.getStudentPoint(x)){
                    System.out.printf(manager.getStudentName(x)+" : %.2f.\n",manager.getStudentPoint(x));
                    failedStudents++;
                }
            }
            if(failedStudents == 0){
                System.out.println("There are no failed students.\n");
            }
            else{
                System.out.println("The number of failed student are  : "+failedStudents+"\n");
            }
        }
        else{
            System.out.println("There are no students yet in the class.\n");
        }
    }

    // This function is used to search for a student and change his/her grades.
    // Search by name, point, age, passed or failed, grade, male or female.
    public static void searchForStudent(StudentsManager manager){
        if(!manager.isEmpty()){
            System.out.print("Enter your student name you want to search : ");
            String name = input.nextLine().trim().toLowerCase();
            boolean isFound = false;

            for(int x = 0 ; x<manager.studentsSize() ; x++){
                if(name.equals(manager.getStudentName(x).toLowerCase().trim())){
                    System.out.println("The student "+manager.getStudentName(x)+" is found, The details are : ");
                    manager.OneStudentDetails(x);
                    manager.studentStatistics(x);
                    System.out.println();
                    manager.modifyStudent(x); // This is the method of modifying the student.
                    isFound = true;
                }
            }
            if(!isFound){
                System.out.println("The student "+name+" is not found in the list.\n");
            }
        }
        else{
            System.out.println("There are no students yet in the claas.\n");
        }
    }

    // This function is used to remove a specific student from the list of the class.
    public static void removeStudent(StudentsManager manager){
        boolean found = false;
        String choice ;
        int studentPosition =0;
        System.out.print("What is the name of the student you want to remove : ");
        String name = input.nextLine().toLowerCase().trim();

        for(int x = 0 ; x<manager.studentsSize() ; x++){
            if(name.equals(manager.getStudentName(x).trim().toLowerCase())){
                System.out.println("The student "+manager.getStudentName(x)+" is found, The details are : ");
                manager.OneStudentDetails(x);
                studentPosition = x;
                System.out.println();
                found = true;
            }
        }
        if(found){
            System.out.print("Do you want to remove the student "+name+" (yes/no) : ");
            choice = input.nextLine().trim().toLowerCase();
            if(choice.equals("yes")){
                System.out.println("You have removed the student "+name+" from the list of the students.\n");
                manager.removeStudent(studentPosition);
            }
            else if(choice.equals("no")){
                System.out.println();
            }
            else{
                System.out.println("Incorrect choice, did not remove the student.\n");
            }
        }

        else{
            System.out.println("The student "+name+" is not in the list of the students.\n");
        }
    }

    // Show The top three students.
    public static void topThreeStudents(StudentsManager manager){
        if(!manager.isEmpty()) {
            int threeStudents = 0;
            List<Student> topThree = manager.showTopThreePoints();
            for (Student student : topThree) {
                if (student.getStudentPoint() >= 10) {
                    student.studentInfo();
                    threeStudents++;
                }
                if (threeStudents == 3) {
                    System.out.println();
                    break;
                }
            }
            if(threeStudents == 0){
                System.out.println("There are no passed students.\n");
            }
            else{
                System.out.println();
            }
        }
        else{
            System.out.println("There are no students yet in the class.\n");
        }
    }

    // Statistics.
    public static void statistics(StudentsManager manager){
        if(!manager.isEmpty()){
            ArrayList<Double> points = new ArrayList<>();
            for(int x = 0 ;x<manager.studentsSize() ; x++){
                points.add(manager.getStudentPoint(x));
            }
            System.out.println("Statistics of the class : ");
            manager.classStatistics(points);
            System.out.println();
        }
        else{
            System.out.println("There are no students yet in the class.\n");
        }
    }

    // This method is for clearing the file if the user wants to start new students.
    public static void clearFile(String path){
        File file = new File(path);

        try(FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write("");
        }
        catch (IOException e){
            System.out.println("Something is wrong.\n");
        }
    }

    // This is for saving the students in a file.
    public static void saveStudentsFile(StudentsManager manager) {
        if(!manager.isEmpty()){
            String filePath = "C:\\Users\\asus\\desktop\\studentsFile.csv";

            try( FileWriter fileWriter = new FileWriter(filePath)){
                for(int x = 0 ; x< manager.studentsSize() ; x++){
                    fileWriter.write(manager.saveFile(x));
                }
                System.out.println("The file has been written.\n");
            }
            catch (FileNotFoundException e){
                System.out.println("Could not find the file location.\n");
            }
            catch (IOException e){
                System.out.println("Something went wrong with file.\n");
            }
        }
        else {
            System.out.println("The are no students yet in the class.\n");
        }
    }

    // This is for using the data from the file that we have saved the students' details in.
    public static void takeStudentsFile(StudentsManager manager,Student student){
        String filePath = "C:\\Users\\asus\\desktop\\studentsFile.csv";
        File file = new File(filePath);
        int answer;
        String[] confirm = {"Yes, Use the old data","No, Make new one(Delete the old)"};

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            answer = JOptionPane.showOptionDialog(null,"Do want to use the previous file",
                    "Students file",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,
                    confirm,0);
            if(answer == 0){
                if(!(file.length() == 0)){
                    System.out.println("The file is exists. You are using the old data.\n");
                    String paragraph;
                    while ((paragraph = bufferedReader.readLine()) != null){
                        double[] grades = new double[3];
                        String[] data = paragraph.split(",");
                        int classNumber = Integer.parseInt(data[0]);
                        int ID = Integer.parseInt(data[1]);
                        String name = data[2];
                        int age = Integer.parseInt(data[3]);
                        Gender gender = Gender.valueOf(data[4]);
                        double grade1 = Double.parseDouble(data[6]);
                        double grade2 = Double.parseDouble(data[7]);
                        double grade3 = Double.parseDouble(data[8]);
                        grades[0] = grade1;
                        grades[1] = grade2;
                        grades[2] = grade3;

                        student.setStudentName(name);
                        student.setStudentAge(age);
                        student.setStudentClassNumber(classNumber);
                        student.setStudentGender(gender);
                        student.setStudentID(ID);
                        student.setGrades(grades);
                        student.calculateGrades();
                        manager.saveStudent(student);
                    }
                }
                else {
                    System.out.println("The file is empty.\n");
                }
            }
            else {
                System.out.println("You deleted the data in the previous file.\n");
                clearFile(filePath);
            }
        }
        catch (FileNotFoundException e){
            System.out.println("File location is not found.\n");
        }
        catch (IOException e){
            System.out.println("Something is wrong.\n");
        }
    }
}