enum subjects{Math,Physics_and_Chemistry,Life_and_Earth_Natural_Sciences}
enum schools{Elementary_School,Middle,High_School}

public class Teacher {

    private String name;
    private int age;
    private String password;
    private subjects subject;
    private schools school;

    Teacher(){
        this.name = "Not found";
        this.age = 0;
        this.password = "******";
    }

    Teacher(String name, int age, String password, subjects subject, schools school){
        this.name = name;
        this.age = age;
        this.password = password;
        this.subject = subject;
        this.school = school;
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
}
