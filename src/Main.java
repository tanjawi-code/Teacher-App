import javax.swing.*;
import java.io.*;
import java.util.*;

public class Main {
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {

        StudentsManager manager = new StudentsManager(); // For students.
        TeachersManager teachersManager = new TeachersManager(); // For teachers.

        Login login = new Login(manager,teachersManager);

        input.close();
    }
}