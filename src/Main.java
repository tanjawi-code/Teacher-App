import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);
    static int index ;
    public static void main(String[] args) {

        System.out.print("How many students do you have ? : ");
        int students = input.nextInt();
        input.nextLine();
        float[][] Grades = new float[students][3];
        String[] Names = new String[students];
        int[] Age = new int[students];
        float[] finalPoints = new float[students];

        System.out.println("\n\n");
        studentsNames(Names);
        System.out.println("\n\n");
        studentsAge(Age,Names);
        System.out.println("\n\n");
        studentsGrades(Grades,Names,finalPoints);
        System.out.println("\n\n");

        int choose ;
        do{
            System.out.println("1 : Showing all the details about students.");
            System.out.println("2 : Checking a student and change its point.");
            System.out.println("3 : Showing all the passed student.");
            System.out.println("4 : Showing all the failed student.");
            System.out.println("5 : Exit.");
            System.out.println("Enter your choice : ");
            choose = input.nextInt();
            System.out.println();

            switch (choose){
                case 1 : details(Names,Age,finalPoints); break;
                case 2 : searchForStudent(Names,Grades,finalPoints); changing(Names,Grades,finalPoints); break;
                case 3 : passedStudents(Names,finalPoints); break;
                case 4 : failedStudents(Names,finalPoints); break;
                case 5 : System.out.println("The program is finished."); break;
                default :
                    System.out.println("Wrong choice , try again.");
            }

        }while(choose!=0);

        input.close();
    }

    // This is a function for taking names from the teacher
    public static void studentsNames(String[] names){
        for(int x = 0 ; x<names.length ; x++){
            System.out.print("Enter student name for number "+(x+1)+" : ");
            names[x] = input.nextLine().toLowerCase().trim();
        }
    }

    // This is a function for taking age from the teacher
    public static void studentsAge(int[] age , String[] names){
        for(int y = 0 ; y<age.length ; y++){
            System.out.print("Enter student age for student "+(names[y])+" : ");
            age[y] = input.nextInt();
        }
    }

    // This is a function for taking student's grades from the teacher
    public static void studentsGrades(float[][] grades , String[] names , float[] finalPoint){
        for(int a = 0 ; a<grades.length ; a++){
            for(int b = 0 ; b<grades[a].length ; b++){
                System.out.println("Enter "+names[a]+"'s point for exam "+(b+1)+" : ");
                grades[a][b] = input.nextFloat();
                if(grades[a][b] < 0 || grades[a][b] > 20){
                    System.out.println("Enter a grade between 0 and 20.");
                    b--;
                }
            }
            System.out.println();
        }
        for(int x = 0 ; x<grades.length ; x++){
            float sum = 0;
            for(int y = 0 ; y<grades[x].length ; y++){
                sum += grades[x][y];
            }
            finalPoint[x] = sum/3;
        }
    }

    // This is a function for giving details about every from student
    public static void details(String[] name , int[] age , float[] finalPoint){
        for(int x = 0 ; x<name.length ; x++){
            System.out.printf("Name : "+name[x]+" || Age : "+age[x]+" || Point : %.2f\n ",finalPoint[x]);
        }
    }

    // This is a function for showing passed students
    public static void passedStudents(String[] name , float[] points){
        float passed = 10 , passedStudent = 0;
        for(int a = 0 ; a<points.length ; a++) {
            if (passed <= points[a]) {
                System.out.printf(name[a]+ " : %.2f\n", points[a]);
                passedStudent++;
            }

        }
        if(passedStudent == 0){
            System.out.println("No passed students.");
            System.out.println("\n");
        }
        else{
            System.out.println("\n");
        }
    }

    // This is a function for showing failed students
    public static void failedStudents(String[] name , float[] points){
        float failed = 9.99f , failedStudent = 0;
        for(int a = 0 ; a<points.length ; a++){
            if(failed >= points[a]){
                System.out.printf(name[a]+" : %.2f\n",points[a]);
                failedStudent++;
            }
        }
        if(failedStudent == 0){
            System.out.println("No failed students.");
            System.out.println("\n");
        }
        else{
            System.out.println("\n");
        }
    }

    // This function is about finding a student
    public static void searchForStudent(String[] name , float[][] Grades , float[] point){
        System.out.print("Enter the student you want to change : ");
        input.nextLine();
        String student = input.nextLine().toLowerCase().trim();
        boolean found = false ;

        for(int x = 0 ; x<name.length ; x++){
            if(student.equals(name[x])){
                System.out.println("The student "+name[x]+" is found");
                for(int y= 0 ; y<Grades[x].length ; y++){
                    System.out.printf("Exam : "+(y+1)+" is : %.2f\n",Grades[x][y]);
                }
                System.out.printf("His/Her point is : %.2f\n",point[x]);
                found = true ;
                index = x ;
                System.out.println("\n\n");
            }
        }
        if(!found){
            System.out.println("The student "+student+" is not found.");
            index = -1;
        }
    }

    public static void changing(String[] names , float[][] grades , float[]Point){
        if(index!=-1){
            for(int a = 0 ; a<grades[index].length ; a++){
                float add , subtract ;
                System.out.printf("Do you want to change "+(a+1)+" exam : %.2f\n",grades[index][a]);
                System.out.print("Choose 1 to continue or any number to skip : ");
                int choose = input.nextInt();
                if(choose!=1){
                    continue;
                }
                System.out.print("1 : + || 2 : -");
                System.out.print("Choose : ");
                int operator = input.nextInt();
                if(operator==1){
                    System.out.print("How much do you want to add : ");
                    add = input.nextFloat();
                    if(add == 0 || add > 20) {
                        System.out.print("The number dose not contains the condition.");
                    }
                    else{
                        grades[index][a] += add;
                    }
                }
                else if (operator==2){
                    System.out.print("How much do you want to subtract : ");
                    subtract = input.nextFloat();
                    if(subtract == 0 || subtract > grades[index][a]) {
                        System.out.print("The number dose not contains the condition.");
                    }
                    else{
                        grades[index][a] -= subtract;
                    }
                }
                else{
                    System.out.println("Wrong choice.");
                }
                if(grades[index][a] > 20){
                    grades[index][a] = 20;
                }
                float sum = 0;
                for(int b = 0 ; b<grades[index].length ; b++){
                    sum += grades[index][b];
                }
                Point[index] = sum/grades[index].length ;
                System.out.printf("The new point of the student "+names[index]+" is : %.2f\n",Point[index]);
            }
        }
    }

}