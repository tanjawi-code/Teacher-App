import javax.swing.*;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        StudentsManager manager = new StudentsManager(); // For students.
        TeachersManager teachersManager = new TeachersManager(); // For teachers.
        StudentsSQLite studentsSQLite = new StudentsSQLite();// This is for SQLite.
        studentsSQLite.createStudentsTable();
        TeachersSQLite teachersSQLite = new TeachersSQLite();// This is for SQLite.
        teachersSQLite.createTeachersTable();

        Login login = new Login(manager,teachersManager,studentsSQLite,teachersSQLite);

        input.close();
    }
}