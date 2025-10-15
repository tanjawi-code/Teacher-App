import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        StudentsManager manager = new StudentsManager(); // For students.
        TeachersManager teachersManager = new TeachersManager(); // For teachers.

        DataBase dataBase = new DataBase(); // This class is for connecting with SQLite.

        StudentsSQLite studentsSQLite = new StudentsSQLite(dataBase);// This is for SQLite.
        studentsSQLite.createStudentsTable();

        TeachersSQLite teachersSQLite = new TeachersSQLite(dataBase);// This is for SQLite.
        teachersSQLite.createTeachersTable();

        SavedFilesSQLite savedFilesSQLite = new SavedFilesSQLite(dataBase); // This is for saving files.
        savedFilesSQLite.createSavedFilesTeachersTable();
        studentsSQLite.check();

        Login login = new Login(manager,teachersManager,studentsSQLite,teachersSQLite,savedFilesSQLite);

        input.close();
    }
}