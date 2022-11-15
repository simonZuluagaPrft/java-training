# Functionalities
CRUD student
enroll student
get student courses
get student subjects
get student grade
get student schedule
get student report card

CRUD teacher
grade a student (through a course)

CRUD subject

CRUD course
enroll student in a course
drop student from a course
get students that passed the course
get students that failed the course
get students enrolled in the course

CRUD class

---
# Routing
student/
POST    create student

student/{id}
GET     read student
PUT     update student
DELETE  delete student

student/courses
GET     get courses enrolled by the student

student/subjects
GET     get subjects the student is studying

student/grades
GET     get student's grade for a course

student/schedule
GET     get student's schedule

student/reportCard
GET     get student's reportCard

teacher/
POST    create teacher

teacher/{id}
GET     read teacher
PUT     update teacher
DELETE  delete teacher

teacher/grade
POST    grade a student
PUT     update a students grade

subject/
POST    create subject

subject/{id}
GET     read subject
PUT     update subject
DELETE  delete subject

course/
POST    create subject

course/{id}
GET     read subject
PUT     update subject
DELETE  delete subject

course/enroll
POST    enroll student in a course
DELETE  drop student from a course