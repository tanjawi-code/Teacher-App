import javax.swing.*;
import java.io.*;
import java.util.*;

public class Main {
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {

        StudentsManager manager = new StudentsManager();

        //Login login = new Login();
        MainWindow mainWindow = new MainWindow(manager);

        /*
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
                //case 1 : addStudent(student,manager); break;
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

         */

        input.close();
    }

    /*

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
         */
}