import java.util.*;

public class Main {
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {

        System.out.println("Hello teacher, we made a big update to the program which it has more things than the old one\n\n ");

        Student student = new Student();
        StudentsManager manager = new StudentsManager();

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
                    9 : Exit.""");
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
                    System.out.println("The choice should be numbers.");
                    input.nextLine();
                }
            }
            switch (choose){
                case 1 : addStudent(student,manager); break;
                case 2 : manager.displayStudentInfo(); break;
                case 3 :
                    searchForStudent(manager);
                    modifyStudent(manager);
                    break;
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
                case 9 : System.out.println("The program is finished."); break;
                default :
                    System.out.println("Wrong choice , try again.\n");
            }

        }while(choose!=9);

        input.close();
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
                return name;
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
                    System.out.println("Less than 13 or above 20 can't be in this school.");
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

    // This function has (addStudentName, addStudentAge, addStudentGrades). It used to take the details of the student.
    public static void addStudent(Student student, StudentsManager manager){
        int count =0;
        int choice;
        do{
            student.setStudentName(addStudentName(count,manager));
            student.setStudentAge(addStudentAge(student.getStudentName()));
            student.setGrades(addStudentGrade(student.getStudentName()));
            student.calculateGrades();
            student.setStudentID(addStudentID());
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
    public static void searchForStudent(StudentsManager manager){
        if(!manager.isEmpty()){
            System.out.print("Enter your student name you want to search : ");
            String name = input.nextLine().trim().toLowerCase();
            boolean isFound = false;

            for(int x = 0 ; x<manager.studentsSize() ; x++){
                if(name.equals(manager.getStudentName(x))){
                    System.out.println("The student "+manager.getStudentName(x)+" is found, The details are : ");
                    manager.OneStudentDetails(x);
                    manager.studentStatistics(x);
                    manager.setStudentPosition(x);
                    System.out.println();
                    isFound = true;
                }
            }
            if(!isFound){
                System.out.println("The student "+name+" is not found in the list.\n");
            }
        }
        else{
            System.out.println("There are no students yet in the claas.\n");
            manager.setStudentPosition(-1);
        }
    }

    // This function is used to change the grades of the student that we take from (searchForStudent).
    public static void changeStudentGrades(StudentsManager manager){
        int index = manager.getStudentPosition();
        for(int x = 0;  x<3 ; x++){
            double grade;
            System.out.printf("Do you want to change the grade %.2f exam "+(x+1)+"\n",manager.getStudentGrades(index,x));
            System.out.print("Choose 1 to put a new grade or any number to skip this grade : ");
            int choice = input.nextInt();
            if(choice == 1 ){
                System.out.print("Put the new grade of the exam "+(x+1)+" : ");
                grade = input.nextDouble();
                if(grade < 0 || grade > 20){
                    System.out.println("The grade does not contains the conditions.\n");
                    x--;
                }
                else{
                    manager.changeGrades(x,grade,index);
                    System.out.println();
                }
            }
            else{
                System.out.println();
            }
        }
        manager.calculateNewGrades(index);
    }

    // This function is used to change the name of the student that we take from (searchForStudent).
    public static void changeStudentName(StudentsManager manager){
        int index = manager.getStudentPosition();
        String name;
        input.nextLine();

        while(true){
            System.out.print("Enter the new name of the student "+manager.getStudentName(index)+" : ");
            name = input.nextLine().trim().toLowerCase();
            if(name.equals(manager.checkName(name))){
                System.out.println("The name is already in the list.");
            }
            else if(name.matches("[a-zA-Z]+(\\s[a-zA-Z]+)*")){
                System.out.println("The new name of the student "+manager.getStudentName(index)+" is : "+name);
                manager.changeName(name,index);
                break;
            }
            else{
                System.out.println("Cannot enter symbols or numbers in the name.");
            }
        }
    }

    // This function is used to change the age of the student that we take from (searchForStudent).
    public static void changeStudentAge(StudentsManager manager){
        int index = manager.getStudentPosition();
        int age;
        while(true){
            try{
                System.out.print("Enter the new age of the student "+manager.getStudentName(index)+" : ");
                age = input.nextInt();
                if(age >= 14 && age <= 20){
                    System.out.println("The new age of the student "+manager.getStudentName(index)+" : "+age);
                    manager.changeAge(age,index);
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

    // This method has the three function (changeStudentGrades, changeStudentName, changeStudentAge).
    public static void modifyStudent(StudentsManager manager){
        if(!manager.isEmpty() && manager.getStudentPosition() != -1){
            System.out.print("Do you want to modify the student's details (yes/no) : ");
            String choose = input.nextLine().trim().toLowerCase();
            if(choose.equals("yes")){
                int choice;
                System.out.println("\nWhat do you want to modify.");
                System.out.println("""
                    1 : Change the grades.
                    2 : Change the name.
                    3 : Change the age.
                    4 : Exit.""");
                do{
                    System.out.print("\nEnter your choice : ");
                    choice = input.nextInt();
                    switch (choice){
                        case 1 : changeStudentGrades(manager); break;
                        case 2 : changeStudentName(manager); break;
                        case 3 : changeStudentAge(manager); break;
                        case 4 : System.out.println("\n");break;
                        default : System.out.println("The choice is incorrect.");
                    }
                } while (choice != 4);
            }
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
            if(name.equals(manager.getStudentName(x))){
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
            else{
                System.out.println();
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
}