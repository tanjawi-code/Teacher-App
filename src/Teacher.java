enum subjects{Math,Physics_and_Chemistry,Life_and_Earth_Natural_Sciences}
enum schools{Elementary_School,Middle_School,High_School}
enum teacherGender{Male,Female}

public class Teacher {

    private String name;
    private int age;
    private String password;
    private subjects subject;
    private schools school;
    private teacherGender gender;

    Teacher(){
        this.name = "Not found";
        this.age = 0;
        this.password = "******";
    }

    Teacher(String name, int age, String password, subjects subject, schools school, teacherGender gender){
        this.name = name;
        this.age = age;
        this.password = password;
        this.subject = subject;
        this.school = school;
        this.gender = gender;
    }

    Teacher(String name, String password){
        this.name = name;
        this.password = password;
    }

    Teacher(Teacher other){
        this.name = other.name;
        this.age = other.age;
        this.password = other.password;
        this.gender = other.gender;
        this.subject = other.subject;
        this.school = other.school;
    }

    // Getters
    String getName() {
        return name;
    }
    int getAge() {
        return age;
    }
    String getPassword() {
        return password;
    }
    subjects getSubject() {
        return subject;
    }
    schools getSchool() {
        return school;
    }
    teacherGender getGender() {
        return gender;
    }
}
