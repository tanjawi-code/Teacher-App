import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);
    static int index ;
    public static void main(String[] args) {

        System.out.print("How many students do you have ? : ");
        int studentsNumber = input.nextInt();
        input.nextLine();

        Student[] students = new Student[studentsNumber];

        System.out.println("\n\n");
        studentsNames(students);
        System.out.println("\n\n");
        studentsAge(students);
        System.out.println("\n\n");
        studentsGrades(students);
        System.out.println("\n\n");

        int choose ;
        do{
            System.out.println("1 : Showing all the details about students.");
            System.out.println("2 : Checking a student and change its point.");
            System.out.println("3 : Showing all the passed student.");
            System.out.println("4 : Showing all the failed student.");
            System.out.println("5 : Exit.");
            System.out.print("Enter your choice : ");
            choose = input.nextInt();
            System.out.println();

            switch (choose){
                case 1 : details(students); break;
                case 2 : searchForStudent(students); changing(students); break;
                case 3 : passedStudents(students); break;
                case 4 : failedStudents(students); break;
                case 5 : System.out.println("The program is finished."); break;
                default :
                    System.out.println("Wrong choice , try again.");
            }

        }while(choose!=0);

        input.close();
    }

    // This is a function for taking names from the teacher
    public static void studentsNames(Student[] students){
        for(int x = 0 ; x<students.length ; x++){
            students[x] = new Student();
            System.out.print("Enter student name for number "+(x+1)+" : ");
            students[x].setStudentName(input.nextLine().toLowerCase().trim());
        }
    }

    // This is a function for taking age from the teacher
    public static void studentsAge(Student[] students){
        for(int y = 0 ; y<students.length ; y++){
            System.out.print("Enter student age for student "+students[y].getStudentName()+" : ");
            students[y].setStudentAge(input.nextInt());
        }
    }

    // This is a function for taking student's grades from the teacher
    public static void studentsGrades(Student[] students){
        for(int a = 0 ; a<students.length ; a++){
            for(int b = 0 ; b<3 ; b++){
                System.out.print("Enter "+students[a].getStudentName()+"'s point for exam "+(b+1)+" : ");
                students[a].setGrades(input.nextDouble(),b);
                if(students[a].getStudentGrades(b) < 0 || students[a].getStudentGrades(b) > 20){
                    System.out.println("Enter a grade between 0 and 20.");
                    b--;
                }
            }
            System.out.println();
        }
        for(int x = 0 ; x<students.length ; x++){
            students[x].calculateGrades();
        }
    }

    // This is a function for giving details about every from student
    public static void details(Student[] students){
        for(int x = 0 ; x<students.length ; x++){
            students[x].studentDetails();
        }
        System.out.println();
    }

    // This is a function for showing passed students
    public static void passedStudents(Student[] students){
        double passed = 10 ;
        int passedStudent = 0;
        for(int a = 0 ; a<students.length ; a++) {
            if (passed <= students[a].getStudentPoint()) {
                System.out.printf(students[a].getStudentName()+ " : %.2f\n", students[a].getStudentPoint());
                passedStudent++;
            }

        }
        if(passedStudent == 0){
            System.out.println("No passed students.");
            System.out.println();
        }
        else{
            System.out.println();
        }
    }

    // This is a function for showing failed students
    public static void failedStudents(Student[] students){
        double failed = 9.99f ;
        int failedStudent = 0;
        for(int a = 0 ; a<students.length ; a++){
            if(failed >= students[a].getStudentPoint()){
                System.out.printf(students[a].getStudentName()+" : %.2f\n",students[a].getStudentPoint());
                failedStudent++;
            }
        }
        if(failedStudent == 0){
            System.out.println("No failed students.");
            System.out.println();
        }
        else{
            System.out.println();
        }
    }

    // This function is about finding a student
    public static void searchForStudent(Student[] students){
        System.out.print("Enter the student name you want to change : ");
        input.nextLine();
        String student = input.nextLine().toLowerCase().trim();
        boolean found = false ;

        for(int x = 0 ; x<students.length ; x++){
            if(student.equals(students[x].getStudentName())){
                System.out.println("The student "+students[x].getStudentName()+" is found");
                for(int y= 0 ; y<3 ; y++){
                    System.out.printf("Exam : "+(y+1)+" is : %.2f\n",students[x].getStudentGrades(y));
                }
                System.out.printf("His/Her point is : %.2f\n",students[x].getStudentPoint());
                found = true ;
                index = x;
                System.out.println("\n\n");
            }
        }
        if(!found){
            System.out.println("The student "+student+" is not found.");
            index = -1;
        }
    }

    public static void changing(Student[] students){
        if(index!=-1){
            for(int a =0 ; a<3; a++){
                double add , subtract ;
                System.out.printf("Do you want to change "+(a+1)+" exam : %.2f\n",students[index].getStudentGrades(a));
                System.out.print("Choose 1 to continue or any number to skip and see the next grade : ");
                int choose = input.nextInt();
                if(choose!=1){
                    continue;
                }
                System.out.println("1 : + || 2 : -");
                System.out.print("Choose : ");
                int operator = input.nextInt();
                if(operator==1){
                    System.out.print("How much do you want to add : ");
                    add = input.nextDouble();
                    if(add == 0 || add > 20) {
                        System.out.print("The number dose not contains the condition.");
                    }
                    else{
                        students[index].addToTheGrade(a,add);
                    }
                }
                else if (operator==2){
                    System.out.print("How much do you want to subtract : ");
                    subtract = input.nextFloat();
                    if(subtract == 0 || subtract > students[index].getStudentGrades(a)) {
                        System.out.print("The number dose not contains the condition.");
                    }
                    else{
                        students[index].subtractFromTheGrade(a,subtract);
                    }
                }
                else{
                    System.out.println("Wrong choice.");
                }

                students[index].calculateGrades();

                System.out.printf("The new point of the student "+students[index].getStudentName()+
                        " is : %.2f\n",students[index].getStudentPoint());
                System.out.println();
            }
        }
    }

}