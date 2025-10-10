enum subjects{Math,Physics_and_Chemistry,Life_and_Earth_Natural_Sciences}
enum schools{Elementary_School,Middle_School,High_School}
enum teacherGender{Male,Female}

public class Teacher {

    private int id;
    private final String name;
    private final int age;
    private final String password;
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

    Teacher(String name, int age, String password, subjects subject, schools school, teacherGender gender,int id){
        this.name = name;
        this.age = age;
        this.password = password;
        this.subject = subject;
        this.school = school;
        this.gender = gender;
        this.id = id;
    }

    Teacher(Teacher other){
        this.name = other.name;
        this.age = other.age;
        this.password = other.password;
        this.gender = other.gender;
        this.subject = other.subject;
        this.school = other.school;
        this.id = other.id;
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
