package com.example.teacherApp.controllers;

import com.example.teacherApp.models.Settings;
import com.example.teacherApp.models.Student;
import com.example.teacherApp.services.FilesManager;
import com.example.teacherApp.services.SettingsManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import com.example.teacherApp.Enums.City;
import com.example.teacherApp.Enums.Gender;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.example.teacherApp.models.Teacher;
import com.example.teacherApp.services.StudentsManager;
import com.example.teacherApp.services.TeachersManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Home implements Initializable {

    private StudentsManager studentsManager;
    private TeachersManager teachersManager;
    private FilesManager filesManager;
    private SettingsManager settingsManager;
    private Teacher teacher;
    private Settings settings;

    @FXML private Button editButton, deleteButton;
    @FXML private BorderPane borderPane;
    @FXML private AnchorPane leftAnchorPane, topAnchorPane, centerAnchorPane;
    @FXML private TableView<Student> tableView;
    @FXML private TableColumn<Student, String> firstNameColumn, lastNameColumn;
    @FXML private TableColumn<Student, Integer> ageColumn, classNumberColumn, studentIDColumn;
    @FXML private TableColumn<Student, Double> firstGradeColumn, secondGradeColumn, thirdGradeColumn, pointColumn;
    @FXML private TableColumn<Student, Gender> genderColumn;
    @FXML private TableColumn<Student, City> cityColumn;
    @FXML private TextField searchNameField;

    private final ObservableList<Student> students = FXCollections.observableArrayList();
    FilteredList<Student> filteredList = new FilteredList<>(students, _ -> true);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("Age"));
        studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        firstGradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade_1"));
        secondGradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade_2"));
        thirdGradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade_3"));
        pointColumn.setCellValueFactory(new PropertyValueFactory<>("point"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        classNumberColumn.setCellValueFactory(new PropertyValueFactory<>("class_number"));

        searchNameFilter();
    }

    // This is for searching for the student name in the table directory using name field.
    private void searchNameFilter() {
        searchNameField.textProperty().addListener((_, _, newValue) ->
                filteredList.setPredicate(student -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    else {
                        String studentName = newValue.toLowerCase();

                        if (student.getFirst_name().toLowerCase().contains(studentName)) {
                            return true;
                        }
                        else return student.getLast_name().toLowerCase().contains(studentName);
                    }
                }));
        SortedList<Student> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }

    @FXML // This is for refreshing the table of the students.
    private void refreshTable() {
        searchNameField.clear();
        filteredList.setPredicate(_ -> true);
    }

    @FXML // This is for deleting and confirming the deletion.
    private void deleteStudentColumn() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (students.isEmpty()) {
            alert.setTitle("Students table");
            alert.setHeaderText("The students table is till empty");
            alert.setContentText("Add students, so you can remove.");
            alert.showAndWait();
        }
        else {
            if (tableView.getSelectionModel().getSelectedIndex() != -1) {
                confirmDeletingStudent(alert);
            }
            else {
                alert.setTitle("Delete");
                alert.setHeaderText("Select a student from the table to remove");
                alert.setContentText("To delete a student, select a student on the table to delete.");
                alert.showAndWait();
            }
        }
    }
    private void confirmDeletingStudent(Alert alert) {
        String fullName = students.get(tableView.getSelectionModel().getSelectedIndex()).getFull_name();
        int id = students.get(tableView.getSelectionModel().getSelectedIndex()).getID();
        int studentDataBaseID = studentsManager.studentDataBaseId(fullName,id);

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete");
        confirmAlert.setHeaderText("Are you sure you want to remove the student "+fullName+"?");
        confirmAlert.setContentText("Removing this student will remove the student from the program.");

        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (studentDataBaseID != -1) {
                if (studentsManager.deleteStudent(studentDataBaseID)) {
                    students.remove(tableView.getSelectionModel().getSelectedIndex());
                    alert.setTitle("Delete");
                    alert.setHeaderText("The student is removed");
                    alert.setContentText("You have removed the student "+fullName+".");
                    alert.showAndWait();
                }
            }
        }
        else if (result.isPresent() && result.get() == ButtonType.CANCEL){
            confirmAlert.close();
        }
    }

    @FXML // This is for getting the name of the student to edit the student.
    private void editStudent() throws IOException {
        String name = getStudentNameFromUser();

        if (name != null && !name.isEmpty()) {
            Student target = getStudentIsInTable(name);
            if (target != null) {
                String page = "EditStudentScene";
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/scenes/"+page+".fxml")));
                Parent parent = loader.load();
                EditStudentController editStudentController = loader.getController();

                editStudentController.setStudent(target);
                editStudentController.setStudentsManager(studentsManager);
                editStudentController.setObservableList(students);
                editStudentController.setTableView(tableView);
                editStudentController.setHomePage(centerAnchorPane,borderPane);
                borderPane.setCenter(parent);
            }
            else {
                new Alert(Alert.AlertType.INFORMATION,"The student "+name+" is not found in the table",
                        ButtonType.OK).showAndWait();
            }
        }
    }

    @FXML // This is for going to filter page.
    private void filterPage() throws IOException {
        String page = "FilterAndSortingScene";
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/scenes/"+page+".fxml")));
        Parent parent = loader.load();
        FilterAndSortingController filterAndSortingController = loader.getController();

        filterAndSortingController.setLeftAnchorPane(leftAnchorPane);
        filterAndSortingController.setBorderPane(borderPane);
        filterAndSortingController.setFilteredList(filteredList);
        filterAndSortingController.setDisabledButtons(editButton,deleteButton);

        borderPane.setLeft(parent);
    }

    @FXML // This is for showing the table of students.
    private void showTable() {
        borderPane.setCenter(centerAnchorPane);
    }

    @FXML // This is for going to the page where the user adds a student.
    private void addStudent() throws IOException {
        String page = "AddStudentScene";
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/scenes/"+page+".fxml")));
        Parent parent = loader.load();
        AddStudentsController studentsController = loader.getController();

        studentsController.setTeachersManager(teachersManager);
        studentsController.setStudentsManager(studentsManager);
        studentsController.setObservableList(students);

        borderPane.setCenter(parent);
    }

    @FXML // This is for showing all the information and statistics of specific student.
    private void studentStatistics() throws IOException {
        String name = getStudentNameFromUser();

        if (name != null && !name.isEmpty()) {
            Student target = getStudentIsInTable(name);
            if (target != null) {
                String page = "StudentStatisticsScene";
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/scenes/"+page+".fxml")));
                Parent parent = loader.load();
                StudentStatisticsController studentStatisticsController = loader.getController();

                studentStatisticsController.setStudent(target);

                borderPane.setCenter(parent);
            }
            else {
                new Alert(Alert.AlertType.INFORMATION,"The student "+name+" is not found in the table",
                        ButtonType.OK).showAndWait();
            }
        }
    }

    @FXML // This is for showing all the information and statistics of the class.
    private void classStatistics() throws IOException {
        if (students.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Students table");
            alert.setHeaderText("The students table is empty.");
            alert.setContentText("There are no students in the table.");
            alert.showAndWait();
        }
        else {
            String page = "ClassStatisticsScene";
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/scenes/"+page+".fxml")));
            Parent parent = loader.load();
            ClassStatisticsController classStatisticsController = loader.getController();
            classStatisticsController.setStudentsManager(teacher, studentsManager, students);
            borderPane.setCenter(parent);
        }
    }

    @FXML // This is for going to the page where the user adds another class.
    private void addClass() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Adding class");
        alert.setHeaderText("Adding a new class is not available yet.");
        alert.setContentText("We are going to add this feature in the future soon.");
        alert.showAndWait();
    }

    @FXML // This is for changing the class.
    private void changeClass() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Changing class");
        alert.setHeaderText("Changing a class is not available yet.");
        alert.setContentText("We are going to add this feature in the future soon.");
        alert.showAndWait();
    }

    @FXML // This is for opening the user's profile.
    private void openUserProfile() throws IOException {
        String page = "UserAccountScene";
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/scenes/"+page+".fxml")));
        Parent parent = loader.load();
        UserAccountController userAccountController = loader.getController();

        userAccountController.setTeacher(teacher);
        userAccountController.setBorderPane(borderPane);
        userAccountController.setTeachersManager(teachersManager);
        borderPane.setCenter(parent);
    }

    @FXML // This is for changing the color of the program.
    private void changeTheme() throws IOException {
        String page = "ChangeBackgroundColorScene";
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getResource("/scenes/"+page+".fxml")));
        DialogPane dialogPane = loader.load();

        ChangeBackgroundColorController backgroundColorController = loader.getController();
        backgroundColorController.setDialogPane(settingsManager, teachersManager.getUserID(), dialogPane,
                                                borderPane, leftAnchorPane, settings);
    }

    @FXML // This is for deleting all the students.
    private void deleteAllStudents() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Delete all students");
        textInputDialog.setHeaderText("Confirm yourself");
        textInputDialog.setContentText("Your password: ");
        Optional<String> result = textInputDialog.showAndWait();

        if (result.isPresent()) {
            if (!result.get().isEmpty()) {
                if (teachersManager.selectTeacher(teacher.getName(), result.get())) {
                    confirmDeleteAllStudents();
                }
                else {
                    new Alert(Alert.AlertType.INFORMATION,"The password is incorrect",ButtonType.NO).showAndWait();
                }
            }
        }
    }
    private void confirmDeleteAllStudents() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting all students");
        alert.setHeaderText("Are you sure you want to remove all students?");
        String message = """
                You will lose all the students you have, please confirm that you're agreeing to
                delete all of your students, once you click ok, you can not bring them again.""";
        Label label = new Label(message);
        label.setWrapText(true);
        alert.getDialogPane().setContent(label);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            studentsManager.deleteAllStudentsFromList();
            students.clear();
        }
    }

    @FXML // This is for changing the language of the program.
    private void changeLanguage() {
        new Alert(Alert.AlertType.INFORMATION, "Not available yet",ButtonType.OK).showAndWait();
    }

    @FXML // This is for saving a file.
    private void saveFile() throws IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            if (!students.isEmpty()) {
                SaveFile saveFile = new SaveFile(filesManager, students);
                saveFile.saveStudentsFile(file, teachersManager.getUserID());
            }
            else {
                new Alert(Alert.AlertType.INFORMATION,"Cannot save, because there are not students",
                          ButtonType.OK).showAndWait();
            }
        }
    }

    @FXML // This is for opening a file.
    private void openFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            String filePath = file.getPath();
            if (filePath.equals(filesManager.userFilePath(teachersManager.getUserID(), filePath))) {
                if (file.length() != 0) {
                    GetFile getFile = new GetFile(students, studentsManager);
                    getFile.getStudentsFile(file, teachersManager.getUserID());
                }
                else {
                    new Alert(Alert.AlertType.INFORMATION,"Cannot use an empty file",ButtonType.OK).showAndWait();
                }
            }
            else {
                new Alert(Alert.AlertType.INFORMATION,"This is not the file you created",ButtonType.OK).showAndWait();
            }
        }
    }

    @FXML // This is for saving class statistics as a file.
    private void saveClassStatisticsFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save statistics file");

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            if (students.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION,"Cannot save file, because there are no students",
                          ButtonType.OK).showAndWait();
            }
            else {
                SaveClassStatisticsFile statisticsFile = new SaveClassStatisticsFile(studentsManager,students,filesManager,teacher);
                statisticsFile.saveClassStatistics(teachersManager.getUserID(),file);
            }
        }
    }

    @FXML // This is for showing the version of the program.
    public void version() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Version");
        alert.setHeaderText("Version: 3.0.1");
        alert.setContentText("A large update in the whole program,\nand we added a lot of features");
        alert.showAndWait();
    }

    @FXML // This is a help item in the menu bar.
    private void help() throws IOException {
        String page = "HelpMenuItemScene";
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/scenes/"+page+".fxml")));
        Parent parent = loader.load();
        borderPane.setCenter(parent);
    }

    @FXML // This is for checking any updates.
    private void checkUpdates() {
        new Alert(Alert.AlertType.INFORMATION, "There are no updates",ButtonType.OK).showAndWait();
    }

    // This is for getting the name of a specific student, and it is used with edit and student statistics.
    private String getStudentNameFromUser() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("The Student name");
        textInputDialog.setHeaderText("Enter the student full name to find the right student.");
        textInputDialog.setContentText("The student name:");
        Optional<String> result = textInputDialog.showAndWait();
        return result.orElse(null);
    }
    // This is for getting a student name, and check if a student in the table, and used with edit, student statistics.
    private Student getStudentIsInTable(String name) {
        for (Student student : students) {
            String fullName = student.getFull_name().toLowerCase();
            if (fullName.equals(name.toLowerCase())) {
                return student;
            }
        }
        return null;
    }

    public void setFilesManager(FilesManager filesManager) {
        this.filesManager = filesManager;
    }
    public void setStudentsManager(StudentsManager studentsManager) {
        this.studentsManager = studentsManager;
    }
    public void setTeachersManager(TeachersManager teachersManager) {
        this.teachersManager = teachersManager;
    }
    public void setUserIsFound(boolean userIsFound) {
        if (userIsFound) {
            studentsManager.getAllStudents(teachersManager.getUserID(), studentsManager, students);
        }
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public void setSettings(Settings settings) {
        this.settings = settings;
        this.settings.setProgramBackground(settings.getProgramBackground());
        this.settings.setDashboardBackground(settings.getDashboardBackground());

        Color programColor = Color.valueOf(settings.getProgramBackground());
        Color dashboard = Color.valueOf(settings.getDashboardBackground());
        borderPane.setBackground(new Background(new BackgroundFill(programColor,CornerRadii.EMPTY, Insets.EMPTY)));
        leftAnchorPane.setBackground(new Background(new BackgroundFill(dashboard, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    public void setSettingsManager(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }
}
