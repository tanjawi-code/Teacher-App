# Teacher app project

- introduction.

A simple java project to manage and store students' details.

## project description :

This project allows you to :

- manage students' details (add student, remove student, edit, ect).
- Allows you to see statistics (number of students, number of passed students, number of failed students, highest
point, lowest point, class average, success rate, and the top three students in the class).
- Provide a simple screen of dealing with the program.
---
## To-Do list.
- [  ] Make things more clear and simple.
- [  ] Add searching ways : (age, point, grade, passed or failed, and male or female).
- [  ] Add frames to other buttons in the main frame.
- [  ] Improve Showing statistics for class and for students.
---
## Problems need to be fixed.
- Find a solution to reduce using try-catch without methods too much.
- Make the program hold all the problems of (InputMismatchException) to avoid stopping the program while working.
- Fix the problem in class number. When the teacher store students in the first step, and then the teacher wants
to add new students that weren't in the list before. The class number starts from 1 instead of continue from the last student in the list.
- Fix the problem in student statistics button. when you press cancel the program show that student is not found.
- Fix the problem in class number, example : when add students like five, and then you want to delete a student, but
  **not the last student because the problem will not happen**. You delete a student, and then you add another 
student. the last student who has 5 as the class number will be the same number of the added student after him.
The last student (5 as class number), the added student (the one after him, 5 as class number). This problem will only
happen when you delete a student and then add a student.
- Fix some problems in updating student's details.
---
## Future improvements.
- Add database support (MySQl or SQLite).
- Improve the project by using **JavaFx** instead of swing.
- Add multi-language support (Localization).
---
## **The developer**
- **Name** : [private name].
- **Github** : [https://github.com/tanjawi-code].
- *Version* : 2.1.0
